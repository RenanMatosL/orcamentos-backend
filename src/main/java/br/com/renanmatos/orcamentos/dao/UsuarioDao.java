package br.com.renanmatos.orcamentos.dao;

import java.util.List;

import br.com.renanmatos.orcamentos.enuns.StatusUsuarioAtivo;
import br.com.renanmatos.orcamentos.excecoes.RegistroJaExisteException;
import br.com.renanmatos.orcamentos.excecoes.RegistroNaoEncontradoException;
import br.com.renanmatos.orcamentos.model.Usuario;

public interface UsuarioDao {
    List<Usuario> carregarTodosUsuarios();
    Usuario buscarPorNomeUsuario(String nomeUsuario);
    Usuario buscarPorCpf(String cpf);
    Usuario buscarPorEmail(String emailUsuario);
    Usuario consultarUsuarioPorId(Long idUsuario);
    Usuario salvarUsuario(Usuario usuario) throws RegistroJaExisteException;
    Usuario alterarUsuario(Usuario usuario) throws RegistroNaoEncontradoException, RegistroJaExisteException;
    void alterarStatusUsuario(Long idUsuario, StatusUsuarioAtivo statusUsuario) throws RegistroNaoEncontradoException;
    void validarCamposUnicos(Usuario usuario) throws RegistroJaExisteException;
}

