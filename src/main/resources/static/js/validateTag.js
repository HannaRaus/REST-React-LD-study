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
}