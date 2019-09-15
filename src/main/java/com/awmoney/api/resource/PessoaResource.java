package com.awmoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.awmoney.api.event.RecursoCriadoEvent;
import com.awmoney.api.model.Pessoa;
import com.awmoney.api.repository.PessoaRepository;
import com.awmoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private PessoaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> findAll() {
		return repository.findAll();
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> findOne(@PathVariable Long codigo) {
		Pessoa pessoa = repository.findOne(codigo);
		return (pessoa != null) ? ResponseEntity.ok().body(pessoa) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PostMapping
	public ResponseEntity<Pessoa> insert(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = repository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		repository.delete(codigo);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> update(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = service.update(codigo, pessoa);
		return ResponseEntity.ok().body(pessoaSalva);
	}
	
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PutMapping("/{codigo}/status")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Long codigo, @RequestBody Boolean status){
		service.updateStatus(codigo, status);
	}
}
