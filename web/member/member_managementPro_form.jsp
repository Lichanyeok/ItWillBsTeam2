<%@ page import="vo.BoardBean" %>
<%@ page import="vo.MemberBean" %>
<%@ page import="action.test" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%
    MemberBean article = (MemberBean) request.getAttribute("article");
%>
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
<h1>정보수정</h1>
<form action="MemberManagementProAction.me" method="post" name="managementForm" onsubmit="return checkSubmit1()">
    <table border="1">
        <tr>
            <td>이름</td>
            <td><input type="text" name="name" required="required" size="20" value="<%=article.getName()%>"></td>
        </tr>
        <tr>
            <td>아이디</td>
            <td>
                <input type="text" name="id" required="required" size="20"
                       value="<%=article.getId()%>" readonly="readonly">
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
            <td>성별</td>
            <td>
                <%
                    if (article.getGender().equals("남")) {
                %>
                <input type="radio" name="gender" value="남" checked="checked">남&nbsp;&nbsp;
                <input type="radio" name="gender" value="여">여
                <%
                } else { %>
                <input type="radio" name="gender" value="남">남&nbsp;&nbsp;
                <input type="radio" name="gender" value="여" checked="checked">여
                <%
                    }
                %>

            </td>
        </tr>
        <tr>
            <td>나이</td>
            <td><input type="text" name="age" required="required" size="10" value="<%=article.getAge()%>"></td>
        </tr>
        <tr>
            <td>E-Mail</td>
            <td>
                <%
                    String email = article.getEmail();
                    String[] array = email.split("@");
                %>
                <input type="text" name="email1" required="required" size="10" value="<%=array[0]%>">@
                <input type="text" name="email2" required="required" size="10" value="<%=array[1]%>">
                <select name="selectDomain" onchange="changeDomain1(this)">
                    <option value="">직접입력</option>
                    <option value="naver.com">naver.com</option>
                    <option value="nate.com">nate.com</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="정보수정">
                <input type="button" value="취소" onclick="history.back()">
            </td>
        </tr>
    </table>
</form>
</body>
</html>