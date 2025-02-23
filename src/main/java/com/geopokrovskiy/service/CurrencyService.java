package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.Currency;
import com.geopokrovskiy.repository.CurrencyRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public Currency getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code).orElse(null);
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
