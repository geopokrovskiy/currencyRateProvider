package com.geopokrovskiy.currencyRateProvider.utils;

import com.geopokrovskiy.entity.ConversionRate;
import com.geopokrovskiy.entity.Currency;
import com.geopokrovskiy.entity.RateProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestUtils {

    public static final String GOOD_PROVIDER = "PR1";
    public static final String PROVIDER1_NAME = "Test provider 1";
    public static final String CURRENCY_SOURCE1 = "CS1";
    public static final String CURRENCY_DESTINATION1 = "CD1";

    public static RateProvider getCorrectRateProvider() {
        RateProvider provider = new RateProvider();
        provider.setProviderCode(GOOD_PROVIDER);
        provider.setProviderName(PROVIDER1_NAME);
        provider.setActive(true);
        provider.setDescription("Description");
        provider.setPriority(1);
        provider.setCreatedAt(LocalDateTime.of(2025, 2, 25, 13, 16));
        provider.setModifiedAt(LocalDateTime.of(2025, 2, 25, 13, 16));
        provider.setDefaultMultiplier(BigDecimal.ONE);
        return provider;
    }

    public static Currency getCorrectCurrencySource() {
        Currency currency = new Currency();
        currency.setCode(CURRENCY_SOURCE1);
        currency.setActive(true);
        currency.setDescription("Description");
        currency.setSymbol("S1");
        currency.setCreatedAt(LocalDateTime.of(2025, 2, 25, 13, 16));
        currency.setModifiedAt(LocalDateTime.of(2025, 2, 25, 13, 16));
        return currency;
    }

    public static Currency getCorrectCurrencyDestination() {
        Currency currency = new Currency();
        currency.setCode(CURRENCY_DESTINATION1);
        currency.setActive(true);
        currency.setDescription("Description");
        currency.setSymbol("D1");
        currency.setCreatedAt(LocalDateTime.of(2025, 2, 25, 13, 16));
        currency.setModifiedAt(LocalDateTime.of(2025, 2, 25, 13, 16));
        return currency;
    }

    public static ConversionRate getCorrectConversionRateSourceToDest() {
        return new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(getCorrectRateProvider())
                .source(getCorrectCurrencySource())
                .destination(getCorrectCurrencyDestination())
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal("42.0"))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build();
    }

    public static ConversionRate getCorrectConversionRateSourceToDestUpdated() {
        return new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(getCorrectRateProvider())
                .source(getCorrectCurrencySource())
                .destination(getCorrectCurrencyDestination())
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal("43.0"))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build();
    }

    public static ConversionRate getCorrectConversionRateDestToSource() {
        return new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(getCorrectRateProvider())
                .source(getCorrectCurrencySource())
                .destination(getCorrectCurrencyDestination())
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal("39.0"))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build();
    }

    public static ConversionRate getCorrectConversionRateDestToSourceUpdated() {
        return new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(getCorrectRateProvider())
                .source(getCorrectCurrencySource())
                .destination(getCorrectCurrencyDestination())
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal("39.0"))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build();
    }
}
