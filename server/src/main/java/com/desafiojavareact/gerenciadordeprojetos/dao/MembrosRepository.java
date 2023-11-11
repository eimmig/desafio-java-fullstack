package com.desafiojavareact.gerenciadordeprojetos.dao;

import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.MembrosId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembrosRepository extends JpaRepository<Membros, MembrosId> {
    List<Membros> findByProjetoId(Long idProjeto);

    List<Membros> findByPessoaId(Long idPessoa);

    Membros findByPessoaIdAndProjetoId(Long idPessoa, Long idProjeto);
}

