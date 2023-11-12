package com.desafiojavareact.gerenciadordeprojetos.service;

import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class PessoaServiceTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    PessoaService pessoaService;
    @Test
    @Transactional
    @DisplayName("Validar Edição de pessoa.")
    void atualizarPessoa() {
        Pessoa pessoa = this.createPessoa();
        Pessoa pessoaClone = new Pessoa(pessoa);

        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("Teste 1", new Date(2002-06-13), "123-456-789-00", false, true, "");
        pessoaService.atualizarPessoa(pessoa, pessoaRequestDTO);

        assertThat(!pessoaClone.equals(pessoa));
    }


    @Test
    @Transactional
    @DisplayName("Validar CPF Valido.")
    void validarCpfValido() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("Teste 1", new Date(2002-06-13), "614.991.490-93", false, true, "");

        Boolean cpfValido = pessoaService.validarCPF(pessoaRequestDTO.cpf());

        assertThat(cpfValido);
    }


    @Test
    @Transactional
    @DisplayName("Validar CPF Invalido.")
    void validarCpfInalido() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("Teste 1", new Date(2002-06-13), "123-456-789-00", false, true, "");

        Boolean cpfValido = pessoaService.validarCPF(pessoaRequestDTO.cpf());

        assertThat(!cpfValido);
    }




    private Pessoa createPessoa() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("Teste", new Date(2002-06-13), "123-456-789-00", false, true, "");
        Pessoa pessoa = new Pessoa(pessoaRequestDTO);
        this.entityManager.persist(pessoa);
        return pessoa;
    }
}