package br.com.fiap.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;


import br.com.fiap.chat.definicoes.Acoes;

public class ClientInstance implements Runnable {

	private String ip, apelido = null;
	private Socket client;
	private ServerSocket server;
	private PrintWriter out;
	private BufferedReader in;
	private ClientesConectados cc;
	private Logger logger = Logger.getLogger(ClientInstance.class);

	/**
	 * Inicia o cliente
	 * @param ServerSocket serverSocket
	 * @param Socket client
	 */
	public ClientInstance(ServerSocket serverSocket, Socket client, ClientesConectados cc) {
		this.server = serverSocket;
		this.client = client;
		this.cc = cc;

		try {
			out = new PrintWriter(this.client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		} catch (Exception e) {}
	}

	@Override
	public void run() {
		log("Cliente conectado: " + this.client.getRemoteSocketAddress());
		serve();
	}

	/**
	 * Envia uma mensagem ao cliente conectado
	 * @param resposta
	 */
	public void responde(String resposta) {
		out.println(resposta);
	}

	
	/**
	 * Metodo responsavel por receber mensagens do cliente.
	 */
	public void serve() {

		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("sair:")) {
					fechaConexao(this.client);
					throw new SocketException();
				}
				enviaParaSala(inputLine);
				responde(processInput(inputLine));
			}
		} catch (SocketException e) {
			enviaParaSala(apelido + " saiu da sala...");
		} catch (IOException e) {
			log("Pau no cliente: " + apelido);
		}
	}

	/**
	 * Metodo responsavel por fechar a conexao passada como parametro
	 * @param Socket s
	 */
	public void fechaConexao(Socket s) {
		try {
			s.close();
		} catch (Exception e) {
			log("Cliente saiu");
		}
	}

	/**
	 * Metodo que retorna um texto baseado no "comando" passado para ele.
	 * @param theInput
	 * @return
	 */
	public String processInput(String theInput) {

		String theOutput = null;
		String[] comando = theInput.split(":");
		
		if (Acoes.REGISTRA_USUARIO.getAcao().equals(comando[0])) {
			theOutput = svRegistraUsuario(comando[1]);
		} else if(Acoes.ENVIA_MENSAGEM.getAcao().equals(comando[0])) {
			svEnviaMensagem(comando[1]);
		} else if(Acoes.DESCONECTA.getAcao().equals(comando[0])) {
			fechaConexao(this.client);
		}

		return theOutput;
	}

	private void svEnviaMensagem(String string) {
		
	}

	private String svRegistraUsuario(String comando) {

		if(this.cc.apelidoExists(comando)) {
			log("Cliente com o apelido \"" + comando + "\" ja existe");
			return "false";
		}
		
		this.apelido = comando;
		this.cc.adicionaCliente(this.apelido, this);
		
		enviaParaSala(this.apelido + " entrou na sala...");
		return "true";
	}
	
	private void enviaParaSala(String msg) {
		// TODO: depois que implementar o "broadcast"
		System.out.println("SALA: " + msg);
	}
	
	private void log(String msg) {
		// System.out.println("LOG: " + msg);
		logger.info(msg);
	}
}
