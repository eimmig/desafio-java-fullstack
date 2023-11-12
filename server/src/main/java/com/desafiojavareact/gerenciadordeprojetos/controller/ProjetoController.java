package com.desafiojavareact.gerenciadordeprojetos.controller;

import com.desafiojavareact.gerenciadordeprojetos.dto.*;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.ProjetoJaIniciadoException;
import com.desafiojavareact.gerenciadordeprojetos.exceptions.PessoaNaoEncontradaException;
import com.desafiojavareact.gerenciadordeprojetos.model.Membros;
import com.desafiojavareact.gerenciadordeprojetos.model.Projeto;
import com.desafiojavareact.gerenciadordeprojetos.service.MembrosService;
import com.desafiojavareact.gerenciadordeprojetos.service.PessoaService;
import com.desafiojavareact.gerenciadordeprojetos.service.ProjetoService;
import jakarta.transaction.Transactional;
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
    private final MembrosService membrosService;
    private final PessoaService pessoaService;

    @Autowired
    public ProjetoController(ProjetoService projetoService, MembrosService membrosService, PessoaService pessoaService) {
        this.projetoService = projetoService;
        this.membrosService = membrosService;
        this.pessoaService = pessoaService;
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

    @GetMapping("gerenteFuncionarios/{id}")
    public ResponseEntity<ProjetoResponseDTO> getProjetoGerenteFuncionarioById(@PathVariable Long id) {
        try {
            Projeto projeto = projetoService.buscarPorId(id);

            List<Membros> membros = membrosService.findByProjetoId(id);

            ProjetoResponseDTO projetoResponseDTO = projetoService.consultarProjetoComGerenteEFuncionarios(projeto, membros);
            return ResponseEntity.ok(projetoResponseDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<String> salvarProjeto(@RequestBody ProjetoRequestDTO projetoRequestDTO) {
        try {
            Projeto projeto = new Projeto(projetoRequestDTO, pessoaService);
            projetoService.salvarProjeto(projeto);
            membrosService.criarMembros(projetoRequestDTO, projeto);
            return ResponseEntity.ok("Projeto salvo com sucesso!");
        } catch (PessoaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao salvar projeto: " + e.getMessage());
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
            membrosService.criarMembros(projetoRequestDTO, projeto);
            return ResponseEntity.ok("Projeto atualizado com sucesso!");
        } catch (PessoaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao salvar projeto: " + e.getMessage());
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

            List<Membros> membros = membrosService.findByProjetoId(id);

            for (Membros membro : membros) {
                membrosService.excluirMembro(membro);
            }
            projetoService.excluirProjeto(id);
            return ResponseEntity.ok("Projeto excluído com sucesso!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (ProjetoJaIniciadoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro ao excluir projeto: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir projeto: " + e.getMessage());
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<ProjetoStats> getStats() {
        ProjetoStats projetoStats = projetoService.getStats();

        return ResponseEntity.ok(projetoStats);
    }
}