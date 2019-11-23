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
import org.springframework.web.bind.annotation.PutMapping;
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
import br.com.alura.forum.service.TopicoService;
import br.com.alura.forum.error.ResourceNotFoundException;

@RestController
@RequestMapping(path= "/v1/topicos", produces=  { "application/json" }, consumes = { "application/json" })
public class TopicoController {

	@Autowired
	private TopicoService topicoService;
	
	@Autowired
	private CursoRepository cursoRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<TopicoDTO> allList() {
		return topicoService.allList();
	}

	@RequestMapping(value = "/burcarPorNomeCurso/{nome}", method = RequestMethod.GET)
	public ResponseEntity<?> findByNameCourse( String nome) {
		return topicoService.findByNameCourse(nome);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		check(id);
	    return topicoService.getById(id);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> register(@RequestBody @Valid TopicoForm form,UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri= uriBuilder.path("/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@PutMapping
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> update(@RequestBody @Valid Topico topico){
		   check(topico.getId());
		   topicoRepository.save(topico);
		   return ResponseEntity.ok(new TopicoDTO(topico) );
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id){
		check(id);
		return topicoService.delete(id);
	}
	
	private void check(Long id)  {
	 Optional<Topico> topico=	topicoRepository.findById(id);
	 if(! topico.isPresent()) 
		 throw new ResourceNotFoundException("Topico Not Found for id " + id);
	 
	}

}
