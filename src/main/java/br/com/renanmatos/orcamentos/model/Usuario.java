package br.com.renanmatos.orcamentos.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.renanmatos.orcamentos.enuns.StatusUsuarioAtivo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOME_USUARIO", length = 100, nullable = false, unique = true)
    private String nomeUsuario;

    @Column(name = "EMAIL_USUARIO", length = 100, nullable = false, unique = true)
    private String emailUsuario;

    @Column(name = "SENHA_USUARIO", length = 50, nullable = false)
    private String senha_Usuario;

    @Column(name = "CPF", length = 11, nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USUARIO_ATIVO", nullable = false)
    private StatusUsuarioAtivo statusUsuarioAtivo;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSenha_Usuario() {
        return senha_Usuario;
    }

    public void setSenha_Usuario(String senha_Usuario) {
        this.senha_Usuario = senha_Usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public StatusUsuarioAtivo getStatusUsuarioAtivo() {
        return statusUsuarioAtivo;
    }

    public void setStatusUsuarioAtivo(StatusUsuarioAtivo statusUsuarioAtivo) {
        this.statusUsuarioAtivo = statusUsuarioAtivo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(idUsuario, other.idUsuario);
    }
}
