package br.com.fiap.chat.suporte;

import br.com.fiap.chat.definicoes.TipoLog;

// apagar classe
public class TesteLogger {

	public static void main(String[] args) {
		
		Logger.write(TipoLog.CLIENT, "teste log client...");
		Logger.write(TipoLog.SERVER, "teste log server...");
	}
}
