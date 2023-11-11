package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dao.MembrosRepository;
import com.desafiojavareact.gerenciadordeprojetos.dao.PessoaRepository;
import com.desafiojavareact.gerenciadordeprojetos.dao.ProjetoRepository;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.RegraDeNegocioException;
import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final MembrosRepository membrosRepository;

    private final ProjetoRepository projetoRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, MembrosRepository membrosRepository, ProjetoRepository projetoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.membrosRepository = membrosRepository;
        this.projetoRepository = projetoRepository;
    }

    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public void atualizarPessoa(Pessoa pessoa, PessoaRequestDTO pessoaRequestDTO) {
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setDataNascimento(pessoaRequestDTO.dataNascimento());
        pessoa.setCpf(pessoaRequestDTO.cpf());
        pessoa.setFuncionario(pessoaRequestDTO.funcionario());
        pessoa.setGerente(pessoaRequestDTO.gerente());
        pessoaRepository.save(pessoa);
    }

    public void excluirPessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<Projeto> buscarProjetos(Pessoa pessoa) {

        List<Membros> membros = membrosRepository.findByPessoaId(pessoa.getId());

        List<Long> projetoIds = membros.stream()
                .map(membro -> membro.getProjeto().getId())
                .collect(Collectors.toList());

        return projetoRepository.findAllById(projetoIds);
    }

    public void validarPossibilidadeExclusao(Projeto projeto) {
        if (!(projeto.getStatus() == StatusProjeto.EM_ANALISE || projeto.getStatus() == StatusProjeto.ANALISE_REALIZADA || projeto.getStatus() == StatusProjeto.ANALISE_APROVADA)) {
            throw new RegraDeNegocioException("Projetos já inicializados não podem ser excluídos!");
        }
    }
}