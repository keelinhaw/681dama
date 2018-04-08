package com.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread{
	Socket socket;
	ServerThread(Socket socket){
		this.socket = socket;
	}
	public void run() {
		try {
			String message = null;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while((message = bufferedReader.readLine()) != null) {
				System.out.println("New player alert: " + message);
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
