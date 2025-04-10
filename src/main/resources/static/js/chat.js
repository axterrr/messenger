window.onload = function() {
    var messageBox = document.getElementById('messages');
    messageBox.scrollTop = messageBox.scrollHeight;
}

document.getElementById("send-message-form").addEventListener('submit', function(e) {
    e.preventDefault();

    const message = {
        content: document.getElementById('send-message-form-content').value,
        chatId: document.getElementById('send-message-form-chat-id').value,
        senderId: document.getElementById('send-message-form-sender-id').value,
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
});
