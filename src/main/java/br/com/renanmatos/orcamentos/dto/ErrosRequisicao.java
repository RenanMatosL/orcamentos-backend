package br.com.renanmatos.orcamentos.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrosRequisicao {
	
	private List <ErroProcessamento> erros = new ArrayList <> ();

	public List<ErroProcessamento> getErros() {
		return erros;
	}

	public void setErros(List<ErroProcessamento> erros) {
		this.erros = erros;
	}
	
	

}
