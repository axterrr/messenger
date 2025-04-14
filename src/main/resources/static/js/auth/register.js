document.getElementById("register-user-form").addEventListener('submit', function(e) {
    e.preventDefault();

    const user = {
        phone: document.getElementById('phone').value,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        password: document.getElementById('password').value,
        confirmPassword: document.getElementById('confirmPassword').value,
    };

    fetch('/api/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => { throw error })
            } else {
                location.href = "/auth/login"
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
});
