package br.com.fiap.chat.client.view;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.ajt.rsa.RSA;

import br.com.fiap.chat.definicoes.Acoes;
import br.com.fiap.chat.server.ClientInstance;
import br.com.fiap.chat.suporte.Conexao;

public class Receiver implements Runnable{
	private final Conexao conexao;
	private final JTextArea txtMensagens;
	private final JTextArea txtListaUsuarios;
	
	
	public Receiver(Conexao conexao, JTextArea txpHistorico, JTextArea txpListaUsuarios) {
		this.conexao = conexao;
		this.txtMensagens = txpHistorico;
		this.txtListaUsuarios = txpListaUsuarios;
	}
	
	public Receiver(Conexao conexao) {
		this(conexao, null, null);
	}
	
	public void read(boolean sendToTxt){
		processInput(this.conexao.receive(), sendToTxt);
	}
	
	public void processInput(String mensagemRecebida, boolean sendToTxt){
		if(mensagemRecebida.split(":", 2)==null)
			return;
		String[] partesDaMensagem = mensagemRecebida.split(":", 2);
		
		switch (Acoes.valueOf(partesDaMensagem[0])) {
			case ENVIA_MENSAGEM:
				if (sendToTxt) {
					StringBuffer mensagensAnteriores = new StringBuffer(txtMensagens.getText());
					mensagensAnteriores.append("\n");
					System.out.println("al llegar comando: "+partesDaMensagem[1]);
					String[] partes = partesDaMensagem[1].split("-");
					
					if(partes.length >1){///Esta cifrado 
						
						BigInteger cifrado = new BigInteger(partes[1]);
						BigInteger plainText = conexao.rsa.decrypt(cifrado);
						
						String mensajeDescifrado = new String(plainText.toByteArray());
						
						
						BigInteger plaintext2 = new BigInteger(mensajeDescifrado.getBytes());
						BigInteger ciphertext2 = conexao.rsa.encrypt(plaintext2);
						
						//txtMensagens.setText(partes[0]+"-"+mensajeDescifrado);
						if(ciphertext2.equals(cifrado)){
							mensagensAnteriores.append(mensajeDescifrado);
							txtMensagens.setText(mensagensAnteriores.toString());
						}
							
						
					}else{
						mensagensAnteriores.append(partes[0]);
						txtMensagens.setText(mensagensAnteriores.toString());
					}
					
					
					
					
				}else{
					System.out.println(partesDaMensagem[1]);
				}
			break;
			case LISTA_USUARIO:
				if (sendToTxt) {
					StringBuffer usuarios = new StringBuffer();
					
					for (String nomeUsuario : partesDaMensagem[1].split(";")) {						
						usuarios.append(nomeUsuario);
						usuarios.append("\n");
						txtListaUsuarios.setText(usuarios.toString());
					}
				}else{
					for (String nomeUsuario : partesDaMensagem[1].split(";")) {						
						System.out.println(nomeUsuario);
					}
				}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void run() {
		while(this.conexao.isRegistered())
			this.read(true);
		
	}

}
