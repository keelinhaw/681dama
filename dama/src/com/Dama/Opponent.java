/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Opponent{
	public Opponent(){
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		String name = "Keelin";
		Socket socket = new Socket("localhost", 7777);
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));
		while(true){
			String readerInput = bufferedReader.readLine();
			printWriter.println(name + ": " + readerInput);
		}
	}
	public void newOpponent (String username) throws IOException {
		Socket socket = new Socket("localhost", 7777);
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
		//BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));
		printWriter.println(username + " has joined the Dama Game Server!");
		//while(true){
			//String readerInput = bufferedReader.readLine();
			//printWriter.println(username + " has joined the Dama Game Server!");
		//}
	}

}
