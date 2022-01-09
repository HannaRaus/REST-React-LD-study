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
        "userAsAuthor": userAsAuthor
    });
    request.send(data);
}

function createNewTag() {
    const TAG_DUPLICATE_ERROR = "This tag already exists";
    const TAG_IS_NOT_UNIQUE = "There are few tags with same label, try to be more fancy)";
    const WRONG_TAG_LENGTH = "Tag is too long";

    let tagErrorField = document.querySelector('.tagErrorField');
    tagErrorField.innerHTML = null;

    let label = document.querySelector('#label');

    let request = new XMLHttpRequest();
    let url = "/tags/create";
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            const operationStatus = request.response;
            if (operationStatus.success === true) {
                window.location.reload();
            } else {
                operationStatus.errors.forEach(function (error) {
                    switch (error) {
                        case 'TAG_DUPLICATE_ERROR':
                            tagErrorField.innerHTML = TAG_DUPLICATE_ERROR;
                            break;
                        case 'TAG_IS_NOT_UNIQUE':
                            tagErrorField.innerHTML = TAG_IS_NOT_UNIQUE;
                            break;
                        case 'WRONG_TAG_LENGTH':
                            tagErrorField.innerHTML = WRONG_TAG_LENGTH;
                            break;
                    }
                });
            }
        }
    };
    const data = JSON.stringify({
        "label": label.value
    });
    request.send(data);
    document.getElementById('label').value = '';
}