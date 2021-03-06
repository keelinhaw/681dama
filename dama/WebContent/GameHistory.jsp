<%-- 
    Document   : GameHistory
    Created on : Mar 18, 2018, 8:50:46 PM
    Author     : Chadia Habib
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="javax.servlet.http.HttpSession,com.Dama.Game,com.Dama.LoadGame,java.sql.Connection,java.sql.DriverManager,java.sql.PreparedStatement,java.sql.ResultSet,com.Dama.GetPropertyValues" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Game History</title>
        <style>
input {
	width: 120px;
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
table, td, th {    
    border: 1px solid #ddd;
    text-align: left;
}

table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    padding: 15px;
}
</style>
<script>
function loadGame(event){
    console.log('td clicked'); 
    event.stopPropagation()
};
</script>
    </head>
    <body>
    <%
	if(session.getAttribute("username") == null){
	    String redirectURL = "./login.html";
	    response.sendRedirect(redirectURL);
	}
	%>
    <h2>Game History</h2>
    <ul class=nav>
		  <li class=nav><a class=nav href="landing.jsp">Home</a></li>
		  <li class=nav><a class=nav href="GameHistory.jsp">Game History</a></li>
		  <li class=nav><a class=nav href="gamestatistics.jsp">Game Statistics</a></li>
		  <li class=nav><a class=nav href="instructions.html">How To Play</a></li>
		  <li class=nav style="float:right"><form action=./Logout method=post><button class=logout type=submit name=logout>Logout</button></form></li>
	</ul class=nav>
    
<%
	String myusername = (String) session.getAttribute("username");
	//database connection details
	GetPropertyValues properties = new GetPropertyValues();
	String dburl = properties.getPropValues("dburl");
	String dbuser = properties.getPropValues("dbuser");
	String dbpassword = properties.getPropValues("dbpassword");
  	Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);

	String findcompletedgames = "SELECT id, startdate, player1, player2, win FROM games WHERE (status=? OR status=?) AND (player1=? OR player2=?)";
	PreparedStatement ps = con.prepareStatement(findcompletedgames);
	ps.setString(1, "complete");
	ps.setString(2, "forfeit");
	ps.setString(3, myusername);
	ps.setString(4, myusername);
	ResultSet completedgames = ps.executeQuery();
	//Print table header
	out.println("<table><tr><th>Date</th><th>Player1</th><th>Player2</th><th>Winner</th><th>Game</th></tr>");
	while (completedgames.next()) {
		out.println("<tr>");
		out.println("<td>" + completedgames.getString("startdate") + "</td>");
		out.println("<td>" + completedgames.getString("player1") + "</td>");
		out.println("<td>" + completedgames.getString("player2") + "</td>");
		out.println("<td>" + completedgames.getString("win") + "</td>");
		out.println("<td><form action=./ReturnToGame method=post><button type=submit name=gameid value=" + completedgames.getString("id") + ">View Game</button></form></td>");
		out.println("</tr>");
	}
	out.println("<table>");
	con.close();
%>

    </body>
</html>
