package com.desafiojavareact.gerenciadordeprojetos.model;

import com.desafiojavareact.gerenciadordeprojetos.dto.ProjetoRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.enums.RiscoProjeto;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.service.PessoaService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    @OneToOne
    @JoinColumn(name = "idgerente")
    private Pessoa idGerente;

    // Construtor
    public Projeto(ProjetoRequestDTO data, PessoaService pessoaService) {
        this.nome = data.nome();
        this.dataInicio = data.dataInicio();
        this.dataPrevisaoFim = data.dataPrevisaoFim();
        this.dataFim = data.dataFim();
        this.descricao = data.descricao();
        this.status = data.status();
        this.orcamento = data.orcamento();
        this.risco = data.risco();
        this.idGerente = pessoaService.buscarPorId(data.idGerente());
    }

    public Projeto (Projeto projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.dataInicio = projeto.getDataInicio();
        this.dataPrevisaoFim = projeto.getDataPrevisaoFim();
        this.dataFim = projeto.getDataFim();
        this.descricao =projeto.getDescricao();
        this.status = projeto.getStatus();
        this.orcamento = projeto.getOrcamento();
        this.risco = projeto.getRisco();
        this.idGerente = projeto.getIdGerente();
    }
}

