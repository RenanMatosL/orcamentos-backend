package br.com.renanmatos.orcamentos.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanmatos.orcamentos.dao.UsuarioDao;
import br.com.renanmatos.orcamentos.dto.UsuarioLoginDto;
import br.com.renanmatos.orcamentos.enuns.StatusUsuarioAtivo;
import br.com.renanmatos.orcamentos.model.Usuario;
import jakarta.validation.Valid;

//@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao; // Sua DAO customizada

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioLoginDto usuarioLoginDto) {
        // 1. Busca o usuário pelo nome
        Usuario usuario = usuarioDao.buscarPorNomeUsuario(usuarioLoginDto.getNomeUsuario());

        // 2. Verifica se o usuário existe e a senha está correta (texto puro)
        if (usuario == null || !usuario.getSenha_Usuario().equals(usuarioLoginDto.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Usuário ou senha incorretos"));
        }

        // 3. Verifica se o usuário está ATIVO
        if (usuario.getStatusUsuarioAtivo() != StatusUsuarioAtivo.ATIVO) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", "Usuário inativo. Contate o administrador."));
        }

        // 4. Retorna sucesso (sem token por enquanto)
        return ResponseEntity.ok().body(Map.of(
            "message", "Login bem-sucedido",
            "usuario", usuario.getNomeUsuario(),
            "idUsuario", usuario.getIdUsuario() // Pode ser útil para o frontend
        ));
    }
}