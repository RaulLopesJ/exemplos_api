package com.example.Treinamento_Prova.Repository;


import com.example.Treinamento_Prova.Model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    // Métodos adicionais, se necessário
}
