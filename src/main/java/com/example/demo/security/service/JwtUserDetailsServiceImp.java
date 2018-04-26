package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pessoa;
import com.example.demo.security.jwt.JwtUserFactory;
import com.example.demo.service.PessoaService;

@Service
public class JwtUserDetailsServiceImp implements UserDetailsService{

	@Autowired
	private PessoaService pService;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Pessoa pessoa = pService.buscarUsuario(login);
		if(pessoa == null) {
			throw new UsernameNotFoundException("Usuario nao encontrado com o email: ");
		}else {
			return JwtUserFactory.create(pessoa);
		}
	}

}
