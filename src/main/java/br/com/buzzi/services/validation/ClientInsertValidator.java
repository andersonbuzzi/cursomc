package br.com.buzzi.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.buzzi.domain.Cliente;
import br.com.buzzi.domain.enums.TipoCliente;
import br.com.buzzi.dto.ClienteNewDTO;
import br.com.buzzi.repositories.ClienteRepository;
import br.com.buzzi.resources.exception.FieldMessage;
import br.com.buzzi.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())) {
			if (!BR.isValidCpf(objDto.getCpfOuCnpj())) {
				list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
			}
		} else if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())) {
			if (!BR.isValidCnpj(objDto.getCpfOuCnpj())) {
				list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
			}
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente."));			
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}