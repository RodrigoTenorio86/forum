package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.forum.modelo.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
	public List<Topico> findByCursoNome(String nomeCurso);
	public List<Topico> findByTitulo(String titulo);
}
