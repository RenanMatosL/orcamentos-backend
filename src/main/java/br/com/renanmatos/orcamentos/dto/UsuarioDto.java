package br.com.renanmatos.orcamentos.dto;

import java.io.Serializable;

import br.com.renanmatos.orcamentos.enuns.StatusUsuarioAtivo;
import br.com.renanmatos.orcamentos.validacao.ValidacaoAlteracao;
import br.com.renanmatos.orcamentos.validacao.ValidacaoCadastro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioDto implements Serializable {

    @NotNull(
        message = "{validacao.campo-obrigatorio.idUsuario}",
        groups = {ValidacaoAlteracao.class}
    )
    private Long idUsuario;

    @NotEmpty(
        message = "{validacao.campo-obrigatorio.nome}",
        groups = {ValidacaoCadastro.class, ValidacaoAlteracao.class}
    )
    @Size(max = 100, message = "Nome do usuário deve ter no máximo 100 caracteres")
    private String nomeUsuario;

    @NotEmpty(
        message = "{validacao.campo-obrigatorio.cpf}",
        groups = {ValidacaoCadastro.class, ValidacaoAlteracao.class}
    )
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números sem pontos ou traços")
    private String cpf;

    @NotEmpty(
        message = "{validacao.campo-obrigatorio.email}",
        groups = {ValidacaoCadastro.class, ValidacaoAlteracao.class}
    )
    @Email(message = "E-mail deve ser válido")
    private String emailUsuario;

    @NotEmpty(
        message = "{validacao.campo-obrigatorio.senha}",
        groups = {ValidacaoCadastro.class, ValidacaoAlteracao.class}
    )
    @Size(min = 6, max = 50, message = "A senha deve ter entre 6 e 50 caracteres")
    private String senha_Usuario;

    private StatusUsuarioAtivo statusUsuarioAtivo;

    public StatusUsuarioAtivo getStatusUsuarioAtivo() {
        return statusUsuarioAtivo;
    }

    public void setStatusUsuarioAtivo(StatusUsuarioAtivo statusUsuarioAtivo) {
        this.statusUsuarioAtivo = statusUsuarioAtivo;
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
}
