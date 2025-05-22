package br.com.renanmatos.orcamentos.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.renanmatos.orcamentos.enuns.StatusUsuarioAtivo;
import br.com.renanmatos.orcamentos.excecoes.RegistroJaExisteException;
import br.com.renanmatos.orcamentos.excecoes.RegistroNaoEncontradoException;
import br.com.renanmatos.orcamentos.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Usuario> carregarTodosUsuarios() {
        String jpql = "SELECT DISTINCT u FROM Usuario u ORDER BY u.nomeUsuario";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        return typedQuery.getResultList();
    }

    @Override
    public Usuario buscarPorNomeUsuario(String nomeUsuario) {
        String jpql = "SELECT u FROM Usuario u WHERE u.nomeUsuario = :nomeUsuario";
        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        query.setParameter("nomeUsuario", nomeUsuario);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario buscarPorCpf(String cpf) {
        String jpql = "SELECT u FROM Usuario u WHERE u.cpf = :cpf";
        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        query.setParameter("cpf", cpf);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario buscarPorEmail(String emailUsuario) {
        String jpql = "SELECT u FROM Usuario u WHERE u.emailUsuario = :emailUsuario";
        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        query.setParameter("emailUsuario", emailUsuario);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario consultarUsuarioPorId(Long idUsuario) {
        String jpql = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        typedQuery.setParameter("idUsuario", idUsuario);

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Transactional
    @Override
    public Usuario salvarUsuario(Usuario usuario) throws RegistroJaExisteException {
        validarCamposUnicos(usuario);
        entityManager.persist(usuario);
        return usuario;
    }

    @Override
    public Usuario alterarUsuario(Usuario usuario) throws RegistroNaoEncontradoException, RegistroJaExisteException {
        if (consultarUsuarioPorId(usuario.getIdUsuario()) == null) {
            throw new RegistroNaoEncontradoException("Usuário de ID " + usuario.getIdUsuario() + " não encontrado.");
        }

        validarCamposUnicos(usuario);
        entityManager.merge(usuario);
        entityManager.flush();
        return usuario;
    }

    @Override
    public void alterarStatusUsuario(Long idUsuario, StatusUsuarioAtivo statusUsuarioAtivo) throws RegistroNaoEncontradoException {
        String jpql = "UPDATE Usuario u SET u.statusUsuarioAtivo = :statusUsuarioAtivo WHERE u.idUsuario = :idUsuario";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("statusUsuarioAtivo", statusUsuarioAtivo);
        query.setParameter("idUsuario", idUsuario);

        int affectedRows = query.executeUpdate();
        if (affectedRows == 0) {
            throw new RegistroNaoEncontradoException("Usuário de ID " + idUsuario + " não encontrado.");
        }
    }

    @Override
    public void validarCamposUnicos(Usuario usuario) throws RegistroJaExisteException {
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.cpf = :cpf OR u.emailUsuario = :emailUsuario";
        
        if (usuario.getIdUsuario() != null) {
            jpql += " AND u.idUsuario <> :idUsuario";
        }

        TypedQuery<Long> typedQuery = entityManager.createQuery(jpql, Long.class);
        typedQuery.setParameter("cpf", usuario.getCpf());
        typedQuery.setParameter("emailUsuario", usuario.getEmailUsuario());

        if (usuario.getIdUsuario() != null) {
            typedQuery.setParameter("idUsuario", usuario.getIdUsuario());
        }

        Long count = typedQuery.getSingleResult();
        if (count > 0) {
            throw new RegistroJaExisteException("Já existe um usuário com esse CPF ou e-mail.");
        }
    }
}
