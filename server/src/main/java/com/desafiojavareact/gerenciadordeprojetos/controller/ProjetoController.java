package com.desafiojavareact.gerenciadordeprojetos.controller;

import com.desafiojavareact.gerenciadordeprojetos.dto.ProjetoRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.enums.StatusProjeto;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.RegraDeNegocioException;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import com.desafiojavareact.gerenciadordeprojetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/projeto")
public class ProjetoController {

    private final ProjetoService projetoService;

    @Autowired
    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> getProjetoById(@PathVariable Long id) {
        try {
            Projeto projeto = projetoService.buscarPorId(id);
            return ResponseEntity.ok(projeto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> salvarProjeto(@RequestBody ProjetoRequestDTO projetoRequestDTO) {
        try {
            projetoService.salvarProjeto(new Projeto(projetoRequestDTO));
            return ResponseEntity.ok("Projeto salvo com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar projeto: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Projeto>> getAllProjetos() {
        List<Projeto> projetos = projetoService.listarProjetos();
        return ResponseEntity.ok(projetos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoRequestDTO projetoRequestDTO) {
        try {
            Projeto projeto = projetoService.buscarPorId(id);
            projetoService.atualizarProjeto(projeto, projetoRequestDTO);
            return ResponseEntity.ok("Projeto atualizado com sucesso!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar projeto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirProjeto(@PathVariable Long id) {
        try {
            //Valida o status do projeto antes de excluir
            projetoService.validarPossibilidadeExclusao(projetoService.buscarPorId(id));

            projetoService.excluirProjeto(id);
            return ResponseEntity.ok("Projeto excluído com sucesso!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir projeto: " + e.getMessage());
        }
    }
}