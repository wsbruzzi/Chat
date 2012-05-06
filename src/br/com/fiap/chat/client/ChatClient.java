package br.com.fiap.chat.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ChatClient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5646894515650330184L;
	
	private JPanel pnlPrincipal, pnlChat;
	private JButton btnOk;
	private JTextField txtTexto, txtUsuario, txtIp;
	private JLabel lblMensagem;
	
	public void initialize() {
		initPnlPrincipal();
		
		this.add(pnlPrincipal);
		this.setTitle("Borracharia do swing");
		this.setSize(1024, 768);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private void initPnlPrincipal() {
		pnlPrincipal = new JPanel();
		pnlPrincipal.setBackground(Color.WHITE);
		
		btnOk = new JButton("Entrar");
		txtTexto = new JTextField(20);
		txtUsuario = new JTextField(15);
		txtIp = new JTextField(15);
		
		pnlPrincipal.add(new JLabel("Apelido"));
		pnlPrincipal.add(txtUsuario);
		pnlPrincipal.add(new JLabel("IP"));
		pnlPrincipal.add(txtIp);
		pnlPrincipal.add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				entraChat();
			}
		});
		
		txtTexto.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					lblMensagem.setText(txtTexto.getText());
					txtTexto.setText("");
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
	}
	
	/**
	 * Faz a validacao do usuario para entrar no chat
	 * 
	 * ve se nao existe o usuario com o mesmo apelido
	 * ve se o IP que esta tentando acessar aceita conexoes na porta informada
	 */
	private void entraChat() {
		initPnlChat();
	}
	
	private void initPnlChat() {
		
		System.out.println("entrou no chat");
		
		pnlChat = new JPanel();
		pnlChat.add(new JTextField(50));
		pnlPrincipal.setVisible(false);
		pnlChat.setVisible(true);
		
		this.add(pnlChat);
	}
	
	public static void main(String[] args) {
		new ChatClient().initialize();
	}
}
