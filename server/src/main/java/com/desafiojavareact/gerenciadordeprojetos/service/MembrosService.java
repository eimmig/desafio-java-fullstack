package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dao.MembrosRepository;
import com.desafiojavareact.gerenciadordeprojetos.dto.FuncionarioDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.ProjetoRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.PessoaNaoEncontradaException;
import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class MembrosService {

    private final MembrosRepository membrosRepository;

    private final PessoaService pessoaService;

    @Autowired
    public MembrosService(MembrosRepository membrosRepository, PessoaService pessoaService) {
        this.membrosRepository = membrosRepository;
        this.pessoaService = pessoaService;
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

    public void criarMembros(@RequestBody ProjetoRequestDTO projetoRequestDTO, Projeto projeto) {
        if (!(projetoRequestDTO.funcionarios() == null) && !projetoRequestDTO.funcionarios().isEmpty()) {
            List<FuncionarioDTO> funcionarios = projetoRequestDTO.funcionarios();
            for (FuncionarioDTO funcionario : funcionarios) {
                Pessoa pessoa = pessoaService.buscarPorId(funcionario.value());
                if (pessoa != null) {
                    Membros membroExiste = this.findByPessoaIdAndProjetoId(pessoa.getId(), projeto.getId());
                    if (membroExiste == null) {
                        Membros membros = new Membros(pessoa, projeto);
                        this.salvarMembro(membros);
                    }
                } else {
                    throw new PessoaNaoEncontradaException("Funcionario não encontrado na base de dados.");
                }
            }
        }
    }

}
