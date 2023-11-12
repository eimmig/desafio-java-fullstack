package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dao.MembrosRepository;
import com.desafiojavareact.gerenciadordeprojetos.dao.PessoaRepository;
import com.desafiojavareact.gerenciadordeprojetos.dao.ProjetoRepository;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaStats;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.ExclusaoDeUsuarioException;
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

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
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

    public List<Pessoa> buscarGerentes() {
        return pessoaRepository.findByGerenteIsTrue();
    }

    public List<Pessoa> buscarFuncionarios() {
        return pessoaRepository.findByFuncionarioIsTrue();
    }


    public PessoaStats getStats() {
        long totalPessoas = pessoaRepository.count();
        long totalGerentes = pessoaRepository.countByGerente(true);
        long totalFuncionarios = pessoaRepository.countByFuncionario(true);

        return new PessoaStats(totalPessoas, totalGerentes, totalFuncionarios);
    }
}