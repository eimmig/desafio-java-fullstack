package com.desafiojavareact.gerenciadordeprojetos.dao;

import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.ProjetoRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.enums.RiscoProjeto;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import com.desafiojavareact.gerenciadordeprojetos.service.PessoaService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
class ProjetoRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    ProjetoRepository projetoRepository;

    @Test
    @Transactional
    @DisplayName("Retornar a soma do orçamento caso tenha projetos.")
    void sumOrcamentoValue() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("Teste", new Date(2002-06-13), "123-456-789-00", false, true, "");
        Pessoa pessoa = createPessoa(pessoaRequestDTO);

        ProjetoRequestDTO projetoRequestDTO = new ProjetoRequestDTO(1L, "Caso de Teste 1", new Date(), new Date(), new Date(), "Caso de Teste 1", StatusProjeto.EM_ANALISE, 100.00, RiscoProjeto.ALTO_RISCO, pessoa.getId(), "Teste", null);
        Projeto projeto = createProjeto(projetoRequestDTO);

        double result = this.projetoRepository.sumOrcamento();

        assertThat(result == projeto.getOrcamento());
    }

    @Test
    @Transactional
    @DisplayName("Retornar a soma do orçamento caso nao projetos.")
    void sumOrcamentoNoValue() {

        double result = this.projetoRepository.sumOrcamento();

        assertThat(result == 0.00);
    }

    private Projeto createProjeto(ProjetoRequestDTO projetoRequestDTO) {
        Projeto projeto = new Projeto(projetoRequestDTO, this.pessoaService);
        this.entityManager.persist(projeto);
        return projeto;
    }

    private Pessoa createPessoa(PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = new Pessoa(pessoaRequestDTO);
        this.entityManager.persist(pessoa);
        return pessoa;
    }
}