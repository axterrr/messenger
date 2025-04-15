document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("edit-user-form").addEventListener('submit', onRegisterUserFormSubmit);
});

function onRegisterUserFormSubmit(e) {
    e.preventDefault();

    const user = {
        phone: document.getElementById('phone').value,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
    };

    fetch(`/api/user/${currentUserId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => { throw error })
            } else {
                location.href = "/settings/profile"
            }
        })
        .catch(error => {
            const errorBox = document.getElementById('validation-errors');
            errorBox.innerHTML = '';
            error.messages.sort().forEach(msg => {
                const line = document.createElement('div');
                line.textContent = msg;
                errorBox.appendChild(line);
            });
            errorBox.style.display = 'block';
        });
}
