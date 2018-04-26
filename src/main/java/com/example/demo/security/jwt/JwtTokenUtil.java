package com.example.demo.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	static final String KEY_USUARIO = "sub";
	static final String KEY_CRIADA = "created";
	static final String KEY_EXPIRADA = "exp";
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String buscarUsuarioNoToken(String token) {
		String pessoa;
		try {
			final Claims claims = getClaimsFromToken(token);
			pessoa = claims.getSubject();
		} catch (Exception e) {
			pessoa = null;
		}
		return pessoa;
	}
	
	public Date pegaExperiracaoDoToken(String token) {
		Date expiracao;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiracao = claims.getExpiration();		
		} catch (Exception e) {
			expiracao = null;
		}
		return expiracao;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	private Boolean tokenExpirado(String token) {
		final Date experiracao = pegaExperiracaoDoToken(token);
		return experiracao.before(new Date());
	}
	
	public String gerarToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put(KEY_USUARIO, userDetails.getUsername());
		final Date createDate = new Date();
		claims.put(KEY_CRIADA, createDate);
		
		return doGerarToken(claims);
	}
	
	private String doGerarToken(Map<String, Object> claims) {
		final Date dataCriacao = (Date) claims.get(KEY_CRIADA);
		final Date dataExpiracao = new Date(dataCriacao.getTime() + expiration * 1000);
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public Boolean podeAtulizar(String token) {
		return (!tokenExpirado(token));
	}
	
	public String atualizaToken(String token) {
		String atualizaToken;
		
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(KEY_CRIADA, new Date());
			atualizaToken = doGerarToken(claims);
		} catch (Exception e) {
			atualizaToken = null;
		}
		return atualizaToken;
	}
	
	public Boolean tokenValido(String token, UserDetails detalhes) {
		JWTUser pessoa = (JWTUser) detalhes;
		final String user = buscarUsuarioNoToken(token);
		return (
				user.equals(pessoa.getUsername())
					&& !tokenExpirado(token));
	}
	

}
