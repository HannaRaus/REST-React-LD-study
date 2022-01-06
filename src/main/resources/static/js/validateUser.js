function validateAndCreateUser() {

    const WRONG_USER_NAME_FORMAT = "Name must be in range of 3-50 symbols";
    const USER_ALREADY_REGISTERED = "User with defined number already registered";
    const INCORRECT_PHONE_NUMBER = "Incorrect phone number. Must be in +380671111111 format";
    const WRONG_PASSWORD_FORMAT = "Password must be in range of 5-50 symbols";

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
                window.location.href = '/login';
            } else {
                operationStatus.errors.forEach(function (error) {
                    switch (error) {
                        case 'WRONG_USER_NAME_FORMAT':
                            usernameErrorField.innerHTML = WRONG_USER_NAME_FORMAT;
                            break;
                        case 'INCORRECT_PHONE_NUMBER':
                            phoneErrorField.innerHTML = INCORRECT_PHONE_NUMBER;
                            break;
                        case 'USER_ALREADY_REGISTERED':
                            phoneErrorField.innerHTML = USER_ALREADY_REGISTERED;
                            break;
                        case 'WRONG_PASSWORD_FORMAT':
                            passwordErrorField.innerHTML = WRONG_PASSWORD_FORMAT;
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