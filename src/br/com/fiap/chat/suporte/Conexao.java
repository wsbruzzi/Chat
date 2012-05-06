package br.com.fiap.chat.suporte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Conexao {
	private String ipServidor;
	private String apelido;
	private final int porta;
	private Socket conexao;
	private PrintWriter writer;
	private BufferedReader reader;

	public Conexao(String ipServidor, String apelido) {
		this.ipServidor = ipServidor;
		this.apelido = apelido;
		this.porta = 4447;
	}
	
	public void open() throws ChatException {
		if(this.conexao == null){
			try{
				this.conexao = new Socket(this.ipServidor, this.porta);
				this.writer = new PrintWriter(this.conexao.getOutputStream(), true);
				this.reader = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
			}catch (Exception e) {
				throw new ChatException("Erro ao abrir conexão: " + e.getMessage());
			}
			
			this.registraUsuario();
		}
	}
	
	public void open(String ipServidor, String apelido) throws ChatException {
		this.ipServidor = ipServidor;
		this.apelido = apelido;
		
		this.open();
	}
	
	public void close(){
		try {
			this.conexao.close();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage());
		}
	}
		
	public boolean isLogado(){
		return !this.conexao.isClosed();
	}
	
	public String receive() {
		try {
			return this.reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao receber mensagem: " + e.getMessage());
		}
	}
	
	public void sendMessage(String mensagem){
		if(!"".equals(mensagem.trim())){
			StringBuilder msg = new StringBuilder(this.apelido);
			msg.append(" diz: ");
			msg.append(mensagem);
			
			this.sendCommand(msg.toString());
		}
	}
	
	private void registraUsuario() throws ChatException{
		this.sendCommand("registraUsuario:" + this.apelido);
		
		if(!"true".equalsIgnoreCase(this.receive())){
			this.close();
			throw new ChatException("Erro ao registrar usuário");
		}
	}
	
	private void sendCommand(String command){
		this.writer.write(command + "\n");
		this.writer.flush();
	}
}
