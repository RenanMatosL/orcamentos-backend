package br.com.renanmatos.orcamentos.validacao;

import jakarta.validation.GroupSequence;

//Indicamos os grupos de validação a serem considerados por essa interface, respeitando a ordem de validação conforme constar esses grupos na lista
@GroupSequence({ValidacaoCadastro.class, ValidacaoAlteracao.class})
public interface SequenciaValidacaoPadrao {
}

