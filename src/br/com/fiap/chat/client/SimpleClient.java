package br.com.fiap.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.fiap.chat.client.view.Receiver;
import br.com.fiap.chat.suporte.ChatException;
import br.com.fiap.chat.suporte.Conexao;
 
public class SimpleClient {
	public static void main(String[] args) throws InterruptedException {
		try {
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
			
			// testando edicao pelo github.
			Conexao conexao = new Conexao("192.168.1.190", "marcio");
			conexao.open();
			
			new Thread(new Receiver(conexao)).start();
			
			while(conexao.isRegistered()){
				conexao.sendMessage(inLine.readLine());
				conexao.getRegisteredUsers();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
