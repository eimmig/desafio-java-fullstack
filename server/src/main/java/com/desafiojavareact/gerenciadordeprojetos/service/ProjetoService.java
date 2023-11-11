package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dao.ProjetoRepository;
import com.desafiojavareact.gerenciadordeprojetos.dto.ProjetoRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.ExclusaoDeUsuarioException;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    @Autowired
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto salvarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto buscarPorId(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public void atualizarProjeto(Projeto projeto, ProjetoRequestDTO projetoRequestDTO) {
        projeto.setNome(projetoRequestDTO.nome());
        projeto.setDataInicio(projetoRequestDTO.dataInicio());
        projeto.setDataPrevisaoFim(projetoRequestDTO.dataPrevisaoFim());
        projeto.setDataFim(projetoRequestDTO.dataFim());
        projeto.setDescricao(projetoRequestDTO.descricao());
        projeto.setStatus(projetoRequestDTO.status());
        projeto.setOrcamento(projetoRequestDTO.orcamento());
        projeto.setRisco(projetoRequestDTO.risco());
        projetoRepository.save(projeto);
    }

    public void excluirProjeto(Long id) {
        projetoRepository.deleteById(id);
    }

    public void validarPossibilidadeExclusao(Projeto projeto) {
        if (!(projeto.getStatus() == StatusProjeto.EM_ANALISE || projeto.getStatus() == StatusProjeto.ANALISE_REALIZADA || projeto.getStatus() == StatusProjeto.ANALISE_APROVADA)) {
            throw new ExclusaoDeUsuarioException("Projetos já inicializados não podem ser excluídos!");
        }
    }
}