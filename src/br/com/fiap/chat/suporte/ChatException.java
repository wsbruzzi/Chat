package br.com.fiap.chat.suporte;

import br.com.fiap.chat.definicoes.TipoLog;

public class ChatException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ChatException() {
		super();
	}
	
	public ChatException(String mensagem){
		super(mensagem);
		Logger.write(TipoLog.SERVER, mensagem);
	}
	
	public ChatException(Exception exception){
		super(exception);
		Logger.write(TipoLog.SERVER, exception.getMessage());
	}
}
