package com.desafiojavareact.gerenciadordeprojetos.model;

import com.desafiojavareact.gerenciadordeprojetos.dto.ProjetoRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.enums.RiscoProjeto;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "data_inicio")
    private Date dataInicio;

    @Column(name = "data_previsao_fim")
    private Date dataPrevisaoFim;

    @Column(name = "data_fim")
    private Date dataFim;

    @Column(name = "descricao", length = 5000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 45)
    private StatusProjeto status;

    @Column(name = "orcamento")
    private Double orcamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "risco", length = 45)
    private RiscoProjeto risco;

    @Column(name = "idgerente", nullable = false)
    private Long idGerente;

    // Construtor
    public Projeto(ProjetoRequestDTO data) {
        this.nome = data.nome();
        this.dataInicio = data.dataInicio();
        this.dataPrevisaoFim = data.dataPrevisaoFim();
        this.dataFim = data.dataFim();
        this.descricao = data.descricao();
        this.status = data.status();
        this.orcamento = data.orcamento();
        this.risco = data.risco();
        this.idGerente = data.idGerente();
    }
}

