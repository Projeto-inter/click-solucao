package com.generation.clicksolucao.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.clicksolucao.model.Servico;
import com.generation.clicksolucao.repository.ServicoRepository;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/servico")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServicoController {

		@Autowired
		private ServicoRepository servicoRepository;

		@GetMapping
		public ResponseEntity<List<Servico>> getAll() {
			return ResponseEntity.ok(servicoRepository.findAll());
		}

		@GetMapping("/{id}")
		public ResponseEntity<Servico> getById(@PathVariable Long id) {
			return servicoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}

		@GetMapping("/nome/{nome}")
		public ResponseEntity<List<Servico>> getByTitulo(@PathVariable String nome) {
			return ResponseEntity.ok(servicoRepository.findAllByNomeContainingIgnoreCase(nome));
		}

		@PostMapping
		public ResponseEntity<Servico> post(@Valid @RequestBody Servico servico) {
			return ResponseEntity.status(HttpStatus.CREATED).body(servicoRepository.save(servico));
		}

		@PutMapping
		public ResponseEntity<Servico>  put(@Valid @RequestBody Servico servico) {
			return servicoRepository.findById(servico.getId())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(servicoRepository.save(servico)))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}

		@ResponseStatus(HttpStatus.NO_CONTENT)
		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			Optional<Servico> servico = servicoRepository.findById(id);

			if (servico.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);

			servicoRepository.deleteById(id);
		}
	}
