<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	if(session.getAttribute("username") != null){

	}
	else {
	    String redirectURL = "./login.html";
	    response.sendRedirect(redirectURL);
	}

%>
<h1>Dama Landing Page</h1>
<button type="button" onclick="./NewGame">New Game</button>
<h2>My Games</h2>
<form action="./Logout" method="post">
    <input type="submit" value="Logout" />
</form>
<br/>
My session is ${sessionScope.username}
</body>

</html>