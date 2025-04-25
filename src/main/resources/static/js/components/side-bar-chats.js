document.addEventListener('DOMContentLoaded', () => {
    connectToUserWebSocket();
});

function connectToUserWebSocket() {
    const socket = new SockJS('/chat-websocket');
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/user/' + currentUserId, function (messageOutput) {
            const message = JSON.parse(messageOutput.body);
            addChat(message);
        });
        stompClient.subscribe('/topic/user/update-last-message/' + currentUserId, function (messageOutput) {
            const message = JSON.parse(messageOutput.body);
            updateLastMessage(message);
        });
        stompClient.subscribe('/topic/user/delete-chat/' + currentUserId, function (chatIdOutput) {
            const chatId = chatIdOutput.body;
            document.getElementById(chatId).remove();
        });
    });
}

function addChat(message) {
    const chat = message.chat;
    let div = document.getElementById(chat.id);
    div.remove();
    div.innerHTML = `
        <a class="${activeChatId === chat.id ? 'active' : ''} chat-link flex-column justify-content-around" href="/?chat=${escapeHtml(chat.id)}">
            <span class="chat-name fw-bold">${escapeHtml(chat.name)}</span>
            <small class="chat-preview text-muted">
                ${escapeHtml((message.sender.id === currentUserId ? 'You: ' : message.sender.name + ': ') + message.content)}
            </small>
          </a>
    `
    const chatList = document.getElementById('chat-list');
    chatList.insertBefore(div, chatList.firstChild);
}

function updateLastMessage(message) {
    let small = document.getElementById(message.chat.id).getElementsByTagName('small')[0];
    if (message.content) {
        small.textContent = escapeHtml((message.sender.id === currentUserId ? 'You: ' : message.sender.name + ': ') + message.content);
    } else {
        small.textContent = 'No messages yet';
    }
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.innerText = text;
    return div.innerHTML;
}
