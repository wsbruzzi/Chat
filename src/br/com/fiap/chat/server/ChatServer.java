package br.com.fiap.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4447);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4447.");
			System.exit(1);
		}

		while(true) {
			
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				ClientInstance ci = new ClientInstance(serverSocket, clientSocket);
				new Thread(ci).start();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		}
	}
}
