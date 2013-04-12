package br.com.fiap.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.fiap.chat.definicoes.TipoLog;
import br.com.fiap.chat.suporte.Logger;

public class ChatServer {
	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;
		ClientesConectados cc = new ClientesConectados();
		int porta = 4447;

		Logger.write(TipoLog.SERVER, "Conectando...");
		try {
			serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			Logger.write(TipoLog.SERVER, "No se pudo escuchar el puerto: " + porta);
			System.exit(1);
		}

		while(true) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				ClientInstance ci = new ClientInstance(serverSocket, clientSocket, cc);
				new Thread(ci).start();
			} catch (IOException e) {
				Logger.write(TipoLog.SERVER, "No se pudo acceptar el cliente");
				System.exit(1);
			}
		}
	}
}
