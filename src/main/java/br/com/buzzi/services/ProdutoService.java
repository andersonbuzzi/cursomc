package br.com.buzzi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.buzzi.domain.Categoria;
import br.com.buzzi.domain.Produto;
import br.com.buzzi.repositories.CategoriaRepository;
import br.com.buzzi.repositories.ProdutoRepository;
import br.com.buzzi.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		if (!obj.isPresent()) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado. Id: " + id + ", Tipo: " + Produto.class.getName());
		}
		return obj.orElse(null);
	}

	@Transactional(readOnly=true)
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}

}
