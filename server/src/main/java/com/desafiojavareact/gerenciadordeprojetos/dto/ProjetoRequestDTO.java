package com.desafiojavareact.gerenciadordeprojetos.dto;

import com.desafiojavareact.gerenciadordeprojetos.enums.RiscoProjeto;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;

import java.util.Date;

public record ProjetoRequestDTO(
        String nome,
        Date dataInicio,
        Date dataPrevisaoFim,
        Date dataFim,
        String descricao,
        StatusProjeto status,
        Double orcamento,
        RiscoProjeto risco,
        Long idGerente
) {
}
