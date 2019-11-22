package br.com.alura.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.forum.repository.TopicoRepository;

@Service
public class TopicoService {
	
	@Autowired
	private TopicoRepository topicoRepository;

}
