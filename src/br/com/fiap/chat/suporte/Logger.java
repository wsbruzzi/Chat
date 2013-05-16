package br.com.fiap.chat.suporte;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import br.com.fiap.chat.definicoes.TipoLog;


public class Logger {

	/**
	 * TipoLog -> servidor o cliente
	 * Mensagem -> string sera gravado en mis datos ahora
	 * 
	 * @param destino
	 * @param mensagem
	 */
	public static void write(TipoLog tipoLog, String mensagem) {

		try {
			FileWriter writer = new FileWriter(tipoLog.toString().toLowerCase() + ".log", true);			
			Date data = new Date();
			writer.write(data + ": " + mensagem + "\r\n");
			writer.close();			
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
