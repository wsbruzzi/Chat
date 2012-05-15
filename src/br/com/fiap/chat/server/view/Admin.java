/*package br.com.fiap.chat.server.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

public class Admin extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JPanel container;
	private JTextPane quadroDeMensagem;
	private JTextPane quadroDeCliente;
	
	public void initialize(){
		
		initContainer();
		
		this.setTitle("Admin Chat Fiap");
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(container);
	}
	
	private void initContainer(){
		GridLayout layout = new GridLayout(1, 2);
		
		container = new JPanel();
		container.setBackground(Color.BLACK);
		container.setSize(780, 600);
		
		quadroDeMensagem = new JTextPane();
		quadroDeCliente  = new JTextPane();
		
		Border txpBorder = BorderFactory.createLineBorder(Color.black);
		quadroDeMensagem.setBorder(txpBorder);
		quadroDeMensagem.setSize(20, 20);
		quadroDeCliente.setBorder(BorderFactory.createLineBorder(Color.red));
		//quadroDeCliente.setSize(200, 600);
		
		//histórico
		layout.putConstraint(SpringLayout.NORTH, quadroDeMensagem,   5, SpringLayout.NORTH, container);
		layout.putConstraint(SpringLayout.SOUTH, quadroDeMensagem, -35, SpringLayout.SOUTH, container);
		layout.putConstraint(SpringLayout.EAST , quadroDeMensagem,  -5, SpringLayout.EAST , container);
		layout.putConstraint(SpringLayout.WEST , quadroDeMensagem,   5, SpringLayout.WEST , container);
		
		//Field Mensagem
//		layout.putConstraint(SpringLayout.NORTH, quadroDeCliente,   5, SpringLayout.NORTH, quadroDeMensagem);
//		layout.putConstraint(SpringLayout.SOUTH, quadroDeCliente, -35, SpringLayout.SOUTH, quadroDeMensagem);
//		layout.putConstraint(SpringLayout.EAST , quadroDeCliente,  -5, SpringLayout.EAST , quadroDeMensagem);
//		layout.putConstraint(SpringLayout.WEST , quadroDeCliente,   5, SpringLayout.WEST , quadroDeMensagem);
			

		container.setLayout(layout);
		
		quadroDeMensagem.setText("mensagens");
		quadroDeCliente.setText("clientes");
		
		container.add(quadroDeMensagem);
		//container.add(quadroDeCliente);
	}
	
	public static void main(String[] args) {
		new Admin().initialize();
	}
}
*/