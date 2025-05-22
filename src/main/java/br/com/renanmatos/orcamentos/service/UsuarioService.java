package br.com.renanmatos.orcamentos.service;

import java.util.List;

import br.com.renanmatos.orcamentos.dto.UsuarioDto;
import br.com.renanmatos.orcamentos.enuns.StatusUsuarioAtivo;
import br.com.renanmatos.orcamentos.excecoes.RegistroJaExisteException;
import br.com.renanmatos.orcamentos.excecoes.RegistroNaoEncontradoException;
import br.com.renanmatos.orcamentos.excecoes.RequestInvalidoException;
import br.com.renanmatos.orcamentos.model.Usuario;

public interface UsuarioService {
	public List<Usuario> carregarTodosUsuarios();

	public Usuario consultarUsuarioPorId(Long idUsuario);

	public Usuario salvarUsuario(Usuario usuario) throws RegistroJaExisteException;

	public Usuario alterarUsuario(Usuario usuario) throws RegistroNaoEncontradoException, RegistroJaExisteException;

	public void alterarStatusUsuario(Long idUsuario, StatusUsuarioAtivo statusUsuarioAtivo) throws RegistroNaoEncontradoException;

	public List<UsuarioDto> getListaUsuarioDtoPorUsuario(List<Usuario> listaUsuario);

	public UsuarioDto getUsuarioDtoPorUsuario(Usuario usuario);

	public Usuario getUsuarioPorUsuarioDto(UsuarioDto usuarioDto);

	public void validarUsuarioDtoParaCadastro(UsuarioDto usuarioDto) throws RequestInvalidoException;

	public void validarUsuarioDtoParaAlteracao(UsuarioDto usuarioDto) throws RequestInvalidoException;
}
