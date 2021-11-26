const COMMENT_ADD = document.getElementById("JScomment-add");
const COMMENT_TEXTAREA = document.getElementById("JScomment-textarea");


const HEADER = $("meta[name='_csrf_header']").attr('content');
const TOKEN = $("meta[name='_csrf']").attr('content');

function addComment() {

    let username = document.getElementById("JSusername");

    let content = COMMENT_TEXTAREA.value;
    let usernameText = username.innerText;
    let boardNum = window.location.href.split("/")[4];

    if(usernameText == "") {
        alert("로그인이 필요한 기능입니다.");
        COMMENT_TEXTAREA.value = "";
        return;
    }

    if(content == "") {
        alert("댓글 내용을 입력해주세요.");
        COMMENT_TEXTAREA.focus();
        return;
    }

    $.ajax({
        type: "POST",
        url: `/detail/${boardNum}`,
        data: JSON.stringify({
            writer: usernameText,
            content: content
        }),
        contentType: "application/json",
        beforeSend: function(xhr){
            xhr.setRequestHeader(HEADER, TOKEN);
        },
        success: function () {
           COMMENT_TEXTAREA.value = "";
        }
    });
}


function deleteComment(commentId) {

    if (confirm("정말로 삭제하시겠습니까?") == true) {
        $.ajax({
            type: "DELETE",
            url: "/api/comment",
            data: JSON.stringify(commentId),
            contentType: "application/json",
            beforeSend: function(xhr){
                xhr.setRequestHeader(HEADER, TOKEN);
            },
            success: function (response) {
                // To Do : 리얼타임으로 보이도록 HTML제거, reload X
                window.location.reload();
            }
        });
    }
}


function init() {
    COMMENT_ADD.addEventListener("click", addComment)
}

init()

