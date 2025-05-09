document.addEventListener('DOMContentLoaded', () => {
    if (activeChatId) {
        const messageBox = document.getElementById('messages');
        messageBox.scrollTop = messageBox.scrollHeight;
        document.getElementById("send-message-form-content").focus();
        document.getElementById("send-message-form-content").addEventListener("keydown", onEnterClick);
        document.getElementById("send-message-form").addEventListener('submit', processSendFormSubmit);
        document.querySelectorAll(".around-message").forEach(m => m.addEventListener('contextmenu', onMessageContextMenu));
        document.addEventListener('click', () => document.getElementById("context-menu")?.remove());
        document.getElementById('leave-chat-button')?.addEventListener('click', onLeaveChatButtonClick);
        document.getElementById('delete-chat-button')?.addEventListener('click', onDeleteChatButtonClick);
        document.querySelectorAll(".chat-participant").forEach(p => p.addEventListener('contextmenu', onParticipantContextMenu));
        connectToChatWebSocket();
    }
});

function processSendFormSubmit(e) {
    e.preventDefault();

    const message = {
        content: document.getElementById('send-message-form-content').value,
        chatId: activeChatId,
    };

    processRequest(fetch('/api/message', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(message)
    }), () => document.getElementById('send-message-form-content').value = '');

    document.getElementById("send-message-form-content").focus();
}

function connectToChatWebSocket() {
    const socket = new SockJS('/chat-websocket');
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/chat/' + activeChatId, function (messageOutput) {
            const message = JSON.parse(messageOutput.body);
            showMessage(message);
        });
        stompClient.subscribe('/topic/chat/delete-message/' + activeChatId, function (messageOutput) {
            const messageId = messageOutput.body;
            removeMessage(messageId);
        });
        stompClient.subscribe(`/topic/chat/${activeChatId}/delete-user/${currentUserId}`, function () {
            window.location.href = "/";
        });
    });
}

function showMessage(message) {
    let emptyChatPlaceholder = document.getElementById('empty-chat-placeholder');
    if (emptyChatPlaceholder) emptyChatPlaceholder.hidden = true;

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

    div.classList.add('message-wrapper', message.sender.id === currentUserId ? 'from-user' : 'from-other');
    div.innerHTML += `
        <div class="around-message p-2">
            <div class="message" id="${message.id}">
                ${message.sender.id !== currentUserId ? `<span class="username">${escapeHtml(message.sender.name)}</span>` : ''}
                <div>
                    <span>${escapeHtml(message.content)}</span>
                    <small class="text-muted d-block">${formatTime(message.sentAt)}</small>
                </div>
            </div>
        </div>
    `;
    messagesDiv.appendChild(div);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
    div.getElementsByClassName("around-message")[0].addEventListener('contextmenu', onMessageContextMenu);
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

function onEnterClick(event) {
    if (event.key === "Enter" && !event.shiftKey) {
        event.preventDefault();
        document.getElementById("send-message-form").requestSubmit() ;
    }
}

function onMessageContextMenu(e) {
    const messageId = this.getElementsByClassName("message")[0].id
    const isCurrentUserOwner = document.getElementById("owner-sigh").parentElement.id === currentUserId
    const isCurrentUserSender = this.parentElement.classList.contains("from-user");
    const options = `
        <div class="menu-item" onclick="selectMessage('${messageId}')">Select</div>
        ${isCurrentUserSender ? `<div class="menu-item" onclick="editMessage('${messageId}')">Edit</div>` : ""}
        ${isCurrentUserSender || isCurrentUserOwner ? `<div class="menu-item text-danger" onclick="deleteMessage('${messageId}')">Delete</div>` : ""}
    `;
    showContextMenu(e, options)
}

function onParticipantContextMenu(e) {
    const userId = this.id;
    const isCurrentUserOwner = document.getElementById("owner-sigh").parentElement.id === currentUserId
    const options = `
        <div class="menu-item" onclick="handleViewProfile('${userId}')">View</div>
        ${(isCurrentUserOwner && userId !== currentUserId) ? `<div class="menu-item text-warning" onclick="makeOwner('${userId}')">Make Owner</div>` : ""}
        ${(isCurrentUserOwner && userId !== currentUserId) ? `<div class="menu-item text-danger" onclick="deleteUser('${userId}')">Delete</div>` : ""}
    `;
    showContextMenu(e, options)
}

function showContextMenu(e, options) {
    e.preventDefault()

    document.getElementById("context-menu")?.remove()

    let menu = document.createElement('div');
    menu.id ='context-menu';
    menu.className += "position-absolute bg-light border rounded p-1 shadow";
    menu.style.zIndex = "10000";
    menu.innerHTML = options;
    document.body.appendChild(menu);

    const menuWidth = menu.offsetWidth;
    const menuHeight = menu.offsetHeight;
    const windowWidth = window.innerWidth;
    const windowHeight = window.innerHeight;

    let x = e.pageX;
    let y = e.pageY;

    if (x + menuWidth > windowWidth) {
        x = windowWidth - menuWidth - 10;
    }

    if (y + menuHeight > windowHeight) {
        y = windowHeight - menuHeight - 10;
    }

    menu.style.left = `${x}px`;
    menu.style.top = `${y}px`;
}

function selectMessage(messageId) {
    console.log("selecting "+messageId)
}

function editMessage(messageId) {
    console.log("editing "+messageId)
}

function deleteMessage(messageId) {
    if (!confirm("Are you sure you want to delete the message?")) return;
    processRequest(fetch(`/api/message/${messageId}`, {
        method: 'DELETE',
    }), () => {});
}

function removeMessage(messageId) {
    let wrapper = document.getElementById(messageId).parentElement.parentElement;
    let divider = wrapper.getElementsByClassName("date-divider")[0];
    let next = wrapper.nextElementSibling;
    if (divider && next && !next.firstElementChild.classList.contains("date-divider")) {
        next.insertBefore(divider, next.firstChild);
    }
    wrapper.remove();
}

function processRequest(request, onSuccess) {
    request
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => { throw error })
            } else {
                if (onSuccess) {
                    onSuccess();
                }
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

function onLeaveChatButtonClick() {
    if (!confirm("Are you sure you want to leave the chat?")) return;
    processRequest(fetch(`/api/chat/${activeChatId}/leave`, {
        method: 'DELETE'
    }), () => window.location.href = "/");
}

function onDeleteChatButtonClick() {
    if (!confirm("Are you sure you want to delete the chat for everyone? This action cannot be undone.")) return;
    processRequest(fetch(`/api/chat/${activeChatId}`, {
        method: 'DELETE'
    }), () => window.location.href = "/");
}

function handleViewProfile(userId) {
    const element = document.getElementById(userId);
    if (!element) return;

    const user = {
        name: element.dataset.name,
        username: element.dataset.username,
        phone: element.dataset.phone,
        email: element.dataset.email,
        description: element.dataset.description
    };

    document.getElementById('modalUserName').textContent = escapeHtml(user.name);
    document.getElementById('modalUserUsername').textContent = escapeHtml(user.username);
    document.getElementById('modalUserPhone').textContent = escapeHtml(user.phone);

    const descSection = document.getElementById('modalUserDescriptionSection');
    if (user.description?.trim()) {
        document.getElementById('modalUserDescription').textContent = escapeHtml(user.description);
        descSection.style.display = 'block';
    } else {
        descSection.style.display = 'none';
    }

    const emailSection = document.getElementById('modalUserEmailSection');
    if (user.email?.trim()) {
        document.getElementById('modalUserEmail').textContent = escapeHtml(user.email);
        emailSection.style.display = 'block';
    } else {
        emailSection.style.display = 'none';
    }

    const modal = new bootstrap.Modal(document.getElementById('userProfileModal'));
    modal.show();
}

function makeOwner(userId) {
    if (!confirm("Are you sure you want to make the user owner of the chat? You will lost your status as owner.")) return;
    processRequest(fetch(`/api/chat/${activeChatId}/owner`, {
        method: 'PATCH',
        body: userId,
    }), () => window.location.reload());
}

function deleteUser(userId) {
    if (!confirm("Are you sure you want to delete the user from the chat?")) return;
    processRequest(fetch(`/api/chat/${activeChatId}/users/${userId}`, {
        method: 'DELETE',
    }), () => document.getElementById(userId).remove());
}
