package com.desafiojavareact.gerenciadordeprojetos.dto;
import com.desafiojavareact.gerenciadordeprojetos.enums.RiscoProjeto;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;

import java.util.Date;
import java.util.List;

public record ProjetoResponseDTO(
        Long id,
        String nome,
        Date dataInicio,
        Date dataPrevisaoFim,
        Date dataFim,
        String descricao,
        StatusProjeto status,
        Double orcamento,
        RiscoProjeto risco,
        Long idGerente,

        String nomeGerente,

        List<FuncionarioDTO> funcionarios
) {

}

