<%@page import="org.owasp.esapi.ESAPI"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.sql.Connection,java.sql.DriverManager,java.sql.PreparedStatement,java.sql.ResultSet,com.server.GetPropertyValues,javax.servlet.http.HttpSession"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dama Game</title>
</head>
<body>
<%
    String name = session.getAttribute("username").toString() ;
    
	if(name == null || name.equals("")){
//todo//            name = ESAPI.User.ANONYMOUS;
            name = "anonymous";
	    String redirectURL = "./login.html";
	    response.sendRedirect(redirectURL);
	}
%>
<h1>Dama Landing Page</h1>

Welcome  <%= ESAPI.encoder().encodeForHTMLAttribute(name) %>
<br/><br/>
<!--<button type="button" onclick="./NewGame">Play New Game</button>-->
<form name="NewGame" action="./NewGame" method="POST">
    <input type="submit" value="Start New Game" name="newgame" />
</form>
<br/><br/>
<div name="mygames">
<h2>My Games</h2>
<%
	//database connection details
	GetPropertyValues properties = new GetPropertyValues();
	String dburl = properties.getPropValues("dburl");
	String dbuser = properties.getPropValues("dbuser");
	String dbpassword = properties.getPropValues("dbpassword");
    Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
    
    //Find all games that logged in user is a player in
    String myusername = (String) session.getAttribute("username");
	String findmygames = "SELECT id, startdate, player1, player2, status FROM games WHERE player1=? OR player2=?";
	PreparedStatement ps = con.prepareStatement(findmygames);
	ps.setString(1, myusername);
	ps.setString(2, myusername);
	ResultSet mygames = ps.executeQuery();
	out.println("<ul>");
	System.out.println("myusername: " + myusername);
	while (mygames.next()) {
		if (mygames.getString("player1").equals(myusername)) 
		{
			// if there are 2 players
			if (mygames.getString("player2") != null){
				out.println("<li><form action=./JoinGame method=post><button type=submit name=gameid value=" + mygames.getString("id") + ">Continue game with " + mygames.getString("player2") + "</button></form></li>");
			}
			// if there is 1 player
			else {
				out.println("<li><form action=./JoinGame method=post><button type=submit name=gameid value=" + mygames.getString("id") + ">Waiting for opponent to join game " + mygames.getString("id") + "</button></form></li>");
			}
		}
		else {
			out.println("<li><form action=./JoinGame method=post><button type=submit name=gameid value=" + mygames.getString("id") + ">Continue game with " + mygames.getString("player1") + "</button></form></li>");
		}
	}
	out.println("</ul>");
%>
</div>
<div name="opengames">
<h2>Open Games</h2>
<%
	//find all games that are in status 'new' (looking for an opponent)
	String findopengames = "SELECT id, startdate, player1 from games where status='new' AND player1<>?";
	ps.clearParameters();
	ps = con.prepareStatement(findopengames);
	ps.setString(1, myusername);
	ResultSet opengames = ps.executeQuery();
	out.println("<ul>");
	while (opengames.next()) {
			out.println("<li><form action=./JoinGame method=post><button type=submit name=gameid value=" + opengames.getString("id") + ">Join game with " + opengames.getString("player1") + "</button></form></li>");
	}
	out.println("</ul>");
%>
</div>
<form id="form1" action="./GameHistory" method="GET">
   <input type="hidden" name="username" value="${sessionScope.username}" /> 
<!--    <input type="submit" value="View My Game History" name="gamehistory"  /> -->
</form>
   <button type="submit" form="form1" >View My Game History</button>
<br/>
<br/>
<form action="./Logout" method="post">
    <input type="submit" value="Logout" />
</form>
<br/>

</body>
</html>
