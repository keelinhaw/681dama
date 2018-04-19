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
        <script src="./jquery-3.2.1.js" type="text/javascript"></script>
        <link href="https://fonts.googleapis.com/css?family=+Mono" rel="stylesheet">
        <link rel="stylesheet" href="style.css">
        <title>New Game JSP Page</title>
    </head>
    <body>
        <h2>Let's play Dama!</h2>
        Player 1:	${sessionScope.username} <br/>
        Player 2: 	${sessionScope.opponent} <br/><br/>
        Game#: ${gameBean.gameid} 
		<div name="moves">
		<form action="./MyTurn" method="post">
        		Piece from: <input type="text" name="oldLocation" /><br/>
        		Piece to: <input type="text" name="newLocation" /><br/>
        		<input type="submit" value="Submit" />
        	</form>
        </div>
		<div class="gameBoard">	
&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;3&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;&nbsp;5&nbsp;&nbsp;&nbsp;6&nbsp;&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;8</br>		
&nbsp;&nbsp;-------------------------------</br>
A|&nbsp;${gameBean.a1}&nbsp;|&nbsp;${gameBean.a2}&nbsp;|&nbsp;${gameBean.a3}&nbsp;|&nbsp;${gameBean.a4}&nbsp;|&nbsp;${gameBean.a5}&nbsp;|&nbsp;${gameBean.a6}&nbsp;|&nbsp;${gameBean.a7}&nbsp;|&nbsp;${gameBean.a8}&nbsp;|</br>
&nbsp;+---+---+---+---+---+---+---+---+</br>
B|&nbsp;${gameBean.b1}&nbsp;|&nbsp;${gameBean.b2}&nbsp;|&nbsp;${gameBean.b3}&nbsp;|&nbsp;${gameBean.b4}&nbsp;|&nbsp;${gameBean.b5}&nbsp;|&nbsp;${gameBean.b6}&nbsp;|&nbsp;${gameBean.b7}&nbsp;|&nbsp;${gameBean.b8}&nbsp;|</br>
&nbsp;+---+---+---+---+---+---+---+---+</br>
C|&nbsp;${gameBean.c1}&nbsp;|&nbsp;${gameBean.c2}&nbsp;|&nbsp;${gameBean.c3}&nbsp;|&nbsp;${gameBean.b4}&nbsp;|&nbsp;${gameBean.c5}&nbsp;|&nbsp;${gameBean.c6}&nbsp;|&nbsp;${gameBean.c7}&nbsp;|&nbsp;${gameBean.c8}&nbsp;|</br>
&nbsp;+---+---+---+---+---+---+---+---+</br>
D|&nbsp;${gameBean.d1}&nbsp;|&nbsp;${gameBean.d2}&nbsp;|&nbsp;${gameBean.d3}&nbsp;|&nbsp;${gameBean.d4}&nbsp;|&nbsp;${gameBean.d5}&nbsp;|&nbsp;${gameBean.d6}&nbsp;|&nbsp;${gameBean.d7}&nbsp;|&nbsp;${gameBean.d8}&nbsp;|</br>
&nbsp;+---+---+---+---+---+---+---+---+</br>
E|&nbsp;${gameBean.e1}&nbsp;|&nbsp;${gameBean.e2}&nbsp;|&nbsp;${gameBean.e3}&nbsp;|&nbsp;${gameBean.e4}&nbsp;|&nbsp;${gameBean.e5}&nbsp;|&nbsp;${gameBean.e6}&nbsp;|&nbsp;${gameBean.e7}&nbsp;|&nbsp;${gameBean.e8}&nbsp;|</br>
&nbsp;+---+---+---+---+---+---+---+---+</br>
F|&nbsp;${gameBean.f1}&nbsp;|&nbsp;${gameBean.f2}&nbsp;|&nbsp;${gameBean.f3}&nbsp;|&nbsp;${gameBean.f4}&nbsp;|&nbsp;${gameBean.f5}&nbsp;|&nbsp;${gameBean.f6}&nbsp;|&nbsp;${gameBean.f7}&nbsp;|&nbsp;${gameBean.f8}&nbsp;|</br>
&nbsp;+---+---+---+---+---+---+---+---+</br>
G|&nbsp;${gameBean.g1}&nbsp;|&nbsp;${gameBean.g2}&nbsp;|&nbsp;${gameBean.g3}&nbsp;|&nbsp;${gameBean.g4}&nbsp;|&nbsp;${gameBean.g5}&nbsp;|&nbsp;${gameBean.g6}&nbsp;|&nbsp;${gameBean.g7}&nbsp;|&nbsp;${gameBean.g8}&nbsp;|</br>
&nbsp;+---+---+---+---+---+---+---+---+</br>
H|&nbsp;${gameBean.h1}&nbsp;|&nbsp;${gameBean.h2}&nbsp;|&nbsp;${gameBean.h3}&nbsp;|&nbsp;${gameBean.h4}&nbsp;|&nbsp;${gameBean.h5}&nbsp;|&nbsp;${gameBean.h6}&nbsp;|&nbsp;${gameBean.h7}&nbsp;|&nbsp;${gameBean.h8}&nbsp;|</br>
&nbsp;&nbsp;-------------------------------	</br>
		</div>
    </body>
</html>