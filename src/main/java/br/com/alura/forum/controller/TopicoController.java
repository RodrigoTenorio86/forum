package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.dto.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.error.ResourceNotFoundException;

@RestController
@RequestMapping("/v1/topicos")
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<TopicoDTO> allList() {
		List<Topico> listTopicos = topicoRepository.findAll();
		return TopicoDTO.coverter(listTopicos);
	}

	@RequestMapping(value = "/burcarPorNomeCurso", method = RequestMethod.GET)
	public List<TopicoDTO> findByNameCourse(String nome) {
		List<Topico> listTopico = topicoRepository.findByCursoNome(nome);
		return TopicoDTO.coverter(listTopico);
	}
	
	@GetMapping(value="/{id}")
	public TopicoDTO getById(@PathVariable Long id) {
		Topico topico = topicoRepository.getOne(id);
		return new TopicoDTO(topico);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> register(@RequestBody @Valid TopicoForm form,UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri= uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id){
		check(id);
		topicoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	private void check(Long id)  {
	 Optional<Topico> topico=	topicoRepository.findById(id);
	 if(! topico.isPresent()) 
		 throw new ResourceNotFoundException("Topico Not Found for id " + id);
	 
	}

}
