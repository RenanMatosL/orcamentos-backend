package br.com.renanmatos.orcamentos.excecoes;

import br.com.renanmatos.orcamentos.dto.ErrosRequisicao;

public class RequestInvalidoException extends Exception{
	
	private ErrosRequisicao errosRequisicao;
	
	public RequestInvalidoException (String mensagemErro, ErrosRequisicao errosRequisicao, final Throwable throwable) {
		super(mensagemErro, throwable);
		this.errosRequisicao = errosRequisicao;
    	}

	public ErrosRequisicao getErrosRequisicao() {
		return errosRequisicao;
	}

	public void setErrosRequisicao(ErrosRequisicao errosRequisicao) {
		this.errosRequisicao = errosRequisicao;
	}
	
}
