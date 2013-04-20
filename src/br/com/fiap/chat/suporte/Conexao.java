package br.com.fiap.chat.suporte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import com.ajt.rsa.RSA;

import br.com.fiap.chat.definicoes.Acoes;

/**
 * Esta es la clase conexao es la clase principal para el chat y el cifrado
 * maneja el envio de mensaje, de comandos, la supscripcion de un cliente con el servidor.
 * agrega encriptacion a los mensajes enviados.
 */
public class Conexao {
	private String ipServidor;
	public String apelido;
	private final int porta;
	private Socket conexao;
	private PrintWriter writer;
	private BufferedReader reader;
	public RSA rsa = new RSA(1024);
	private Map<String, RSA> llavesMap = new TreeMap<String, RSA>();
	

	public Conexao(String ipServidor, String apelido) {
		this.ipServidor = ipServidor;
		this.apelido = apelido;
		this.porta = 4447;
	}

	public void open() throws ChatException {
		if (this.conexao == null) {
			try {
				this.conexao = new Socket(this.ipServidor, this.porta);
				this.writer = new PrintWriter(this.conexao.getOutputStream(),
						true);
				this.reader = new BufferedReader(new InputStreamReader(
						this.conexao.getInputStream()));
			} catch (Exception e) {
				throw new ChatException("Error al abrir la conexion: "
						+ e.getMessage());
			}

			this.inscribeUser();
		}
	}

	public void open(String ipServidor, String apelido) throws ChatException {
		this.ipServidor = ipServidor;
		this.apelido = apelido;

		this.open();
	}

	public void close() {
		try {
			this.sendCommand(Acoes.DESCONECTA, "");
			this.conexao.close();
		} catch (IOException e) {
			throw new RuntimeException("Error al cerrar la conexion: "
					+ e.getMessage());
		}
	}

	public boolean isRegistered() {
		return !this.conexao.isClosed();
	}

	public String receive() {
		try {
			String mensajeRecivido = this.reader.readLine();
			procesarLLaves(mensajeRecivido);
			return mensajeRecivido;
		} catch (IOException e) {
			throw new RuntimeException("Error al recibir mensaje: "
					+ e.getMessage());
		}
	}
	
	public void procesarLLaves(String mensagemRecebida){
		if (mensagemRecebida==null)
			return;
		String[] partesDaMensagem = mensagemRecebida.split(":", 2);
		
		switch (Acoes.valueOf(partesDaMensagem[0])) {
			case LLAVE_PUBLICA:
				
				String[] parts = partesDaMensagem[1].split(";");
				System.out.print(partesDaMensagem[1]);
				RSA nuevaRSA = new RSA(new BigInteger( parts[1]),new BigInteger( parts[2]) );
				if(!llavesMap.containsKey(parts[0]))
					llavesMap.put(parts[0], nuevaRSA);
			break;
			default:
				
			break;
		}
	}
	public void sendMessage(String mensagem) {
		this.sendCommand(Acoes.LLAVE_PUBLICA, this.apelido+";"+rsa.getN().toString()+";"+rsa.getE().toString());
		if (!"".equals(mensagem.trim())) {
			String msg = new String(this.apelido);
			//String cifrado="";
			msg+=" dice: ";
			msg+=mensagem;
//			msg.append("-mmg-");
//			for(int i=0; i<llavesMap.size();i++){
//				BigInteger plaintext = new BigInteger(mensagem.getBytes());
//				BigInteger ciphertext = llavesMap.keySet()..encrypt(plaintext);
//				msg.append("cv" + llavesMap.get(this.apelido).getN()+" dif "+llavesMap.get(this.apelido).getE());
//				this.sendCommand(Acoes.ENVIA_MENSAGEM, ciphertext.toString());
//			}
			
			for(Map.Entry<String,RSA> entry : llavesMap.entrySet()) {
				  String key = entry.getKey();
				  RSA value = entry.getValue();
				  
				  BigInteger plaintext = new BigInteger(msg.getBytes());
				  BigInteger ciphertext = value.encrypt(plaintext);
				  //cifrado = key+";"+ciphertext;
				  //System.out.println();
				  //System.out.println(this.apelido+"llave que llego: ;"+value.getN().toString()+"  ;  "+value.getE().toString());
				  //System.out.println( "al enviar mensaje: "+key+"-"+ciphertext.toString());
				  this.sendCommand(Acoes.ENVIA_MENSAGEM, "  "+"-"+ciphertext.toString());
			}
		}
	}

	public void getRegisteredUsers() {
		this.sendCommand(Acoes.LISTA_USUARIO, "");
	}

	private void inscribeUser() throws ChatException {
		this.sendCommand(Acoes.REGISTRA_USUARIO, this.apelido);

		String[] partesRetorno = this.receive().split(":", 2);
		if (Acoes.valueOf(partesRetorno[0]) != Acoes.REGISTRA_USUARIO|| (!"true".equalsIgnoreCase(partesRetorno[1]))) {
			this.close();
			throw new ChatException("Error al registrar al usuario");
		}else{
			System.out.println(this.apelido+"antes;"+rsa.getN().toString()+";"+rsa.getE().toString());
			this.sendCommand(Acoes.LLAVE_PUBLICA, this.apelido+";"+rsa.getN().toString()+";"+rsa.getE().toString());
		}
	}

	private void sendCommand(Acoes acao, String msg) {
		StringBuilder mensagem = new StringBuilder(acao.toString());
		mensagem.append(msg);
		mensagem.append("\n");
		System.out.println("al enviar comando: "+mensagem.toString());
		this.writer.write(mensagem.toString());
		this.writer.flush();
	}
}
