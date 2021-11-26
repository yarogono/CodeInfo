// Input tag
const USER_PW = document.getElementById('password');
const USER_PW2 = document.getElementById('password2');
const NICKNAME = document.getElementById('nickname')

// Check span tag
const PW_CHECK = document.getElementById('join_pw_check');
const PW2_CHECK = document.getElementById('join_pw2_check');
const NICKNAME_CHECK = document.getElementById('nickname_check');

// Regex
const REGEX_NICKNAME = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{3,}$/; // 닉네임이 검사할 정규식



function checkRegex(regex, tag, message) {
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

    if(!checkRegex(REGEX_NICKNAME, NICKNAME,"닉네임은 영어 대소문자 각각 1개, 숫자 포함 형태의 3~15자리입니다.")) {
        return false;
    }

    let isDuplicate = apiUserDuplicate($("#nickname").val())
    if(!isDuplicate) {
        alert("중복된 닉네임입니다.")

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


    let inputNickname = $(this).val();
    const nicknameCheck = document.getElementById("join_nickname_check");

    if(nicknameCheck !== null) {
        nicknameCheck.style.display = "none";
    }

    if(!REGEX_NICKNAME.test(inputNickname)) {
        NICKNAME_CHECK.innerHTML = "영어 대소문자 각각 1개, 숫자 포함 형태의 3~15자리입니다.";
        NICKNAME_CHECK.style.color = "red";
        NICKNAME.focus();
        return;
    }

    apiUserDuplicate(inputNickname);
}

function apiUserDuplicate(inputNickname) {
    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    let isDuplicate = false;

    $.ajax({
        type: "post",
        url: "/api/user/duplicate",
        data: inputNickname,
        async: false,
        contentType: "text/plain",
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            isDuplicate = response
            if (response) {
                NICKNAME_CHECK.innerHTML = "사용할 수 있는 닉네임입니다.";
                NICKNAME_CHECK.style.color = "blue";
            } else {
                NICKNAME_CHECK.innerHTML = "이미 존재하는 닉네임입니다.";
                NICKNAME_CHECK.style.color = "red";
                NICKNAME.focus();
            }
        }
    });

    return isDuplicate;
}


function init() {
    $("#pw_input2").on("change keyup paste", pwdEqualCheck);
    $("#nickname").change(nickDuplicateCheck);
}


if(PW2_CHECK) {
    init();
}
