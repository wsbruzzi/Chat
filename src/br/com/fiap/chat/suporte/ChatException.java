package br.com.fiap.chat.suporte;

public class ChatException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ChatException() {
		super();
	}
	
	public ChatException(String mensagem){
		super(mensagem);
	}
	
	public ChatException(Exception exception){
		super(exception);
	}
}
