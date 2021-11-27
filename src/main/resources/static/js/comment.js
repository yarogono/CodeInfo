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
            window.location.reload();
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

function updateBtnDisplayChg(commentId) {
    let comment = document.getElementById(`comment${commentId}`);
    let commentTextArea = comment.childNodes[5];
    let commentBodyText = comment.childNodes[3];
    let meta = comment.childNodes[1];
    let commentSetting = meta.childNodes[5];
    let commentEditSubmit = commentSetting.childNodes[3];
    let commentEdit = commentSetting.childNodes[7];

    commentTextArea.style.display = "block";
    commentBodyText.style.display = "none";
    commentEditSubmit.style.display = "block";
    commentEdit.style.display = "none";
    commentTextArea.value = commentBodyText.innerHTML;
}


function updateCommentSubmit(commentId) {
    let comment = document.getElementById(`comment${commentId}`);
    let commentTextArea = comment.childNodes[5];
    let commentBodyText = comment.childNodes[3];
    let meta = comment.childNodes[1];
    let commentSetting = meta.childNodes[5];
    let commentEditSubmit = commentSetting.childNodes[3];
    let commentEdit = commentSetting.childNodes[7];

    if(commentTextArea.value == "") {
        alert("내용을 입력 해주세요.");
        return;
    }

    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    let result = {
        commentId: commentId,
        content: commentTextArea.value
    }

    $.ajax({
        type: "PUT",
        url: "/api/board/comment",
        data: JSON.stringify(result),
        contentType: "application/json",
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            commentBodyText.innerHTML = commentTextArea.value;
            commentTextArea.style.display = "none";
            commentBodyText.style.display = "block";
            commentEditSubmit.style.display = "none";
            commentEdit.style.display = "block";
        }
    });
}


function init() {
    COMMENT_ADD.addEventListener("click", addComment);
}

init()

