function filterByTag() {
    let tags = [];
    // document.querySelectorAll('input[name="tag"]:checked').forEach(function (el) {
    //     tags.push(el.parentElement.textContent.trim());
    // });

    // let tag = document.getElementById('tag');
    // let label = tag.textContent.trim() || tag.innerText.trim();
    // tags.push(label);

    let request = new XMLHttpRequest();
    let url = "/lessons/search/tags";
    if (tags.length) {
        for (let i = 0; i < tags.length; ++i) {
            if (url.indexOf('?') === -1) {
                url = url + '?tag=' + tags[i];
            } else {
                url = url + '&tag=' + tags[i];
            }
        }
    } else {
        url = "/lessons/all"
    }
    request.open("GET", url, true);
    request.send(null);
}
