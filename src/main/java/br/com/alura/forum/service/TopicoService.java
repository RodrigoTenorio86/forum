package br.com.alura.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@Service
public class TopicoService {

	@Autowired
	private TopicoRepository topicoRepository;

	public List<TopicoDTO> allList() {
		List<Topico> listTopicos = topicoRepository.findAll();
		return TopicoDTO.coverter(listTopicos);

	}

	public ResponseEntity<?> findByNameCourse(String nome) {
		List<Topico> listTopico = topicoRepository.findByCursoNome(nome);
		return ResponseEntity.ok(TopicoDTO.coverter(listTopico));
	}

	public ResponseEntity<?> getById(Long id) {
		Topico topico = topicoRepository.getOne(id);
		return ResponseEntity.ok(new TopicoDTO(topico));

	}

	public ResponseEntity<?> delete(Long id) {
		topicoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
