package br.com.buzzi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzzi.domain.Categoria;
import br.com.buzzi.repositories.CategoriaRepository;
import br.com.buzzi.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		if (!obj.isPresent()) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
		return obj.orElse(null);
	}

	public Categoria insert(Categoria obj) {
		return repo.save(obj);
	}

}
