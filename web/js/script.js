var checkIdResult = false, checkPasswdResult = false;

function checkId(id) {
    var regex = /^[A-Za-z0-9_]{4,16}$/g;

    var element = document.getElementById('checkIdResult');

    if (regex.exec(id.value)) {
        checkIdResult = true;
    } else {
        element.innerHTML = "사용 불가능한 아이디";
        checkIdResult = false;
    }

}

function checkPasswd(passwd) {
    var lengthRegex = /^[A-Za-z0-9!@#$%]{8,20}$/;
    var engUpperCaseRegex = /[A-Z]/;
    var engLowerCaseRegex = /[a-z]/;
    var digitRegex = /[0-9]/;
    var specRegex = /[!@#$%]/;

    var element = document.getElementById('checkPasswdResult');

    if (lengthRegex.exec(passwd.value)) {
        var safetyCount = 0;

        if (engUpperCaseRegex.exec(passwd.value)) safetyCount++;
        if (engLowerCaseRegex.exec(passwd.value)) safetyCount++;
        if (digitRegex.exec(passwd.value)) safetyCount++;
        if (specRegex.exec(passwd.value)) safetyCount++;

        switch (safetyCount) {
            case 4 :
                element.innerHTML = '안전';
                element.style.color = 'green';
                checkPasswdResult = true;
                break;
            case 3 :
                element.innerHTML = '보통';
                element.style.color = 'blue';
                checkPasswdResult = true;
                break;
            case 2 :
                element.innerHTML = '위험';
                element.style.color = 'orange';
                checkPasswdResult = true;
                break;
            default :
                element.innerHTML = '사용불가';
                element.style.color = 'red';
                checkPasswdResult = false;
        }

    } else {
        element.innerHTML = '8~16자리 영문자,숫자,특수문자 조합 필수!';
        element.style.color = 'red';
        checkPasswdResult = false;
    }


}


function checkSubmit() {
    if (checkIdResult == false) {
        alert('아이디 규칙 확인 필요');
        document.joinForm.id.focus();
        return false;
    } else if (checkPasswdResult == false) {
        alert('패스워드 규칙 확인 필요');
        document.joinForm.passwd.focus();
        return false;
    }
    return true;
}

function checkSubmit1() {
    if (checkPasswdResult == false) {
        alert('패스워드 규칙 확인 필요');
        document.managementForm.passwd.focus();
        return false;
    }
    return true;
}

function changeDomain(domain) {
    document.joinForm.email2.value = domain.value;
}

function changeDomain1(domain) {
    document.managementForm.email2.value = domain.value;
}