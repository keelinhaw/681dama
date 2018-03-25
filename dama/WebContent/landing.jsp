<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dama Game</title>
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
<h2> don't forget to html encode the username being displayed</h2>
Welcome ${sessionScope.username}
<br/><br/>
<!--<button type="button" onclick="./NewGame">Play New Game</button>-->
<form name="NewGame" action="./NewGame.jsp" method="POST">
    <input type="submit" value="Play New Game" name="newgame" />
</form>
<br/><br/>
<form action="GameHistory.jsp" method="POST">
<!--    <input type="text" name="username" value="${sessionScope.username}" /> -->
    <input type="submit" value="View My Game History" name="gamehistory" />
</form>
<br/><br/>
<form action="./Logout" method="post">
    <input type="submit" value="Logout" />
</form>
<br/>

</body>
</html>
