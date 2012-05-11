package br.com.fiap.chat.client.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import br.com.fiap.chat.suporte.Conexao;

public class Chat extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private JPanel      pnlChat;
	private JButton     btnEnvia;
	private JTextField  txtMensagem;
	private JTextPane   txpHistorico;
	//private String      historico = ""; 
	private KeyListener kevBindEnter;
	
	private Conexao     conexao;
	
	public void initialize(Conexao conexao){
		this.conexao = conexao;
		
		initPnlChat();
		
		this.setTitle("Chat Fiap");
		this.setSize(300, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(pnlChat);
	}
	
	private void initPnlChat(){
		SpringLayout layout = new SpringLayout();
		
		pnlChat = new JPanel();
		pnlChat.setBackground(Color.WHITE);
		
		txpHistorico = new JTextPane();
		txtMensagem  = new JTextField(15);
		btnEnvia     = new JButton("Enviar");
		
		Border txpBorder = BorderFactory.createLineBorder(Color.black);
		txpHistorico.setBorder(txpBorder);
		
		//histórico
		layout.putConstraint(SpringLayout.NORTH, txpHistorico,   5, SpringLayout.NORTH, pnlChat);
		layout.putConstraint(SpringLayout.SOUTH, txpHistorico, -35, SpringLayout.SOUTH, pnlChat);
		layout.putConstraint(SpringLayout.EAST , txpHistorico,  -5, SpringLayout.EAST , pnlChat);
		layout.putConstraint(SpringLayout.WEST , txpHistorico,   5, SpringLayout.WEST , pnlChat);
		
		//Field Mensagem
		layout.putConstraint(SpringLayout.NORTH, txtMensagem,  5, SpringLayout.SOUTH, txpHistorico);
		layout.putConstraint(SpringLayout.SOUTH, txtMensagem, -5, SpringLayout.SOUTH, pnlChat);
		layout.putConstraint(SpringLayout.EAST , txtMensagem, -5, SpringLayout.WEST , btnEnvia);
		layout.putConstraint(SpringLayout.WEST , txtMensagem,  5, SpringLayout.WEST , pnlChat);
		
		//Button Envia
		layout.putConstraint(SpringLayout.SOUTH, btnEnvia, -5, SpringLayout.SOUTH, pnlChat);
		layout.putConstraint(SpringLayout.EAST , btnEnvia, -5, SpringLayout.EAST , pnlChat);
		

		pnlChat.setLayout(layout);
		
		pnlChat.add(txpHistorico);
		pnlChat.add(txtMensagem);
		pnlChat.add(btnEnvia);
		
		adicionaListeners();
	}
	
	private void adicionaListeners(){
		
		btnEnvia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enviaMensagem();
			}
		});
		
		kevBindEnter = new KeyListener() {	
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10)
					enviaMensagem();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		};
		txtMensagem.addKeyListener(kevBindEnter);
		btnEnvia.addKeyListener(kevBindEnter);
		
	}
	
	
	
	private void enviaMensagem(){
		//historico += txtMensagem.getText() + "\n";
		conexao.sendMessage(txtMensagem.getText());
		//txpHistorico.setText(historico);
		txtMensagem.setText("");
	}
}
