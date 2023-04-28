package com.generation.clicksolucao.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.clicksolucao.model.Usuario;
import com.generation.clicksolucao.model.UsuarioLogin;
import com.generation.clicksolucao.repository.UsuarioRepository;
import com.generation.clicksolucao.security.JwtService;

// regras de negocios especificas da aplicação
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository; // chama usuário repository

    @Autowired
    private JwtService jwtService; // toda a criação de token e criptografia de senha passamos para ele gerar TOKEN

    @Autowired
    private AuthenticationManager authenticationManager; // toda a criação de token e criptografia de senha passamos para ele gerar USUÁRIO E SENHA

   // cadastro de usuário, ou cadastra ou não cadastra.
    public Optional<Usuario> cadastrarUsuario(Usuario usuario) { // optional é para quando a requisição tem duas opções de resposta

        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
            return Optional.empty(); // esse e-mail já é utilizado por outro usuário

        usuario.setSenha(criptografarSenha(usuario.getSenha())); // após a criação do e-mail a senha é criptografada e enviada para o banco de dados

        return Optional.of(usuarioRepository.save(usuario));// pq não cadastrou? o usuáio já existe? e etc...

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }
    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		// Gera o Objeto de autenticação
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
				usuarioLogin.get().getSenha());

		// Autentica o Usuario
		Authentication authentication = authenticationManager.authenticate(credenciais);

		// Se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

			// Busca os dados do usuário
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

			// Se o usuário foi encontrado
			if (usuario.isPresent()) {

				// Preenche o Objeto usuarioLogin com os dados encontrados
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
				usuarioLogin.get().setSenha("");

				// Retorna o Objeto preenchido
				return usuarioLogin;

			}

		}

		return Optional.empty();

	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}
}
    
