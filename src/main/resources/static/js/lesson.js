function getLesson() {
    let id = window.location.search.replace('?id', '');
    let request = new XMLHttpRequest();
    let lessonSection = document.getElementById('lesson')
    let contentSection = document.getElementById('content')
    let tagsSection = document.getElementById('tags')

    let url = "/lessons/lesson?id" + id;
    request.open("GET", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json';
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            let lesson = request.response;

            lessonSection.innerHTML += `<div class="blockquote text-right">
                                            <footer>${lesson.creationDate}</footer>
                                        </div>`
            lessonSection.innerHTML += `<div class="text-center">
                                            <h1>${lesson.title}</h1>
                                        </div>`
            if (lesson.author != null) {
                lessonSection.innerHTML += `<blockquote class="blockquote text-center">
                                            <p class="mb-0">${lesson.description}</p>
                                            <footer class="blockquote-footer">Author ${lesson.author.name}</footer>
                                        </blockquote>`
            } else {
                lessonSection.innerHTML += `<blockquote class="blockquote text-center">
                                            <p class="mb-0">${lesson.description}</p>
                                        </blockquote>`
            }

            let contents = lesson.contents;
            contents.forEach(function (content) {
                if (content.mediaType === 'VIDEO') {
                    getVideoElement(contentSection, content.url)
                }
                if (content.mediaType === 'IMAGE') {
                    contentSection.innerHTML +=
                        `<img class="rounded mx-auto d-block" width="100%" src="${content.url}" alt="${content.title}">`
                }
                if (content.title != null) {
                    contentSection.innerHTML += `<h5 class="text-center">${content.title}</h5>`
                }
                if (content.comment != null) {
                    contentSection.innerHTML += `<p class="blockquote text-center">${content.comment}</p>`
                }
            })
            if (Array.isArray(lesson.tags) && lesson.tags.length) {
                generateTags(tagsSection, lesson.tags)
            }
        }
    };
    request.send();
}

function getVideoElement(container, url) {
    let embed = url.replace('/watch?v=', '/embed/');
    container.innerHTML += `<div class="embed-responsive embed-responsive-16by9">
                                                        <iframe class="embed-responsive-item" src="${embed}"
                                                        allowfullscreen></iframe>
                                                </div>`
}

function generateTags(container, tags) {
    tags.forEach(function (tag) {
        container.innerHTML += `<label class="btn btn-outline-secondary">${tag.label}</label>`
    });
}