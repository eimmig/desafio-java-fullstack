package com.desafiojavareact.gerenciadordeprojetos.controller;

import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestNomeECargoDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaStats;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.ExclusaoDeUsuarioException;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.cpfInvalidoException;
import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import com.desafiojavareact.gerenciadordeprojetos.service.MembrosService;
import com.desafiojavareact.gerenciadordeprojetos.service.PessoaService;
import com.desafiojavareact.gerenciadordeprojetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;
    private final MembrosService membrosService;
    private final ProjetoService projetoService;

    @Autowired
    public PessoaController(PessoaService pessoaService, MembrosService membrosService, ProjetoService projetoService) {
        this.pessoaService = pessoaService;
        this.membrosService = membrosService;
        this.projetoService = projetoService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> salvarPessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        try {
            Boolean cpfValido = pessoaService.validarCPF(pessoaRequestDTO.cpf());

            if (!cpfValido) {
                throw new cpfInvalidoException("Por favor cadastre um CPF válido");
            }

            pessoaService.salvarPessoa(new Pessoa(pessoaRequestDTO));
            return ResponseEntity.ok("Pessoa salva com sucesso!");
        } catch (cpfInvalidoException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar pessoa: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Pessoa>> getAllPessoas() {
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/stats")
    public ResponseEntity<PessoaStats> getStats() {
        PessoaStats pessoaStats = pessoaService.getStats();

        return ResponseEntity.ok(pessoaStats);
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long pessoaId) {
        Pessoa pessoa = pessoaService.buscarPorId(pessoaId);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getGerentes")
    public ResponseEntity<List<Pessoa>> getGerentes() {
        List<Pessoa> pessoa = pessoaService.buscarGerentes();
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getFuncionarios")
    public ResponseEntity<List<Pessoa>> getFuncionarios() {
        List<Pessoa> pessoa = pessoaService.buscarFuncionarios();
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{pessoaId}")
    public ResponseEntity<String> excluirPessoa(@PathVariable Long pessoaId) {
        try {
            List<Membros> membros = membrosService.findByPessoaId(pessoaId);
            List<Projeto> projetos = projetoService.buscarPorIdGerente(pessoaService.buscarPorId(pessoaId));

            if (!projetos.isEmpty()) {
                throw new ExclusaoDeUsuarioException("A pessoa está vinculada em um ou mais projetos!");
            }

            for (Membros membro : membros) {
                membrosService.excluirMembro(membro);
            }
            pessoaService.excluirPessoa(pessoaId);
            return ResponseEntity.ok("Pessoa excluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir pessoa: " + e.getMessage());
        }
    }

    @PutMapping("/{pessoaId}")
    public ResponseEntity<String> atualizarPessoa(@PathVariable Long pessoaId, @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        try {
            Boolean cpfValido = pessoaService.validarCPF(pessoaRequestDTO.cpf());

            if (!cpfValido) {
                throw new cpfInvalidoException("Por favor cadastre um CPF válido");
            }
            Pessoa pessoa = pessoaService.buscarPorId(pessoaId);
            pessoaService.atualizarPessoa(pessoa, pessoaRequestDTO);
            return ResponseEntity.ok("Pessoa atualizada com sucesso!");
        } catch (cpfInvalidoException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar pessoa: " + e.getMessage());
        }
    }

    @PostMapping("/saveNomeCargo")
    public ResponseEntity<String> salvarPessoaPorNomeECargo(@RequestBody PessoaRequestNomeECargoDTO pessoaRequestNomeCargoDTO) {
        try {
            pessoaService.salvarPessoa(new Pessoa(pessoaRequestNomeCargoDTO));
            return ResponseEntity.ok("Pessoa salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar pessoa: " + e.getMessage());
        }
    }
}