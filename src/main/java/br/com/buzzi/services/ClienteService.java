package br.com.buzzi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzzi.domain.Cliente;
import br.com.buzzi.repositories.ClienteRepository;
import br.com.buzzi.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		if (!obj.isPresent()) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj.orElse(null);
	}

}
