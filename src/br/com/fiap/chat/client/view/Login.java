package br.com.fiap.chat.client.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Login extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel pnlLogin;
	private JButton btnOk;
	private JTextField txtUsuario, txtIp;
	private JLabel lblUsuario, lblIp;
	//private JLabel lblMensagem;
	
	public void initialize(){
		initPnlLogin();

		this.setTitle("Login Chat Fiap");
		this.setSize(400,120);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(pnlLogin);
	}
	
	private void initPnlLogin(){
		SpringLayout layout = new SpringLayout();
		
		pnlLogin = new JPanel();
		pnlLogin.setBackground(Color.WHITE);

		lblUsuario = new JLabel("Apelido");
		lblIp      = new JLabel("IP do servidor"); 
		txtUsuario = new JTextField(15);
		txtIp      = new JTextField(15);
		btnOk      = new JButton("Entrar");

		//Label Usuário
		layout.putConstraint(SpringLayout.NORTH, lblUsuario, 5, SpringLayout.NORTH, pnlLogin);
		layout.putConstraint(SpringLayout.WEST , lblUsuario, 5, SpringLayout.WEST , pnlLogin);
		
		//Field Usuário 
		layout.putConstraint(SpringLayout.NORTH, txtUsuario,   5, SpringLayout.NORTH, pnlLogin);
		layout.putConstraint(SpringLayout.EAST , txtUsuario,  -5, SpringLayout.EAST , pnlLogin);
		layout.putConstraint(SpringLayout.WEST , txtUsuario, 140, SpringLayout.WEST , pnlLogin);
		
		//Label Ip
		layout.putConstraint(SpringLayout.NORTH, lblIp, 5, SpringLayout.SOUTH, lblUsuario);
		layout.putConstraint(SpringLayout.WEST , lblIp, 5, SpringLayout.WEST , pnlLogin);
		
		//Field Ip
		layout.putConstraint(SpringLayout.NORTH, txtIp,   5, SpringLayout.SOUTH, txtUsuario);
		layout.putConstraint(SpringLayout.EAST , txtIp,  -5, SpringLayout.EAST , pnlLogin);
		layout.putConstraint(SpringLayout.WEST , txtIp, 140, SpringLayout.WEST , pnlLogin);
		
		//Botão Entrar
		layout.putConstraint(SpringLayout.NORTH, btnOk,  5, SpringLayout.SOUTH, txtIp);
		layout.putConstraint(SpringLayout.EAST , btnOk, -5, SpringLayout.EAST , pnlLogin);
		
		pnlLogin.setLayout(layout);

		pnlLogin.add(lblUsuario);
		pnlLogin.add(txtUsuario);
		pnlLogin.add(lblIp);
		pnlLogin.add(txtIp);
		pnlLogin.add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				entraChat();
			}
		});
	}
	
	private void entraChat(){
		this.dispose();
		new Chat().initialize();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login().initialize();
	}

}
