package com.desafiojavareact.gerenciadordeprojetos.model;

import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestNomeECargoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "datanascimento")
    private Date dataNascimento;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "funcionario")
    private boolean funcionario;

    @Column(name = "gerente")
    private boolean gerente;

    // Construtor
    public Pessoa(PessoaRequestDTO data) {
        this.nome = data.nome();
        this.dataNascimento = data.dataNascimento();
        this.cpf = data.cpf();
        this.funcionario = data.funcionario();
        this.gerente = data.gerente();
    }

    public Pessoa(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.dataNascimento = pessoa.getDataNascimento();
        this.cpf = pessoa.getCpf();
        this.funcionario = pessoa.isFuncionario();
        this.gerente = pessoa.isGerente();
    }

    public Pessoa(PessoaRequestNomeECargoDTO pessoaRequestNomeECargoDTO) {
        this.nome = pessoaRequestNomeECargoDTO.nome();
        if (pessoaRequestNomeECargoDTO.cargo().equals("funcionario")) {
            this.funcionario = true;
            this.gerente = false;
        } else {
            this.funcionario = false;
            this.gerente = true;
        }
    }
}


