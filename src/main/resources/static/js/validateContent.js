let localStorage = window.sessionStorage;

function validateContent() {
    const WRONG_TITLE_LENGTH = "Title must be under 100 symbols";
    const WRONG_URL_LENGTH = "Url can't be empty and must be under 500 symbols";
    const WRONG_URL_FORMAT = "Url has incorrect path";
    const WRONG_COMMENT_LENGTH = "Comment must be under 500 symbols";
    const SUCCESS_SAVE = "Content added";

    let titleContentErrorField = document.querySelector('.titleContentErrorField');
    titleContentErrorField.innerHTML = null;
    let urlContentErrorField = document.querySelector('.urlContentErrorField');
    urlContentErrorField.innerHTML = null;
    let commentContentErrorField = document.querySelector('.commentContentErrorField');
    commentContentErrorField.innerHTML = null;
    let contentSavedField = document.querySelector('.contentSavedField');
    contentSavedField.innerHTML = null;

    let contentTitle = document.querySelector('#contentTitle');
    let mediaType = document.getElementById('mediaType').selectedOptions[0].value;
    let contentUrl = document.querySelector('#contentUrl');
    let contentComment = document.querySelector('#contentComment');

    let request = new XMLHttpRequest();
    let url = "/contents/create";
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            const operationStatus = request.response;
            if (operationStatus.success === true) {
                contentSavedField.innerHTML = SUCCESS_SAVE;
                window.location.reload();
            } else {
                operationStatus.errors.forEach(function (error) {
                    switch (error) {
                        case 'WRONG_TITLE_LENGTH':
                            titleContentErrorField.innerHTML = WRONG_TITLE_LENGTH;
                            break;
                        case 'WRONG_URL_LENGTH':
                            urlContentErrorField.innerHTML = WRONG_URL_LENGTH;
                            break;
                        case 'WRONG_URL_FORMAT':
                            urlContentErrorField.innerHTML = WRONG_URL_FORMAT;
                            break;
                        case 'WRONG_COMMENT_LENGTH':
                            commentContentErrorField.innerHTML = WRONG_COMMENT_LENGTH;
                            break;
                    }
                });
            }
        }
    };
    const data = JSON.stringify({
        "title": contentTitle.value,
        "mediaType" : mediaType,
        "url" :contentUrl.value,
        "comment" : contentComment.value
    });

    request.send(data);

    addContent(data);
}

function addContent(data) {
    let existingEntries = JSON.parse(localStorage.getItem("contents"));
    if(existingEntries == null) existingEntries = [];

    localStorage.setItem("contents", data);
    existingEntries.push(data);
    localStorage.setItem("contents", JSON.stringify(existingEntries));
}
