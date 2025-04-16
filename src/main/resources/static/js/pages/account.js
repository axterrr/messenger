document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("edit-password-form").addEventListener('submit', onEditPasswordFormSubmit);
    document.getElementById("change-password-button").addEventListener('click', function (){
        document.getElementById("edit-password-form").hidden = false;
        document.getElementById("change-password-button").hidden = true;
    });
    document.getElementById("logout-button").addEventListener('click', onLogoutButtonClick);
    document.getElementById("delete-account-button").addEventListener('click', onDeleteAccountButtonClick);
});

function onEditPasswordFormSubmit(e) {
    e.preventDefault();

    const password = {
        password: document.getElementById('password').value,
        confirmPassword: document.getElementById('confirm-password').value,
    };

    processRequest(fetch(`/api/user/${currentUserId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(password)
    }), "/settings/account?success");
}

function onDeleteAccountButtonClick() {
    if (!confirm("Are you sure you want to delete your account? This action cannot be undone.")) return;
    if (!confirm("All your data from this account will be lost forever.")) return;

    processRequest(fetch(`/api/user/${currentUserId}`, {
        method: 'DELETE',
    }), "/auth/logout");
 }

function onLogoutButtonClick() {
    if (!confirm("Are you sure you want to log out from your account?")) return;
    location.href='/auth/logout'
}

function processRequest(request, onSuccess) {
    request
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => { throw error })
            } else {
                location.href = onSuccess;
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
