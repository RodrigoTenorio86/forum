package br.com.alura.forum.controller.dto;

public class ErroDeFormularioDto {
	private String mensagem;
	private String campo;

	public ErroDeFormularioDto(String mensagem, String campo) {
		this.mensagem = mensagem;
		this.campo = campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getCampo() {
		return campo;
	}

}
