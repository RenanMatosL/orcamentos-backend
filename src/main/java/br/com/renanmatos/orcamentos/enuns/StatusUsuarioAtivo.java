package br.com.renanmatos.orcamentos.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

//Enum presente na PRÓPRIA classe (pois é de uso exclusivo dela
public enum StatusUsuarioAtivo {

	ATIVO(1, "Usuario ativo"),
	INATIVO(0, "Usuario inativo");
			
	private int value;
	private String Descriçao;
			
	StatusUsuarioAtivo (int value, String Descriçao) {
		this.value = value;
		this.setDescriçao(Descriçao);
	}
			
	//Caso esse enum seja utilizado pare receber ou retornar dados JSON de requisições, essa anotação indica que deverá ser considerado o valor NUMÉRICO desse atributo
	@JsonValue
	public Integer toInt() {
		return value;
	}

	public String getDescriçao() {
		return Descriçao;
	}

	public void setDescriçao(String descriçao) {
		Descriçao = descriçao;
	}
}

