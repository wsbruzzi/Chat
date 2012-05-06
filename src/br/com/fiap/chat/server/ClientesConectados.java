package br.com.fiap.chat.server;

import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class ClientesConectados {

	private Map<String, Socket> clienteMap = new TreeMap<String, Socket>();
	
	public ClientesConectados(String nome, Socket cliente) {
		clienteMap.put(nome, cliente);
	}
	
	public void retiraCliente(String nome) {
		clienteMap.remove(nome);
	}
	
	public Map getClientesConectados() {
		return clienteMap;
	}
}
