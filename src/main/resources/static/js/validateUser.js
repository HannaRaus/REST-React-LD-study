function validateAndCreateUser() {

    let usernameErrorField = document.querySelector('.usernameErrorField');
    usernameErrorField.innerHTML = null;
    let phoneErrorField = document.querySelector('.phoneErrorField');
    phoneErrorField.innerHTML = null;
    let passwordErrorField = document.querySelector('.passwordErrorField');
    passwordErrorField.innerHTML = null;

    let name = document.querySelector('#username');
    let phone = document.querySelector('#phone');
    let password = document.querySelector('#password');
    let sendNotifications = document.querySelector('#sendNotifications').checked;

    let request = new XMLHttpRequest();
    let url = "/users/registration";
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            const operationStatus = request.response;
            if (operationStatus.success === true) {
                window.location.href = '/lessons/all';
            } else {
                operationStatus.errors.forEach(function (error) {
                    switch (error.name) {
                        case 'WRONG_USER_NAME_FORMAT':
                            usernameErrorField.innerHTML = error.message;
                            break;
                        case 'INCORRECT_PHONE_NUMBER':
                            phoneErrorField.innerHTML = error.message;
                            break;
                        case 'USER_ALREADY_REGISTERED':
                            phoneErrorField.innerHTML = error.message;
                            break;
                        case 'WRONG_PASSWORD_FORMAT':
                            passwordErrorField.innerHTML = error.message;
                            break;
                    }
                });
            }
        }
    };
    const data = JSON.stringify({
        "name": name.value,
        "phone": phone.value,
        "password": password.value,
        "sendNotifications": sendNotifications});
    request.send(data);
}