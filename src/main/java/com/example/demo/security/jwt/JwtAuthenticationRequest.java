package com.example.demo.security.jwt;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String senha;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public JwtAuthenticationRequest(String usuario, String senha) {
		super();
		this.usuario = usuario;
		this.senha = senha;
	}
	public JwtAuthenticationRequest() {
	
	}
	
	
	
	
	
}
