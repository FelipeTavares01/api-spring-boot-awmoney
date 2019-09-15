package com.awmoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awmoney.api.event.RecursoCriadoEvent;
import com.awmoney.api.model.Categoria;
import com.awmoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	//Só é possivel utilizar a anotação @CrossOrigin se não tiver usando oauth2
	@CrossOrigin(origins = { "http://localhost:8000" })
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Categoria>> findAll() {
		return ResponseEntity.ok().body(repository.findAll());
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> findOne(@PathVariable Long codigo) {
		Categoria categoria = repository.findOne(codigo);

		return (categoria != null) ? ResponseEntity.ok().body(categoria) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	@PostMapping
	// @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> insert(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = repository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);

	}

}
