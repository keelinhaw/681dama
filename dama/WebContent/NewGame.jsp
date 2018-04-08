<%-- 
    Document   : NewGame
    Created on : Mar 18, 2018, 8:49:01 PM
    Author     : Chadia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"
		import="com.server.GameServer,java.io.IOException"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style.css">
        <title>New Game JSP Page</title>
    </head>
    <body>
        <h2>Let's play Dama!</h2>
        Player 1:	${sessionScope.username} <br/>
        Player 2: 	${sessionScope.opponent}<br/>
        <form name="EndGame" action="./EndGame" method="POST">
			<input type="submit" value="End Current Game" name="endgame" />
		</form>
       
		<div class="checker white_checker" style="display:none"> </div>
		<div class="checker black_checker" style="display:none"> </div>
		
		<div class="square" style="display: none" id ="ht"> </div>
		<div class="black_background" id="black_background"> </div>
		
			<div class="score" id="score">
				<br>
			</div>
		<div class="table" id="table">
		
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="clear_float"> </div>
			
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="clear_float"> </div>
		
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="clear_float"> </div>
		
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="clear_float"> </div>
		
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="clear_float"> </div>
		
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="clear_float"> </div>
		
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="clear_float"> </div>
		
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="square white_square"> </div>
			<div class="square black_square"> </div>
			<div class="clear_float"> </div>
		</div>
    </body>
</html>