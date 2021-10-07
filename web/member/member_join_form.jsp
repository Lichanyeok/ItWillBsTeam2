<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="../js/script.js"></script>
<style type="text/css">
	body {
		width: 600px;
		margin: auto;
	}

	h1 {
		width: 400px;
		text-align: center;
	}
</style>
</head>
<body>
	<h1>회원가입</h1>
	<form action="MemberJoinPro.me" method="post" name="joinForm" onsubmit="return checkSubmit()">
		<table border="1">
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" required="required" size="20"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<input type="radio" name="gender" value="남" checked="checked">남&nbsp;&nbsp;
					<input type="radio" name="gender" value="여">여
				</td>
			</tr>
			<tr>
				<td>나이</td>
				<td><input type="text" name="age" required="required" size="10"></td>
			</tr>
			<tr>
				<td>E-Mail</td>
				<td>
					<input type="text" name="email1" required="required" size="10">@
					<input type="text" name="email2" required="required" size="10">
					<select name="selectDomain" onchange="changeDomain(this)">
						<option value="">직접입력</option>	
						<option value="naver.com">naver.com</option>
						<option value="nate.com">nate.com</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>
					<input type="text" name="id" required="required" size="20" 
						placeholder="4-16자리 영문자,숫자 조합" onkeyup="checkId(this)">
					<span id="checkIdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
				</td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td>
					<input type="password" name="passwd" required="required" size="20" 
						placeholder="8-20자리 영문자,숫자,특수문자 조합" onkeyup="checkPasswd(this)">
					<span id="checkPasswdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="회원가입">
					<input type="button" value="취소" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>