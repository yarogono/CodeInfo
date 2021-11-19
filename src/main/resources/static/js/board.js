const UPDATE_CHECKBOX = document.getElementById("update-checkbox");



function deletePost(postNum) {
    $.ajax({
        type: "DELETE",
        url: `/${postNum}`,
        success: function (response) {
            alert(`${postNum}번 글이 삭제되었습니다.`);
            window.location.reload();
        }
    });
}

function isChecked() {
    let titleInput = document.getElementById("title");
    let writerInput = document.getElementById("writer");
    let contentInput = document.getElementById("content");

    if(this.checked) {
        titleInput.readOnly = false;
        writerInput.readOnly = false;
        contentInput.readOnly = false;
    } else {
        titleInput.readOnly = true;
        writerInput.readOnly = true;
        contentInput.readOnly = true;
    }
}

function init() {
    UPDATE_CHECKBOX.addEventListener("change", isChecked)
}

if(UPDATE_CHECKBOX) {
    init();
}
