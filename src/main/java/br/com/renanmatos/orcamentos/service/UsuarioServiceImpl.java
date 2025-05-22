package br.com.renanmatos.orcamentos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.com.renanmatos.orcamentos.dao.UsuarioDao;
import br.com.renanmatos.orcamentos.dto.ErroProcessamento;
import br.com.renanmatos.orcamentos.dto.ErrosRequisicao;
import br.com.renanmatos.orcamentos.dto.UsuarioDto;
import br.com.renanmatos.orcamentos.enuns.StatusUsuarioAtivo;
import br.com.renanmatos.orcamentos.excecoes.RegistroJaExisteException;
import br.com.renanmatos.orcamentos.excecoes.RegistroNaoEncontradoException;
import br.com.renanmatos.orcamentos.excecoes.RequestInvalidoException;
import br.com.renanmatos.orcamentos.model.Usuario;
import br.com.renanmatos.orcamentos.validacao.ValidacaoAlteracao;
import br.com.renanmatos.orcamentos.validacao.ValidacaoCadastro;
import jakarta.validation.ConstraintViolation;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Log logger = LogFactory.getLog(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    @Qualifier("localValidatorFactoryBeanPadrao")
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Usuario> carregarTodosUsuarios() {
        try {
            return usuarioDao.carregarTodosUsuarios();
        } catch (Exception e) {
            logger.error("Erro ao consultar todos os usuários", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Usuario consultarUsuarioPorId(Long idUsuario) {
        try {
            return usuarioDao.consultarUsuarioPorId(idUsuario);
        } catch (Exception e) {
            logger.error("Erro ao consultar usuário por ID: " + idUsuario, e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Usuario salvarUsuario(Usuario usuario) throws RegistroJaExisteException {
        try {
            usuario.setStatusUsuarioAtivo(StatusUsuarioAtivo.ATIVO);

            return usuarioDao.salvarUsuario(usuario);
        } catch (RegistroJaExisteException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao salvar usuário. Nome: " + usuario.getNomeUsuario(), e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Usuario alterarUsuario(Usuario usuario) throws RegistroNaoEncontradoException, RegistroJaExisteException {
        try {
            Usuario usuarioBase = usuarioDao.consultarUsuarioPorId(usuario.getIdUsuario());

            if (usuarioBase == null) {
                throw new RegistroNaoEncontradoException("Usuário de id " + usuario.getIdUsuario() + " não encontrado.");
            }

            usuarioBase.setNomeUsuario(usuario.getNomeUsuario());
            usuarioBase.setCpf(usuario.getCpf());
            usuarioBase.setEmailUsuario(usuario.getEmailUsuario());

            return usuarioDao.alterarUsuario(usuarioBase);
        } catch (RegistroJaExisteException | RegistroNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao alterar usuário. ID: " + usuario.getIdUsuario(), e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void alterarStatusUsuario(Long idUsuario, StatusUsuarioAtivo statusUsuarioAtivo)
            throws RegistroNaoEncontradoException {
        try {
            usuarioDao.alterarStatusUsuario(idUsuario, statusUsuarioAtivo);
        } catch (RegistroNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao alterar status do usuário. ID: " + idUsuario, e);
            throw e;
        }
    }

    @Override
    public List<UsuarioDto> getListaUsuarioDtoPorUsuario(List<Usuario> listaUsuario) {
        if (listaUsuario == null || listaUsuario.isEmpty()) {
            return null;
        }

        List<UsuarioDto> listaUsuarioDto = new ArrayList<>();
        for (Usuario usuario : listaUsuario) {
            listaUsuarioDto.add(getUsuarioDtoPorUsuario(usuario));
        }

        return listaUsuarioDto;
    }

    @Override
    public UsuarioDto getUsuarioDtoPorUsuario(Usuario usuario) {
        if (usuario != null) {
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setIdUsuario(usuario.getIdUsuario());
            usuarioDto.setNomeUsuario(usuario.getNomeUsuario());
            usuarioDto.setCpf(usuario.getCpf());
            usuarioDto.setEmailUsuario(usuario.getEmailUsuario());
            usuarioDto.setStatusUsuarioAtivo(usuario.getStatusUsuarioAtivo());

            return usuarioDto;
        }
        return null;
    }

    @Override
    public Usuario getUsuarioPorUsuarioDto(UsuarioDto usuarioDto) {
        if (usuarioDto != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(usuarioDto.getIdUsuario());
            usuario.setNomeUsuario(usuarioDto.getNomeUsuario());
            usuario.setCpf(usuarioDto.getCpf());
            usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
            usuario.setStatusUsuarioAtivo(usuarioDto.getStatusUsuarioAtivo());

            return usuario;
        }
        return null;
    }

    @Override
    public void validarUsuarioDtoParaCadastro(UsuarioDto usuarioDto) throws RequestInvalidoException {
        Set<ConstraintViolation<UsuarioDto>> errosValidacao = localValidatorFactoryBean.validate(usuarioDto, ValidacaoCadastro.class);

        if (errosValidacao != null && !errosValidacao.isEmpty()) {
            ErrosRequisicao errosRequisicao = new ErrosRequisicao();
            for (ConstraintViolation<UsuarioDto> erro : errosValidacao) {
                errosRequisicao.getErros().add(new ErroProcessamento(null, erro.getMessage()));
            }
            throw new RequestInvalidoException("Requisição inválida", errosRequisicao, null);
        }
    }

    @Override
    public void validarUsuarioDtoParaAlteracao(UsuarioDto usuarioDto) throws RequestInvalidoException {
        Set<ConstraintViolation<UsuarioDto>> errosValidacao = localValidatorFactoryBean.validate(usuarioDto, ValidacaoAlteracao.class);

        if (errosValidacao != null && !errosValidacao.isEmpty()) {
            ErrosRequisicao errosRequisicao = new ErrosRequisicao();
            for (ConstraintViolation<UsuarioDto> erro : errosValidacao) {
                errosRequisicao.getErros().add(new ErroProcessamento(null, erro.getMessage()));
            }
            throw new RequestInvalidoException("Requisição inválida", errosRequisicao, null);
        }
    }
}
