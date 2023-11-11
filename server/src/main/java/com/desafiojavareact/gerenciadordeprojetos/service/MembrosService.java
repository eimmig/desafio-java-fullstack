package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dao.MembrosRepository;
import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MembrosService {

    private final MembrosRepository membrosRepository;

    @Autowired
    public MembrosService(MembrosRepository membrosRepository) {
        this.membrosRepository = membrosRepository;
    }

    public Membros salvarMembro(Membros membros) {
        return membrosRepository.save(membros);
    }

    public void excluirMembro(Membros membros) {
        membrosRepository.delete(membros);
    }

    public List<Membros> findByProjetoId(Long idProjeto) {
        return membrosRepository.findByProjetoId(idProjeto);
    }

    public List<Membros> findByPessoaId(Long idPessoa) {
        return membrosRepository.findByPessoaId(idPessoa);
    }

    public Membros findByPessoaIdAndProjetoId(Long idPessoa, Long idProjeto) {
        return membrosRepository.findByPessoaIdAndProjetoId(idPessoa, idProjeto);
    }

}
