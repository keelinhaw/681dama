<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="javax.servlet.http.HttpSession,com.Dama.Game,com.Dama.LoadGame" %>
<%
	if(session.getAttribute("username") != null){
        response.setHeader("Location", "./login.html");
	}
	Game gameBean = (Game) session.getAttribute("gameBean");
	if (gameBean != null){
		Long gameid = gameBean.getGameid();
		LoadGame game = new LoadGame();
		String playerturn = gameBean.getPlayerturn();
		gameBean = game.getGame(gameid);
		session.setAttribute("gameBean", gameBean);
	}
	else {
	    String redirectURL = "./landing.html";
        response.setHeader("Location", "./landing.jsp");
	}
%>