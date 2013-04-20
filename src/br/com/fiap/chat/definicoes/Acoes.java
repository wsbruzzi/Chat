package br.com.fiap.chat.definicoes;
/**
 * Enum que se usa para saber que tipo de comando es el que se envia 
 * o se recibe desde el servidor
 * 
 *
 */
public enum Acoes {
	
//	REGISTRA_USUARIO(0, "registraUsuario:"), ENVIA_MENSAGEM(1, "enviaMensagem:"), DESCONECTA(2, "desconectar:"), LISTA_USUARIO(3, "listaUsuarios:");
	REGISTRA_USUARIO, ENVIA_MENSAGEM, DESCONECTA, LISTA_USUARIO,LLAVE_PUBLICA;
	
	public String toString() {
		return this.name() + ":";
	}
	
	public String getAcao() {
		return this.name() + ":";
	}
}
