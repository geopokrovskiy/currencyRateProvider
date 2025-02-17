package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.ConversionRate;
import com.geopokrovskiy.entity.RateProvider;
import com.geopokrovskiy.repository.ConversionRateRepository;
import com.geopokrovskiy.repository.RateProviderRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@Slf4j
public class RateProviderService {
    private final RateProviderRepository rateProviderRepository;
    private final String GOOD_PROVIDER = "PR1";

    public List<RateProvider> getProviderList() {
        return rateProviderRepository.findAll();
    }


    public RateProvider getRateProvider(String providerCode) {
        if (providerCode.isBlank()) return null;
        return rateProviderRepository.findById(providerCode).orElse(null);
    }

}
