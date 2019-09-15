package com.awmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awmoney.api.model.Lancamento;
import com.awmoney.api.repository.lancamento.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
