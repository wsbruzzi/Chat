package br.com.fiap.chat.server;

import java.util.Map;
import java.util.TreeMap;

import javax.swing.SingleSelectionModel;
/**
 * 
 * Clase para definir los clientes conectados
 * Posee un diccionario que mapea los clientes a una instancia
 *
 */
public class ClientesConectados {

	private Map<String, ClientInstance> clienteMap = new TreeMap<String, ClientInstance>();
	private static ClientesConectados cc;
	
	/**
	 * Singleton
	 * @return
	 */
	public static ClientesConectados getInstance() {
		if(cc == null) {
			cc = new ClientesConectados();
		}
		
		return cc;
	}
	
	public void adicionaCliente(String nome, ClientInstance cliente) {
		clienteMap.put(nome, cliente);
	}
	
	public void retiraCliente(String nome) {
		clienteMap.remove(nome);
	}
	
	public Map<String, ClientInstance> getClientesConectados() {
		return clienteMap;
	}

	public boolean apelidoExists(String apelido) {
		return clienteMap.containsKey(apelido);
	}
}
