const UPDATE_CHECKBOX = document.getElementById("update-checkbox");



function isChecked() {
    let titleInput = document.getElementById("title");
    let contentInput = document.getElementById("content");

    if(this.checked) {
        titleInput.readOnly = false;
        contentInput.readOnly = false;
    } else {
        titleInput.readOnly = true;
        contentInput.readOnly = true;
    }
}

function init() {
    UPDATE_CHECKBOX.addEventListener("change", isChecked)
}

if(UPDATE_CHECKBOX) {
    init();
}
