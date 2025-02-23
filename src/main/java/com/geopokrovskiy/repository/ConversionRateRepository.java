package com.geopokrovskiy.repository;

import com.geopokrovskiy.entity.ConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversionRateRepository extends JpaRepository<ConversionRate, Long> {

    @Override
    @Query("SELECT cr FROM ConversionRate cr ORDER by cr.modifiedAt DESC")
    List<ConversionRate> findAll();

    @Query("SELECT cr FROM ConversionRate cr WHERE cr.provider.providerCode =?1 and cr.source.code = ?2 and cr.destination.code = ?3 " +
            "ORDER BY cr.modifiedAt DESC ")
    List<ConversionRate> findConversionRateByCurrencySourceAndCurrencyDestinationAndProvider(
            String providerCode, String currencySourceCode, String currencyDestinationCode);
}
