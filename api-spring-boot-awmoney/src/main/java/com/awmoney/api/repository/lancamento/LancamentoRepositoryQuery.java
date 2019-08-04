package com.awmoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.awmoney.api.model.Lancamento;
import com.awmoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> findByFilter(LancamentoFilter filter, Pageable pageable);
}
