package br.com.fiap.chat.client.view;

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
import javax.swing.SpringLayout;

import br.com.fiap.chat.suporte.ChatException;
import br.com.fiap.chat.suporte.Conexao;

public class Login extends JFrame {
	
	/**
	 * Esta clase se encarga de mostrar el JFrame para el login de un nuevo cliente
	 * Puede usar la red 127.0.0.1 si corre el servidor localmente. 
	 * recibe nombre de usuario e ip del servidor
	 * si el nombre de usuario se repite no podra ingresar al chat room
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel      pnlLogin;
	private JButton     btnOk;
	private JTextField  txtUsuario, txtIp;
	private JLabel      lblUsuario, lblIp, lblMensagem;
	private KeyListener kevBindEnter;
	
	
	/**
	 * Seta par�metros para abertura do formul�rio
	 */
	public void initialize(){
		initPnlLogin();

		this.setTitle("Login Chat");
		this.setSize(400,120);
		this.add(pnlLogin);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Adiciona itens ao Painel
	 */
	private void initPnlLogin(){
		SpringLayout layout = new SpringLayout();
		
		pnlLogin = new JPanel();
		pnlLogin.setBackground(Color.WHITE);

		lblUsuario  = new JLabel("Usuario");
		lblIp       = new JLabel("IP del servidor"); 
		lblMensagem = new JLabel();
		txtUsuario  = new JTextField(15);
		txtIp       = new JTextField(15);
		btnOk       = new JButton("Entrar");
		
		lblMensagem.setForeground(Color.RED);

		//Label Usu�rio
		layout.putConstraint(SpringLayout.NORTH, lblUsuario, 5, SpringLayout.NORTH, pnlLogin);
		layout.putConstraint(SpringLayout.WEST , lblUsuario, 5, SpringLayout.WEST , pnlLogin);
		
		//Field Usu�rio 
		layout.putConstraint(SpringLayout.NORTH, txtUsuario,   5, SpringLayout.NORTH, pnlLogin);
		layout.putConstraint(SpringLayout.EAST , txtUsuario,  -5, SpringLayout.EAST , pnlLogin);
		layout.putConstraint(SpringLayout.WEST , txtUsuario, 140, SpringLayout.WEST , pnlLogin);
		
		//Label Ip
		layout.putConstraint(SpringLayout.NORTH, lblIp, 5, SpringLayout.SOUTH, lblUsuario);
		layout.putConstraint(SpringLayout.WEST , lblIp, 5, SpringLayout.WEST , pnlLogin);
		
		
		// label mensagem
		layout.putConstraint(SpringLayout.NORTH, lblMensagem,  5, SpringLayout.SOUTH , txtIp);
		layout.putConstraint(SpringLayout.EAST,  lblMensagem, -5, SpringLayout.WEST  , btnOk);
		layout.putConstraint(SpringLayout.WEST,  lblMensagem,  5, SpringLayout.WEST  , pnlLogin);
		layout.putConstraint(SpringLayout.SOUTH, lblMensagem, -5, SpringLayout.SOUTH , pnlLogin);
		
		//Field Ip
		layout.putConstraint(SpringLayout.NORTH, txtIp,   5, SpringLayout.SOUTH, txtUsuario);
		layout.putConstraint(SpringLayout.EAST , txtIp,  -5, SpringLayout.EAST , pnlLogin);
		layout.putConstraint(SpringLayout.WEST , txtIp, 140, SpringLayout.WEST , pnlLogin);
		
		//Bot�o Entrar
		layout.putConstraint(SpringLayout.NORTH, btnOk,  5, SpringLayout.SOUTH, txtIp);
		layout.putConstraint(SpringLayout.EAST , btnOk, -5, SpringLayout.EAST , pnlLogin);
		
		pnlLogin.setLayout(layout);

		pnlLogin.add(lblUsuario);
		pnlLogin.add(lblMensagem);
		pnlLogin.add(txtUsuario);
		pnlLogin.add(lblIp);
		pnlLogin.add(txtIp);
		pnlLogin.add(btnOk);
		
		adicionaListeners();
	}
	
	/**
	 * Adiciona Listeners aos campos e botões
	 */
	private void adicionaListeners(){
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				entraChat();
			}
		});
		
		
		kevBindEnter = new KeyListener() {	
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10)
					entraChat();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		};
		txtIp.addKeyListener(kevBindEnter);
		txtUsuario.addKeyListener(kevBindEnter);
		btnOk.addKeyListener(kevBindEnter);
	}
	
	/**
	 * Fecha o formul�rio de login e abre a tela de chat
	 */
	private void entraChat(){
		String ip   = txtIp.getText(); 
		String nome = txtUsuario.getText();
		Conexao conexao = new Conexao(ip, nome);
		
		try {
			conexao.open();
		} catch (ChatException e) {
			lblMensagem.setText("No se pudo conectar");
		}
		
		if (conexao.isRegistered()) {
			this.dispose();
			new Chat().initialize(conexao);
		}
	}

	/**
	 * Inicia o formul�rio de login
	 * @param args
	 */
	public static void main(String[] args) {
		new Login().initialize();
	}

}
