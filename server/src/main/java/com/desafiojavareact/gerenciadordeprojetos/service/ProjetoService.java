package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dao.ProjetoRepository;
import com.desafiojavareact.gerenciadordeprojetos.dto.*;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.ProjetoJaIniciadoException;
import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    public List<Projeto> buscarPorIdGerente(Pessoa pessoa) {
        return projetoRepository.findByIdGerente(pessoa);
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
            throw new ProjetoJaIniciadoException("Projetos já inicializados não podem ser excluídos!");
        }
    }
    public ProjetoResponseDTO consultarProjetoComGerenteEFuncionarios(Projeto projeto, List<Membros> membros) {
        List<FuncionarioDTO> funcionarioDTO = membros.stream()
                .map(membro -> new FuncionarioDTO(membro.getPessoa().getId(), membro.getPessoa().getNome()))
                .collect(Collectors.toList());

        return new ProjetoResponseDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDataInicio(),
                projeto.getDataPrevisaoFim(),
                projeto.getDataFim(),
                projeto.getDescricao(),
                projeto.getStatus(),
                projeto.getOrcamento(),
                projeto.getRisco(),
                projeto.getIdGerente().getId(),
                projeto.getIdGerente().getNome(),
                funcionarioDTO
        );
    }

    public ProjetoStats getStats() {
        long totalProjetos = projetoRepository.count();
        double somaOrcamento = projetoRepository.sumOrcamento();

        return new ProjetoStats(totalProjetos, somaOrcamento);
    }
}