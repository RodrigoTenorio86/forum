package br.com.alura.forum.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.dto.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@Service
public class TopicoService {

	@Autowired
	private TopicoRepository topicoRepository;
	
	
	@Autowired
	private CursoRepository cursoRepository;

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
	
	
	
	public Topico register(TopicoForm form) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		return topico;		
	}	
	
	public void update(Topico topico){
		 topicoRepository.saveAndFlush(topico);
	}

	public Optional<Topico> findByIdTopico(Long id) {
	     Optional<Topico>topico=	topicoRepository.findById(id);
		return topico;
	}
	
	

}
