function validateAndCreateLesson() {

    const WRONG_TITLE_LENGTH = "Title must be in range of 5-100 symbols";
    const WRONG_DESCRIPTION_LENGTH = "Description must be under 500 symbols";

    let titleErrorField = document.querySelector('.titleErrorField');
    titleErrorField.innerHTML = null;
    let descriptionErrorField = document.querySelector('.descriptionErrorField');
    descriptionErrorField.innerHTML = null;

    let title = document.querySelector('#title');
    let description = document.querySelector('#description');
    let accessType = document.querySelector('input[name="accessType"]:checked');
    let userAsAuthor = document.querySelector('#userAsAuthor').checked;

    let request = new XMLHttpRequest();
    let url = "/lessons/create";
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            const operationStatus = request.response;
            if (operationStatus.success === true) {
                window.location.href = '/';
            } else {
                operationStatus.errors.forEach(function (error) {
                    switch (error) {
                        case 'WRONG_TITLE_LENGTH':
                            titleErrorField.innerHTML = WRONG_TITLE_LENGTH;
                            break;
                        case 'WRONG_DESCRIPTION_LENGTH':
                            descriptionErrorField.innerHTML = WRONG_DESCRIPTION_LENGTH;
                            break;
                    }
                });
            }
        }
    };
    const data = JSON.stringify({
        "title": title.value,
        "description": description.value,
        "accessType": accessType.value,
        "userAsAuthor": userAsAuthor});
    request.send(data);
}