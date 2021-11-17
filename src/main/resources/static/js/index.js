const deleteBtn = document.getElementById("board-delete-btn");

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
