package com.server;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	
	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	public static final int port = 7777;

	public GameServer(){
	}

	public static void main(String[] args) throws IOException{
		new GameServer().runServer();
	}
	public void runServer() throws IOException {
		System.out.println("Game server is up.");
		serverSocket = new ServerSocket(port);
		System.out.println("Waiting for another player to join...");
		while (true) {
			socket = serverSocket.accept();
			new ServerThread(socket).start();
		}
	}
	public void newGame() throws IOException{
		System.out.println("New game has started.");
		serverSocket = new ServerSocket(port);
		System.out.println("Waiting for another player to join...");
		//while (true) {
		socket = serverSocket.accept();		
		new ServerThread(socket).start();
		return;
		//}
	}
	public void endGame() throws IOException {
		serverSocket.close();
		System.out.println("Game has ended...");
	}
}
