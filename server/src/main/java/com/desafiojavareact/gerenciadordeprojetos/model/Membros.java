package com.desafiojavareact.gerenciadordeprojetos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "membros")
public class Membros {

    @EmbeddedId
    private MembrosId id;

    @ManyToOne
    @MapsId("idProjeto")
    @JoinColumn(name = "idprojeto")
    private Projeto projeto;

    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "idpessoa")
    private Pessoa pessoa;


    public  Membros(Pessoa pessoa, Projeto projeto) {
        this.pessoa = pessoa;
        this.projeto = projeto;
        this.id = new MembrosId(projeto.getId(), pessoa.getId());
    }
}