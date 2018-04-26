package com.example.demo.conttroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.service.PessoaService;

@RestController
public class PessoaConttroller {
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping("/pessoas")
	public Pessoa salvare(@RequestBody Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
}
