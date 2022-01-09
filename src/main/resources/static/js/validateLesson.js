function validateAndCreateLesson() {

    const WRONG_TITLE_LENGTH = "Title must be in range of 5-100 symbols";
    const WRONG_DESCRIPTION_LENGTH = "Description must be under 500 symbols";
    const EMPTY_CONTENT_ERROR = "Content can't be empty. Add some information to lesson";

    let titleErrorField = document.querySelector('.titleErrorField');
    titleErrorField.innerHTML = null;
    let descriptionErrorField = document.querySelector('.descriptionErrorField');
    descriptionErrorField.innerHTML = null;
    let contentErrorField = document.querySelector('.contentErrorField');
    contentErrorField.innerHTML = null;

    let title = document.querySelector('#title');
    let description = document.querySelector('#description');
    let accessType = document.querySelector('input[name="accessType"]:checked');
    let userAsAuthor = document.querySelector('#userAsAuthor').checked;

    let contents = JSON.parse(localStorage.getItem("contents"));
    let tags = [];
    document.querySelectorAll('input[name="tag"]:checked').forEach(function (el) {
        tags.push(el.parentElement.textContent.trim());
    });

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
                        case 'EMPTY_CONTENT_ERROR':
                            contentErrorField.innerHTML = EMPTY_CONTENT_ERROR;
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
        "tags": tags,
        "contents" : contents,
        "userAsAuthor": userAsAuthor
    });
    request.send(data);
    localStorage.clear();
}
