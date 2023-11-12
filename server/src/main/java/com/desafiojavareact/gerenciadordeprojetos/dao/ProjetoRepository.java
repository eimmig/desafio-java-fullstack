package com.desafiojavareact.gerenciadordeprojetos.dao;

import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByIdGerente(Pessoa pessoa);

    @Query("SELECT COALESCE(SUM(p.orcamento), 0) FROM Projeto p")
    double sumOrcamento();
}
