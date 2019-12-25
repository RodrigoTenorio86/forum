package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;

public class DetalhesDoTopicoDto {
	private long id;
	private String tutilo;
	private String mensagem;
	private LocalDateTime dataCricao;
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDto> respostas;

	public DetalhesDoTopicoDto(Topico topico) {
		this.id = topico.getId();
		this.tutilo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCricao = topico.getDataCriacao();
		this.status = topico.getStatus();
		this.respostas = new ArrayList<RespostaDto>();
		this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
	}

	public long getId() {
		return id;
	}

	public String getTutilo() {
		return tutilo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCricao() {
		return dataCricao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}

}
