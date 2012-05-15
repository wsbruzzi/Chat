package br.com.fiap.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

import br.com.fiap.chat.definicoes.TipoLog;
import br.com.fiap.chat.suporte.Logger;
import br.com.fiap.chat.definicoes.Acoes;

public class ClientInstance implements Runnable {

	private String ip, apelido = null;
	private Socket client;
	private ServerSocket server;
	private PrintWriter out;
	private BufferedReader in;
	private ClientesConectados cc;

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
		Logger.write(TipoLog.SERVER, "Cliente conectado: " + this.client.getRemoteSocketAddress());
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
				Logger.write(TipoLog.CLIENT, inputLine);
				processInput(inputLine);
			}
		} catch (SocketException e) {
			if(apelido != null) {
				enviaParaSala("SERVER diz: " + apelido + " saiu da sala...");
				Logger.write(TipoLog.CLIENT, "SERVER diz: " + apelido + " saiu da sala...");
			}
		} catch (IOException e) {
			Logger.write(TipoLog.SERVER, "Pau no cliente: " + apelido);
		} finally {
			fechaConexao();
		}
	}

	/**
	 * Metodo responsavel por fechar a conexao passada como parametro
	 * @param Socket s
	 */
	public void fechaConexao() {
		try {
			this.client.close();
			this.cc.retiraCliente(this.apelido);
			svEnviaListaUsuarios();
		} catch (Exception e) {
			log("Cliente saiu");
		}
	}

	/**
	 * Metodo que retorna um texto baseado no "comando" passado para ele.
	 * @param theInput
	 * @return
	 */
	public void processInput(String theInput) {

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
				String maluca = svRegistraUsuario(comando[1]);
				this.responde(Acoes.REGISTRA_USUARIO.getAcao() + maluca);
				svEnviaListaUsuarios();
				if(maluca.equals("true")) {
					enviaParaSala("SERVER diz: " + this.apelido + " entrou na sala...");
				}
			break;
		}
		
	}

	private void svEnviaListaUsuarios() {
		Map<String, ClientInstance> clientes = this.cc.getClientesConectados();
		String usuarios = Acoes.LISTA_USUARIO.getAcao();
		
		if(clientes.size() > 0) {
			for (Map.Entry<String, ClientInstance> entry : clientes.entrySet()) {
				usuarios += entry.getValue().getApelido() + ";";
			}
			for (Map.Entry<String, ClientInstance> entry : clientes.entrySet()) {
				entry.getValue().responde(usuarios);
			}
		}
	}

	private void svEnviaMensagem(String string) {
		string = Acoes.ENVIA_MENSAGEM.getAcao() + string;
		Map<String, ClientInstance> clientes = this.cc.getClientesConectados();
		
		if(clientes.size() > 0) {
			for (Map.Entry<String, ClientInstance> entry : clientes.entrySet()) {
				entry.getValue().responde(string);
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
		
		return "true";
	}
	
	private void enviaParaSala(String msg) {
		System.out.println("Sala: " + msg);
		svEnviaMensagem(msg);
	}
	
	private void log(String msg) {
		System.out.println("LOG: " + msg);
//		logger.info(msg);
	}
}
