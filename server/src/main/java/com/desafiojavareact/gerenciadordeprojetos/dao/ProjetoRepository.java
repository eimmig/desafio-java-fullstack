package com.desafiojavareact.gerenciadordeprojetos.dao;

import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

}
