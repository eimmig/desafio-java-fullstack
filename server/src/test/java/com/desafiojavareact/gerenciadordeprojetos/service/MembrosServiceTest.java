package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dto.FuncionarioDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.ProjetoRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.enums.RiscoProjeto;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class MembrosServiceTest {

    @InjectMocks
    private ProjetoService projetoService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    MembrosService membrosService;
    @Test
    @Transactional
    @DisplayName("Teste criar membros e vincular ao projeto/pessoa sem funcionario")
    void criarMembrosSemFuncionario() {
        Pessoa pessoa = createPessoa();
        Projeto projeto = createProjeto(pessoa);
        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO(projeto.getId(), "Caso de Teste 1", new Date(), new Date(), new Date(), "Caso de Teste 1", StatusProjeto.EM_ANALISE, 100.00, RiscoProjeto.ALTO_RISCO, pessoa.getId(), "Teste", null);

        membrosService.criarMembros(projetoRequestDTO, projeto);

        List<Membros> membros = membrosService.findByProjetoId(projeto.getId());

        assertThat(membros.isEmpty());

    }

    @Test
    @Transactional
    @DisplayName("Teste criar membros e vincular ao projeto/pessoa com funcionario")
    void criarMembros() {
        Pessoa pessoa = createPessoa();
        Projeto projeto = createProjeto(pessoa);

        List<FuncionarioDTO> funcionarioDTO = new ArrayList<>();

        FuncionarioDTO funcionarioDTO1 = new FuncionarioDTO(pessoa.getId(), pessoa.getNome());

        funcionarioDTO.add(funcionarioDTO1);

        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO(projeto.getId(), "Caso de Teste 1", new Date(), new Date(), new Date(), "Caso de Teste 1", StatusProjeto.EM_ANALISE, 100.00, RiscoProjeto.ALTO_RISCO, pessoa.getId(), "Teste", funcionarioDTO);

        membrosService.criarMembros(projetoRequestDTO, projeto);

        List<Membros> membros = membrosService.findByProjetoId(projeto.getId());

        assertThat(!membros.isEmpty());

    }

    private Projeto createProjeto(Pessoa pessoa) {
        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO(1L, "Caso de Teste 1", new Date(), new Date(), new Date(), "Caso de Teste 1", StatusProjeto.EM_ANALISE, 100.00, RiscoProjeto.ALTO_RISCO, pessoa.getId(), "Teste", null);
        Projeto projeto = new Projeto(projetoRequestDTO, this.pessoaService);
        this.entityManager.persist(projeto);
        return projeto;
    }

    private Pessoa createPessoa() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("Teste", new Date(2002-06-13), "123-456-789-00", false, true, "");
        Pessoa pessoa = new Pessoa(pessoaRequestDTO);
        this.entityManager.persist(pessoa);
        return pessoa;
    }
}