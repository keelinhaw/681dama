<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="javax.servlet.http.HttpSession,com.Dama.Game,com.Dama.LoadGame" %>
<%
	Game gameBean = (Game) session.getAttribute("gameBean");
	Long gameid = gameBean.getGameid();
	LoadGame game = new LoadGame();
	gameBean = game.getGame(gameid);
	session.setAttribute("gameBean", gameBean);
%>