package com.example.Treinamento_Prova.Controller;


import com.example.Treinamento_Prova.Model.Solicitacao;
import com.example.Treinamento_Prova.Model.StatusSolicitacao;
import com.example.Treinamento_Prova.Service.SolicitacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    public List<Solicitacao> getAllSolicitacoes() {
        return solicitacaoService.getAllSolicitacoes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> getSolicitacaoById(@PathVariable Long id) {
        return solicitacaoService.getSolicitacaoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Solicitacao createSolicitacao(@RequestBody Solicitacao solicitacao) {
        return solicitacaoService.createSolicitacao(solicitacao);
    }

    @PutMapping("/{id}/move")
    public ResponseEntity<Solicitacao> updateSolicitacaoStatus(@PathVariable Long id) {
        System.out.println("Status recebido");
        try {
            Solicitacao solicitacaoAtualizada = solicitacaoService.moverSolicitacao(id);
            return ResponseEntity.ok(solicitacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitacao> updateSolicitacao(@PathVariable Long id, @RequestBody Solicitacao solicitacaoDetalhes) {
        try {
            Solicitacao solicitacaoAtualizada = solicitacaoService.updateSolicitacao(id, solicitacaoDetalhes);
            return ResponseEntity.ok(solicitacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitacao(@PathVariable Long id) {
        solicitacaoService.deleteSolicitacao(id);
        return ResponseEntity.noContent().build();
    }
}

