package com.example.Treinamento_Prova.Service;

import com.example.Treinamento_Prova.Model.Solicitacao;
import com.example.Treinamento_Prova.Model.StatusSolicitacao;
import com.example.Treinamento_Prova.Repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public List<Solicitacao> getAllSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public Optional<Solicitacao> getSolicitacaoById(Long id) {
        return solicitacaoRepository.findById(id);
    }

    public Solicitacao createSolicitacao(Solicitacao solicitacao) {
        solicitacao.setStatus(StatusSolicitacao.ABERTO);
        solicitacao.setDataAbertura(LocalDateTime.now());
        return solicitacaoRepository.save(solicitacao);
    }

    // Substituindo o método updateSolicitacaoStatus pelo moverSolicitacao
    public Solicitacao moverSolicitacao(Long id) {
        // Recupera a solicitação pelo ID
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada com id: " + id));

        // Lógica para alternar entre os status da solicitação
        switch (solicitacao.getStatus()) {
            case ABERTO:
                solicitacao.setStatus(StatusSolicitacao.EM_ANDAMENTO);
                break;
            case EM_ANDAMENTO:
                solicitacao.setStatus(StatusSolicitacao.CONCLUIDO);
                break;
            case CONCLUIDO:
                throw new RuntimeException("Solicitação já está concluída");
        }

        // Salva e retorna a solicitação com o novo status
        return solicitacaoRepository.save(solicitacao);
    }

    public Solicitacao updateSolicitacao(Long id, Solicitacao solicitacaoDetalhes) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            solicitacao.setDescricao(solicitacaoDetalhes.getDescricao());
            solicitacao.setPrioridade(solicitacaoDetalhes.getPrioridade());
            solicitacao.setIdUsuario(solicitacaoDetalhes.getIdUsuario());
            return solicitacaoRepository.save(solicitacao);
        }).orElseThrow(() -> new RuntimeException("Solicitação não encontrada com id: " + id));
    }

    public void deleteSolicitacao(Long id) {
        if (!solicitacaoRepository.existsById(id)) {
            throw new RuntimeException("Solicitação não encontrada com id: " + id);
        }
        solicitacaoRepository.deleteById(id);
    }
}
