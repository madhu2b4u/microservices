package org.currencyexchangeservice.currencyexchange.repository;

import org.currencyexchangeservice.currencyexchange.model.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    CurrencyExchange findByFromAndTo(String from, String to);
}
