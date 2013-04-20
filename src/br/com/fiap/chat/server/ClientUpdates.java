package br.com.fiap.chat.server;
/*
 * Instancia de un hilo para poder manejar los updates 
 * dinamicamente
 */
public class ClientUpdates implements Runnable {

	@Override
	public void run() {
		
		chatUpdate();
	}

	private void chatUpdate() {
		ClientesConectados cc;
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
