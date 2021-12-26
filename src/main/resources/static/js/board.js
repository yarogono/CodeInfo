const UPDATE_CHECKBOX = document.getElementById("update-checkbox");

function deletePost(postNum) {
    let username = document.getElementById("JSusername");
    if(username.innerText == "") {
        alert("글을 삭제할려면 로그인이 필요합니다.")
        return;
    }


    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    if (confirm(`${postNum}번 글을 정말 삭제하시겠습니까?`) == true) {
        $.ajax({
            type: "DELETE",
            url: `/${postNum}`,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                alert(`${postNum}번 글이 삭제되었습니다.`);
                window.location.reload();
            }
        });
    }
}

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
