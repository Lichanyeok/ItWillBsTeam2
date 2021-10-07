<%@ page import="dao.MemberDAO" %>
<%@ page import="vo.MemberBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        header {
            text-align: right;
        }

        body {
            text-align: center;
        }
    </style>
</head>
<body>
<header>
<%
    String sId = (String)session.getAttribute("sId");
    if(sId == null) {
%>
<div id="login">
    <a href="MemberLoginForm.me">login</a> | <a href="MemberJoinForm.me">join</a>
</div>
<%
} else {
    session.setAttribute("sId", sId);
%>
<div id="login">
    <%=sId %>님 | <a href="MemberLogoutForm.me">logout</a> | <a href="MemberManagementForm.me">회원정보관리</a>
</div>
<%
    }
%>
</header>
<h1>ItWillBsTeam2 메인</h1>
<h3><a href="BoardWriteForm.bo">글쓰기 페이지</a></h3>
<h3><a href="BoardList.bo">글삭제 페이지</a></h3>

</body>
</html>
