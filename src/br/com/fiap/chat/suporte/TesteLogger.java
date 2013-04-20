package br.com.fiap.chat.suporte;

import br.com.fiap.chat.definicoes.TipoLog;

/*
 * simplemente prueba el debug del servidor
 */
public class TesteLogger {

	public static void main(String[] args) {
		
		Logger.write(TipoLog.CLIENT, "prueba del log del cliente...");
		Logger.write(TipoLog.SERVER, "prueba del log del servidor...");
	}
}
