package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dao.ProjetoRepository;
import com.desafiojavareact.gerenciadordeprojetos.dto.*;
import com.desafiojavareact.gerenciadordeprojetos.enums.RiscoProjeto;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.ProjetoJaIniciadoException;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ProjetoServiceTest {

    @InjectMocks
    private ProjetoService projetoService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PessoaService pessoaService;
    @Test
    @Transactional
    @DisplayName("Atualizar Projeto")
    void atualizarProjeto() {
        Pessoa pessoa = this.createPessoa();
        Projeto projeto = this.createProjeto(pessoa);
        Projeto projetoClone = new Projeto(projeto);

        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO(projeto.getId(), "Caso de Teste 1", new Date(), new Date(), new Date(), "Caso de Teste 1", StatusProjeto.EM_ANALISE, 100.00, RiscoProjeto.ALTO_RISCO, pessoa.getId(), "Teste", null);
        projetoService.atualizarProjeto(projeto, projetoRequestDTO);

        assertThat(!projetoClone.equals(projeto));
    }

    @Test
    @Transactional
    @DisplayName("Validar Possibilidade Exclusao - Projeto Não Iniciado")
    void validarPossibilidadeExclusaoProjetoNaoIniciado() {
        Pessoa pessoa = this.createPessoa();
        Projeto projeto = this.createProjeto(pessoa);
        projeto.setStatus(StatusProjeto.EM_ANALISE);

        assertDoesNotThrow(() -> projetoService.validarPossibilidadeExclusao(projeto));
    }

    @Test
    @Transactional
    @DisplayName("Validar Possibilidade Exclusao - Projeto Iniciado")
    void validarPossibilidadeExclusaoProjetoIniciado() {
        Pessoa pessoa = this.createPessoa();
        Projeto projeto = this.createProjeto(pessoa);
        projeto.setStatus(StatusProjeto.INICIADO);

        ProjetoJaIniciadoException exception = assertThrows(ProjetoJaIniciadoException.class, () -> {
            projetoService.validarPossibilidadeExclusao(projeto);
        });

        assertEquals("Projetos já inicializados não podem ser excluídos!", exception.getMessage());
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