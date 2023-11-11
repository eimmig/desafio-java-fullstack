package com.desafiojavareact.gerenciadordeprojetos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MembrosId implements Serializable {

    private Long idPessoa;
    private Long idProjeto;
}
