package br.com.fiap.chat.definicoes;

public enum Acoes {
	
	REGISTRA_USUARIO(0, "registraUsuario:"), ENVIA_MENSAGEM(1, "enviaMensagem:"), DESCONECTA(2, "desconectar:"), LISTA_USUARIO(3, "listaUsuarios:");
	
	private Integer idAcao;
	private String nomeAcao;
	
	Acoes(Integer idAcao, String nomeAcao) {
		this.idAcao = idAcao;
		this.nomeAcao = nomeAcao;
	}
	
	public String getAcao() {
		return this.nomeAcao;
	}
	
	public Integer getId() {
		return this.idAcao;
	}
}
