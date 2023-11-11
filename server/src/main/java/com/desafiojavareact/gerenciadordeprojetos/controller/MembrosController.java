package com.desafiojavareact.gerenciadordeprojetos.controller;

import com.desafiojavareact.gerenciadordeprojetos.dto.MembrosRequestDTO;
import com.desafiojavareact.gerenciadordeprojetos.dto.PessoaRequestDTO;
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

@RestController
@RequestMapping("/api/membros")
public class MembrosController {

    private final MembrosService membrosService;
    private final PessoaService pessoaService;
    private final ProjetoService projetoService;

    @Autowired
    public MembrosController(MembrosService membrosService, PessoaService pessoaService, ProjetoService projetoService) {
        this.membrosService = membrosService;
        this.pessoaService = pessoaService;
        this.projetoService = projetoService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> salvarPessoa(@RequestBody MembrosRequestDTO membrosRequestDTO) {
        try {
            Pessoa pessoa = pessoaService.buscarPorId(membrosRequestDTO.idPessoa());
            Projeto projeto = projetoService.buscarPorId(membrosRequestDTO.idProjeto());

            membrosService.salvarMembro(new Membros(pessoa, projeto));
            return ResponseEntity.ok("Membro adicionado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar membro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idPessoa}/{idProjeto}")
    public ResponseEntity<String> deleteMembro(@PathVariable Long idPessoa, @PathVariable Long idProjeto) {
        try {
            Membros membro = membrosService.findByPessoaIdAndProjetoId(idPessoa, idProjeto);

            if (membro != null) {
                membrosService.excluirMembro(membro);
                return ResponseEntity.ok("Membro removido do projeto com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Membro não encontrado para exclusão.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir membro: " + e.getMessage());
        }
    }

}
