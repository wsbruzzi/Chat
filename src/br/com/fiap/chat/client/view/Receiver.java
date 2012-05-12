package br.com.fiap.chat.client.view;

import javax.swing.JTextPane;

import br.com.fiap.chat.suporte.Conexao;

public class Receiver implements Runnable{
	private final Conexao conexao;
	private final JTextPane txt;


	public Receiver(Conexao conexao, JTextPane txt) {
		this.conexao = conexao;
		this.txt = txt;
	}
	
	public Receiver(Conexao conexao) {
		this(conexao, null);
	}
	
	public void read(boolean sendToTxt){
		if (sendToTxt) {
			StringBuffer mensagensAnteriores = new StringBuffer(txt.getText());
			mensagensAnteriores.append("\n");
			mensagensAnteriores.append(this.conexao.receive());
		}else{
			System.out.println(this.conexao.receive());
		}
	}
	
	@Override
	public void run() {
		this.read(false);
		
	}

}
