package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.dto.TopicoForm;
import br.com.alura.forum.error.ResourceNotFoundException;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.service.TopicoService;

@RestController
@RequestMapping(path = "/v1/topicos")
public class TopicoController {

	@Autowired
	private TopicoService topicoService;

	@RequestMapping(method = RequestMethod.GET)
	@Cacheable(value = "allList")
	public ResponseEntity<?> allList(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
		Page<TopicoDTO> list = topicoService.allList(page, size, sort);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/burcarPorNomeCurso/{nome}", method = RequestMethod.GET)
	@Cacheable(value="findByNameCourse")
	public ResponseEntity<?> findByNameCourse(@PathVariable String nome) {

		List<TopicoDTO> topicoDTOs = topicoService.findByNameCourse(nome);
		return new ResponseEntity<>(topicoDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		check(id);
		TopicoDTO topicoDTO = topicoService.getById(id);
		return new ResponseEntity<>(topicoDTO, HttpStatus.OK);
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "findByNameCourse", allEntries = true)
	public ResponseEntity<TopicoDTO> register(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoService.register(form);

		URI uri = uriBuilder.path("/{id}").buildAndExpand(topico.getId()).toUri();
//	    ResponseEntity<?> outraFormaDeRespond=	new ResponseEntity<>(uri,HttpStatus.CREATED);
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}

	@PutMapping
	@Transactional(rollbackOn = Exception.class)
	@CacheEvict(value = "findByNameCourse", allEntries = true)
	public ResponseEntity<?> update(@RequestBody @Valid Topico topico) {
		check(topico.getId());
		topicoService.update(topico);
		return ResponseEntity.ok(new TopicoDTO(topico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "findByNameCourse", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		check(id);
		topicoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private void check(Long id) {
		Optional<Topico> topico = topicoService.findByIdTopico(id);
		if (!topico.isPresent())
			throw new ResourceNotFoundException("Topico Not Found for id " + id);

	}

}
