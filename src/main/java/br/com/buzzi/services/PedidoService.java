package br.com.buzzi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzzi.domain.Pedido;
import br.com.buzzi.repositories.PedidoRepository;
import br.com.buzzi.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		if (!obj.isPresent()) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return obj.orElse(null);
	}

}
