package com.example.demo.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.model.Perfil;
import com.example.demo.model.Pessoa;

public class JwtUserFactory {
	private JwtUserFactory() {

	}
	//Caso haja um relacao que tenha permissoes podemos usar um laco de repeticao para listar as regras
	
	public static JWTUser create(Pessoa pessoa) {
		System.out.println("deveria ser: " + pessoa.getUsuario());
			return  new JWTUser(pessoa.getId().toString(), pessoa.getUsuario(),
					pessoa.getSenha(),
					mapToGrantedAuthorities(pessoa.getRole()));
	
	}
	private static List<GrantedAuthority> mapToGrantedAuthorities(Perfil perfil) {
		System.out.println(perfil.toString());
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfil.toString()));
		return authorities;

	}
}
