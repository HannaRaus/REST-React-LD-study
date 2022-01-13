function validateSendLesson(url) {

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
                        case 'WRONG_TITLE_LENGTH':
                            titleErrorField.innerHTML = error.message;
                            break;
                        case 'WRONG_DESCRIPTION_LENGTH':
                            descriptionErrorField.innerHTML = error.message;
                            break;
                        case 'EMPTY_CONTENT_ERROR':
                            contentErrorField.innerHTML = error.message;
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
