package com.example.demo.security.model;

import com.example.demo.model.Pessoa;

public class CurrentUser {
	private String token;
	private Pessoa pessoa;
	
	
	
	public CurrentUser(String token, Pessoa pessoa) {
		super();
		this.token = token;
		this.pessoa = pessoa;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	
	
	
}
