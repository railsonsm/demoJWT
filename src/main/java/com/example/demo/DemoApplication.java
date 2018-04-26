package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.Perfil;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	@Autowired
	private PessoaRepository pr;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setUsuario("railson");
		pessoa.setSenha(encoder.encode("123456"));
		pessoa.setRole(Perfil.ROLE_ADMIN);
		pr.save(pessoa);
		
	}
}
