package br.com.fiap.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;
		ClientesConectados cc = new ClientesConectados();
		int porta = 4447;
		
		try {
			serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			System.err.println("Nao foi possivel ouvir a porta: " + porta);
			System.exit(1);
		}

		while(true) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				ClientInstance ci = new ClientInstance(serverSocket, clientSocket, cc);
				new Thread(ci).start();
			} catch (IOException e) {
				System.err.println("Falha ao aceitar o cliente");
				System.exit(1);
			}
		}
	}
}
