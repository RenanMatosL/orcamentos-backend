package br.com.renanmatos.orcamentos.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanmatos.orcamentos.service.UsuarioService;
import br.com.renanmatos.orcamentos.excecoes.RegistroJaExisteException;
import br.com.renanmatos.orcamentos.model.Usuario;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Alterado para usar o Service

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) throws RegistroJaExisteException {
        try {
            usuarioService.salvarUsuario(usuario); // Agora chamamos o Service, que gerencia transações
            return ResponseEntity.ok().body(Map.of("message", "Usuário cadastrado com sucesso!"));
        } catch (RegistroJaExisteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Nome de usuário já existe!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Erro ao cadastrar usuário."));
        }
    }
}
