package com.awmoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.awmoney.api.model.Pessoa;
import com.awmoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	public Pessoa update(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = findById(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repository.save(pessoaSalva);
	}

	
	public void updateStatus(Long codigo, Boolean status) {
		Pessoa pessoaSalva = findById(codigo);
		pessoaSalva.setAtivo(status);
		repository.save(pessoaSalva);
	}
	
	private Pessoa findById(Long codigo) {
		Pessoa pessoaSalva = repository.findOne(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
}
