package br.com.fiap.chat.definicoes;

public enum Acoes {
	
//	REGISTRA_USUARIO(0, "registraUsuario:"), ENVIA_MENSAGEM(1, "enviaMensagem:"), DESCONECTA(2, "desconectar:"), LISTA_USUARIO(3, "listaUsuarios:");
	REGISTRA_USUARIO, ENVIA_MENSAGEM, DESCONECTA, LISTA_USUARIO;
	
	public String toString() {
		return this.name() + ":";
	}
	
	public String getAcao() {
		return this.name() + ":";
	}
}
