package com.desafiojavareact.gerenciadordeprojetos.controller;

import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.model.Pessoa;
import com.desafiojavareact.gerenciadordeprojetos.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> salvarPessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        try {
            pessoaService.salvarPessoa(new Pessoa(pessoaRequestDTO));
            return ResponseEntity.ok("Pessoa salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar pessoa: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Pessoa>> getAllPessoas() {
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
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

    @DeleteMapping("/{pessoaId}")
    public ResponseEntity<String> deletePessoa(@PathVariable Long pessoaId) {
        try {
            pessoaService.excluirPessoa(pessoaId);
            return ResponseEntity.ok("Pessoa excluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir pessoa: " + e.getMessage());
        }
    }

    @PutMapping("/{pessoaId}")
    public ResponseEntity<String> updatePessoa(@PathVariable Long pessoaId, @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        try {
            Pessoa pessoa = pessoaService.buscarPorId(pessoaId);
            pessoaService.atualizarPessoa(pessoa, pessoaRequestDTO);
            return ResponseEntity.ok("Pessoa atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar pessoa: " + e.getMessage());
        }
    }
}