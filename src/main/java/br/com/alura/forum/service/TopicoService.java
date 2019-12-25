package br.com.alura.forum.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.dto.TopicoForm;
import br.com.alura.forum.controller.form.UpadateTopico;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@Service
public class TopicoService {

	@Autowired
	private TopicoRepository topicoRepository;
	
	
	@Autowired
	private CursoRepository cursoRepository;

	public Page<TopicoDTO> allList(int page, int size, String sort) {
		Pageable paginacao = PageRequest.of(page, size, Direction.DESC, sort);
		Page<Topico> listTopicos= topicoRepository.findAll(paginacao);
		return TopicoDTO.converter(listTopicos);

	}

	public List<TopicoDTO> findByNameCourse(String nome) {
		List<Topico> listTopico = topicoRepository.findByCursoNome(nome);
        return 	TopicoDTO.converter(listTopico);	
	}

	public DetalhesDoTopicoDto getById(Long id) {
		Topico topico = topicoRepository.getOne(id);
		return new DetalhesDoTopicoDto(topico);

	}

	public void delete(Long id) {
		topicoRepository.deleteById(id);
	}
	
	
	
	public Topico register(TopicoForm form) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		return topico;		
	}	
	
	public Topico  update(Long id,UpadateTopico updatetopico){
		Topico topico=topicoRepository.getOne(id);
		topico.setMensagem(updatetopico.getMensagem());
		topico.setTitulo(updatetopico.getTitulo());
		return topico;
		 
	}

	public Optional<Topico> findByIdTopico(Long id) {
	     Optional<Topico>topico=	topicoRepository.findById(id);
		return topico;
	}
	
	

}
