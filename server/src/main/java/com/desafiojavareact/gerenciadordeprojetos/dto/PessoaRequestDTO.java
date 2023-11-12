package com.desafiojavareact.gerenciadordeprojetos.dto;

import java.util.Date;

public record PessoaRequestDTO(
        String nome,
        Date dataNascimento,
        String cpf,
        Boolean funcionario,
        Boolean gerente,

        String cargo
) {

}
