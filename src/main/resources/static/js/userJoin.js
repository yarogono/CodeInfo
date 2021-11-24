// Input tag
const USER_PW = document.getElementById('pw_input');
const USER_PW2 = document.getElementById('pw_input2');
const NICKNAME = document.getElementById('nickname_input')

// Check span tag
const PW_CHECK = document.getElementById('join_pw_check');
const PW2_CHECK = document.getElementById('join_pw2_check');
const NICKNAME_CHECK = document.getElementById('join_nickname_check');

// Regex
const REGEX_NICKNAME = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{3,}$/; // 닉네임이 검사할 정규식



function check(regex, tag, message) {
    if(regex.test(tag.value)) {
        return true;
    }
    alert(message);
    tag.value = "";
    tag.focus();
}


function checkForm() {

    if(USER_PW.value != USER_PW2.value) {
        alert("비밀번호가 다릅니다. 다시 입력해주세요.");
        USER_PW.value = "";
        USER_PW2.value = "";
        PW_CHECK.innerHTML = "";
        PW2_CHECK.innerHTML = "";
        USER_PW.focus();
        return false;
    }

    if(USER_PW.value === NICKNAME.value) {
        alert("닉네임과 비밀번호가 같습니다.");
        USER_PW.value = "";
        USER_PW2.value = "";
        PW_CHECK.innerHTML = "";
        PW2_CHECK.innerHTML = "";
        NICKNAME.focus();
        return false;
    }

    if(!check(REGEX_NICKNAME, NICKNAME,"닉네임은 영어 대소문자, 숫자 포함 형태의 3~15자리입니다.")) {
        return false;
    }
}


// 패스워드, 패스워드 확인 일치여부 span 태그에 출력
function pwdEqualCheck() {

    if (USER_PW.value !== '' && USER_PW2.value !== '') {
        if (USER_PW.value === USER_PW2.value) {
            PW2_CHECK.innerHTML = '비밀번호가 일치합니다.';
            PW2_CHECK.style.color = 'blue';
        } else {
            PW2_CHECK.innerHTML = '비밀번호가 일치하지 않습니다.';
            PW2_CHECK.style.color = 'red';
        }
    }
}


function nickDuplicateCheck() {
    $.ajax({
        type: "POST",
        url: "/api/user/duplicate",
        data: {
            nickname_give: $(this).val()
        },
        success: function (response) {
            if (response) {
                NICKNAME_CHECK.innerHTML = "이미 존재하는 닉네임입니다.";
                NICKNAME_CHECK.style.color = "red";
                NICKNAME.focus();
            } else {
                NICKNAME_CHECK.innerHTML = "사용할 수 있는 닉네임입니다.";
                NICKNAME_CHECK.style.color = "blue";
            }
        }
    });
}


function init() {

    $("#pw_input2").on("change keyup paste", pwdEqualCheck);

    // $("#email").change(emailDuplicateCheck);
    $("#nickname_input").change(nickDuplicateCheck);
}


if(PW2_CHECK) {
    init();
}
