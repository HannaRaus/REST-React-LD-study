function getId() {
    return window.location.search.replace('?id=', '');
}

function getLesson() {
    let id = getId();
    let request = new XMLHttpRequest();
    let title = document.querySelector('#title');
    let description = document.querySelector('#description');
    let accessType = document.querySelector('#accessType');

    let url = "/lessons?id=" + getId();
    request.open("GET", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            let lesson = request.response;
            title.value = lesson.title;
            description.textContent = lesson.description;
            $("[name=accessType]").val([lesson.accessType.toLowerCase()]);

        }
    };
    request.send();
}