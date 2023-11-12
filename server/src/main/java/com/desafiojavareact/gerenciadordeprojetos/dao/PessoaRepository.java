package com.desafiojavareact.gerenciadordeprojetos.dao;

import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByGerenteIsTrue();

    List<Pessoa> findByFuncionarioIsTrue();

    long countByGerente(boolean b);

    long countByFuncionario(boolean b);
}