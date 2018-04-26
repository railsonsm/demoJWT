package com.example.demo.security.conttroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pessoa;
import com.example.demo.security.jwt.JwtAuthenticationRequest;
import com.example.demo.security.jwt.JwtTokenUtil;
import com.example.demo.security.model.CurrentUser;
import com.example.demo.service.PessoaService;

@RestController
@CrossOrigin("${origens-permitidas}")
public class AuthenticationRestConttroller {
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService detailsService;
	
	@Autowired
	private PessoaService pService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	
	@PostMapping("/logar")
	public ResponseEntity<?> criarAutenticacaoToken(@RequestBody JwtAuthenticationRequest authenticationRequest){
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsuario(), 
						authenticationRequest.getSenha()
						)
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = detailsService.loadUserByUsername(authenticationRequest.getUsuario());
		final String token = jwtTokenUtil.gerarToken(userDetails);
		final Pessoa pessoa = pService.buscarUsuario(authenticationRequest.getUsuario());
		//usuarioGrupo..setSenha(null);
		return ResponseEntity.ok(new CurrentUser(token, pessoa));
	}
	
	@PostMapping("refresh")
	public ResponseEntity<?> atualizaEPegaAutenticacaoToken(HttpServletRequest request){
		String token = request.getHeader("Authorization");
		String username = jwtTokenUtil.buscarUsuarioNoToken(token);
		final Pessoa pessoa = pService.buscarUsuario(username);
		
		if(jwtTokenUtil.podeAtulizar(token)) {
			String refreshedToken = jwtTokenUtil.atualizaToken(token);
			return ResponseEntity.ok(new CurrentUser(refreshedToken, pessoa));
		}else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
