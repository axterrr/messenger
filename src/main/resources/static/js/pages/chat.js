document.addEventListener('DOMContentLoaded', () => {
    if (activeChatId) {
        const messageBox = document.getElementById('messages');
        messageBox.scrollTop = messageBox.scrollHeight;
        document.getElementById("send-message-form").addEventListener('submit', processSendFormSubmit);
        connectToChatWebSocket();
    }
});

function processSendFormSubmit(e) {
    e.preventDefault();

    const message = {
        content: document.getElementById('send-message-form-content').value,
        chatId: activeChatId,
    };

    fetch('/api/message', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(message)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => { throw error })
            } else {
                document.getElementById('send-message-form-content').value = '';
            }
        })
        .catch(error => {
            const errorBox = document.getElementById('send-message-error');
            errorBox.innerHTML = '';
            error.messages.sort().forEach(msg => {
                const line = document.createElement('div');
                line.style.paddingLeft = '10px'
                line.textContent = msg;
                errorBox.appendChild(line);
            });
            errorBox.style.display = 'block';
        });
}

function connectToChatWebSocket() {
    const socket = new SockJS('/chat-websocket');
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/chat/' + activeChatId, function (messageOutput) {
            const message = JSON.parse(messageOutput.body);
            showMessage(message);
        });
    });
}

function showMessage(message) {
    emptyChatPlacepolder = document.getElementById('empty-chat-placeholder');
    if (emptyChatPlacepolder) emptyChatPlacepolder.hidden = true;

    const messagesDiv = document.getElementById('messages');
    const div = document.createElement('div');

    let dividers = document.getElementsByClassName("date-divider");
    let lastMessageDateString = dividers.length === 0 ? null : dividers[dividers.length - 1].getElementsByTagName("small")[0].innerText;
    let newMessageDate = new Date(message.sentAt);
    let newMessageDateString = newMessageDate.toLocaleDateString('uk-UA', { day: 'numeric', month: 'long' })
    if (lastMessageDateString !== newMessageDateString) {
        div.innerHTML += `
            <div class="date-divider">
              <small>${newMessageDateString}</small>
            </div>
        `;
    }

    div.classList.add('message-wrapper', message.sender.username === currentUsername ? 'from-user' : 'from-other');
    div.innerHTML += `
        <div class="message">
            ${message.sender.username !== currentUsername ? `<span class="username">${escapeHtml(message.sender.name)}</span>` : ''}
            <div>
                <span>${escapeHtml(message.content)}</span>
                <small class="text-muted d-block">${formatTime(message.sentAt)}</small>
            </div>
        </div>
    `;
    messagesDiv.appendChild(div);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

function formatTime(isoString) {
    const date = new Date(isoString);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.innerText = text;
    return div.innerHTML;
}
