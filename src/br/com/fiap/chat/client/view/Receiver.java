package br.com.fiap.chat.client.view;

import javax.swing.JTextPane;

import br.com.fiap.chat.definicoes.Acoes;
import br.com.fiap.chat.suporte.Conexao;

public class Receiver implements Runnable{
	private final Conexao conexao;
	private final JTextPane txtMensagens;
	private final JTextPane txtListaUsuarios;


	public Receiver(Conexao conexao, JTextPane txtMensagens, JTextPane txtListaUsuarios) {
		this.conexao = conexao;
		this.txtMensagens = txtMensagens;
		this.txtListaUsuarios = txtListaUsuarios;
	}
	
	public Receiver(Conexao conexao) {
		this(conexao, null, null);
	}
	
	public void read(boolean sendToTxt){
		processInput(this.conexao.receive(), sendToTxt);
	}
	
	public void processInput(String mensagemRecebida, boolean sendToTxt){
		String[] partesDaMensagem = mensagemRecebida.split(":", 2);
		
		switch (Acoes.valueOf(partesDaMensagem[0])) {
			case ENVIA_MENSAGEM:
				if (sendToTxt) {
					StringBuffer mensagensAnteriores = new StringBuffer(txtMensagens.getText());
					mensagensAnteriores.append("\n");
					mensagensAnteriores.append(partesDaMensagem[1]);
					txtMensagens.setText(mensagensAnteriores.toString());
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
