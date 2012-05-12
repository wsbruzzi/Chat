package br.com.fiap.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

//import org.apache.log4j.Logger;


import br.com.fiap.chat.definicoes.Acoes;

public class ClientInstance implements Runnable {

	private String ip, apelido = null;
	private Socket client;
	private ServerSocket server;
	private PrintWriter out;
	private BufferedReader in;
	private ClientesConectados cc;
//	private Logger logger = Logger.getLogger(ClientInstance.class);

	/**
	 * Inicia o cliente
	 * @param ServerSocket serverSocket
	 * @param Socket client
	 */
	public ClientInstance(ServerSocket serverSocket, Socket client, ClientesConectados cc) {
		this.server = serverSocket;
		this.client = client;
		this.cc = ClientesConectados.getInstance();

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

	public String getApelido() {
		return this.apelido;
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
		
		String inputLine;
		
		try {
			while ((inputLine = in.readLine()) != null) {
				enviaParaSala(inputLine);
				responde(processInput(inputLine));
			}
		} catch (SocketException e) {
			enviaParaSala(apelido + " saiu da sala...");
		} catch (IOException e) {
			log("Pau no cliente: " + apelido);
		} 
		/*finally {
			this.cc.retiraCliente(this.apelido);
		}*/
	}

	/**
	 * Metodo responsavel por fechar a conexao passada como parametro
	 * @param Socket s
	 */
	public void fechaConexao() {
		try {
			this.client.close();
			this.cc.retiraCliente(this.apelido);
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
		String[] comando = theInput.split(":", 2);
		
		switch(Acoes.valueOf(comando[0])) {
			case DESCONECTA:
				fechaConexao();
			break;
			
			case ENVIA_MENSAGEM:
				svEnviaMensagem(comando[1]);
			break;
			
			case LISTA_USUARIO:
				svEnviaListaUsuarios();
			break;
			
			case REGISTRA_USUARIO:
				theOutput = Acoes.REGISTRA_USUARIO.getAcao() + svRegistraUsuario(comando[1]);
			break;
		}
		
		return theOutput;
	}

	private void svEnviaListaUsuarios() {
		Map<String, ClientInstance> clientes = this.cc.getClientesConectados();
		String usuarios = Acoes.LISTA_USUARIO.getAcao();
		
		if(clientes.size() > 0) {
			for (Map.Entry<String, ClientInstance> entry : clientes.entrySet()) {
				usuarios += entry.getValue().getApelido() + ";";
			}
		}
		
		enviaParaSala(usuarios);
		this.responde(usuarios);
	}

	private void svEnviaMensagem(String string) {
		
		Map<String, ClientInstance> clientes = this.cc.getClientesConectados();

		for (Map.Entry<String, ClientInstance> entry : clientes.entrySet()) {
			
			if(entry.getKey() != this.apelido) {
				entry.getValue().responde(string);
			} else {
				entry.getValue().responde("[VC] " + string);
			}
		}
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
		System.out.println("LOG: " + msg);
//		logger.info(msg);
	}
}
