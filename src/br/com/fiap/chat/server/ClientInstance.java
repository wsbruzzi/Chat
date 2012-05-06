package br.com.fiap.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.TreeMap;

public class ClientInstance implements Runnable {

	private String ip, apelido = null;
	private Socket client;
	private ServerSocket server;
	private PrintWriter out;
	private BufferedReader in;

	public ClientInstance(ServerSocket serverSocket, Socket client) {
		this.server = serverSocket;
		this.client = client;

		try {
			out = new PrintWriter(this.client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		System.out.println("Cliente conectado: " + this.client.getRemoteSocketAddress());
		serve();
	}

	public void responde(String resposta) {
		out.println(resposta);
	}

	public void serve() {

		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				responde("true");
				System.out.println(inputLine);
				if (inputLine.equals("/sair")) {
					break;
				}

				processInput(inputLine);
			}
		} catch (SocketException e) {
			System.out.println(apelido + " saiu da sala...");
		} catch (IOException e) {
			System.out.println("Cliente quebrou: " + apelido);
		}
	}

	public void fechaConexao(Socket s) {
		try {
			// responde("morreDiabo");
			// s.close();
		} catch (Exception e) {
			System.out.println("Cliente saiu");
		}
	}

	public String processInput(String theInput) {

		String theOutput = null;
		String[] comando = theInput.split(":");

		if (comando[0].equals("registraUsuario")) {
			this.apelido = comando[1];
			System.out.println(this.apelido + " entrou na sala...");
			theOutput = "false";
			fechaConexao(client);
		}

		theOutput = theInput;
		return theOutput;
	}
}
