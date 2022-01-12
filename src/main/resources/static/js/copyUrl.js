//<!-- script for copying url button --!>
function copyURI(evt) {
    evt.preventDefault();

    let baseURL = window.location.protocol + "//" + window.location.host // get base url
    let copyURL = baseURL + evt.target.getAttribute('href') // link forming

    navigator.clipboard.writeText(copyURL);
}