function getLesson() {
    let id = window.location.search.replace('?id', '');
    let request = new XMLHttpRequest();
    let title = document.querySelector('.title');
    let description = document.querySelector('.description');

    let url = "/lessons/lesson?id" + id;
    request.open("GET", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            let lesson = request.response;
            title.innerHTML = lesson.title;
            description.innerHTML = lesson.description;
        }
    };
    request.send();
}