package br.com.fiap.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {
		Socket clientSocket;
		
		try {
		    ServerSocket serverSocket = new ServerSocket(4444);
		    while(true) {
		    	
		    	clientSocket = serverSocket.accept();
		    }
		} 
		catch (IOException e) {
		    System.out.println("Could not listen on port: 4444");
		    System.exit(-1);
		}
	}
}
