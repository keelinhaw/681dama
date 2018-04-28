<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.sql.Connection,java.sql.DriverManager,java.sql.PreparedStatement,java.sql.ResultSet,com.Dama.GetPropertyValues,javax.servlet.http.HttpSession"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet"> 
<style>
input {
	width: 100%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 6px;
    margin-bottom: 16px;
}

/* Style the submit button */
input[type=submit] {
    background-color: #4CAF50;
    color: white;
}
input[type=submit]:hover {
    background-color: #63d368;
}
button.logout {
     background:none;
     color:white;
     border:none; 
     font: inherit;
     cursor: pointer;
     text-align: center;
     padding: 14px 16px;
}
button {
    background-color: #4CAF50;
    color: white;
    	width: 50%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
button:hover {
    background-color: #63d368;
}
ul.nav {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
}
li.nav {
    float: left;
}
li.nav a.nav {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}
</style>
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
<h2>Welcome to Dama!</h2>
<ul class=nav>
  <li class=nav><a class=nav href="landing.jsp">Home</a></li>
  <li class=nav><a class=nav href="GameHistory.jsp">Game History</a></li>
  <li class=nav><a class=nav href="gamestatistics.jsp">Game Statistics</a></li>
  <li class=nav><a class=nav href="instructions.html">How To Play</a></li>
  <li class=nav style="float:right"><form action=./Logout method=post><button class=logout type=submit name=logout>Logout</button></form></li>
</ul class=nav>
<!--<button type="button" onclick="./NewGame">Play New Game</button>-->
<form name="NewGame" action="./NewGame" method="POST">
    <input type="submit" value="Start New Game" name="newgame" />
</form>
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
	String findmygames = "SELECT id, startdate, player1, player2, status FROM games WHERE (player1=? OR player2=?) AND status<>'complete'";
	PreparedStatement ps = con.prepareStatement(findmygames);
	ps.setString(1, myusername);
	ps.setString(2, myusername);
	ResultSet mygames = ps.executeQuery();
	while (mygames.next()) {
		if (mygames.getString("player1").equals(myusername)) 
		{
			// if there are 2 players
			if (mygames.getString("player2") != null){
				out.println("<form action=./ReturnToGame method=post><button type=submit name=gameid value=" + mygames.getString("id") + ">Continue game with " + mygames.getString("player2") + "</button></form><br/>");
			}
			// if there is 1 player
			else {
				out.println("<form action=./JoinGame method=post><button type=submit name=gameid value=" + mygames.getString("id") + ">Waiting for opponent to join game</button></form><br/>");
			}
		}
		else {
			out.println("<form action=./ReturnToGame method=post><button type=submit name=gameid value=" + mygames.getString("id") + ">Continue game with " + mygames.getString("player1") + "</button></form></br/>");
		}
	}
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
	while (opengames.next()) {
			out.println("<form action=./JoinGame method=post><button type=submit name=gameid value=" + opengames.getString("id") + ">Join game with " + opengames.getString("player1") + "</button></form></br/>");
	}
%>
</div>

</body>
</html>
