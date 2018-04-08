<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : GameHistory
    Created on : Mar 18, 2018, 8:50:46 PM
    Author     : Chadia Habib
--%>
<%@page import="java.sql.*" %>
<% Class.forName("org.postgresql.Driver"); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GameHistory JSP Page</title>
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

        <h1>List of completed games!</h1>
        
          <%!
            public class History {

               String URL = "jdbc:postgresql://localhost:5432/dama";
               String DBUSERNAME = "postgres";
               String PASSWORD = "ISA681DamaP!";

               Connection con = null;



               public History(){
               
                    try {
                        con = DriverManager.getConnection(URL, DBUSERNAME, PASSWORD);
                    


                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    
                }
                
                public ResultSet GetUserHistory( String username){
                
                    ResultSet resultSet = null;
                    PreparedStatement ps = null;
                    String query = "SELECT player1, startdate FROM history where player1 = ?";

                try {
                        
                 ps = con.prepareStatement(query);
		    ps.clearParameters();
		    ps.setString(1, username);
                    resultSet =  ps.executeQuery();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }                    
                
                    return resultSet;
                
                }

            }
            
            %>
            <% 
                History gameHist = new History();
                ResultSet games = gameHist.GetUserHistory(session.getAttribute("username").toString());
//                  ResultSet games = gameHist.GetUserHistory("sam");
                
            %>
            
            <table border="1">
                <tbody>
                    <tr>
                        <td>for debugging</td>
                        <td>Player1</td>
                        <td>Game Date</td>
                    </tr>
                    <% while (games.next()) {
                    %>
                    <tr>
                        <td>game record</td>
                        <td><%= games.getString("player1") %></td>
                        <td><%= games.getDate("startdate") %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

    </body>
</html>
