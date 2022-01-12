function filterByTag() {
    let tags = [];
    document.querySelectorAll('input[name="tag"]:checked').forEach(function (el) {
        tags.push(el.parentElement.textContent.trim());
    });

    let request = new XMLHttpRequest();
    let url = "/lessons/search/tags";
    for (let i=0; i<tags.length; ++i) {
        if (url.indexOf('?') === -1) {
            url = url + '?tags=' + tags[i];
        }else {
            url = url + '&tags=' + tags[i];
        }
    }
    request.open("GET", url, true);
    request.send(null);
}
