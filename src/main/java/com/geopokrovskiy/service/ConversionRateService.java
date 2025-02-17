package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.ConversionRate;
import com.geopokrovskiy.entity.RateProvider;
import com.geopokrovskiy.repository.ConversionRateRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Data
@Slf4j
public class ConversionRateService {

    private final RateProviderService rateProviderService;
    private final CurrencyService currencyService;
    private final ConversionRateRepository conversionRateRepository;

    private final String GOOD_PROVIDER = "PR1";
    private final String RUB = "RUB";
    private final String USD = "USD";
    private final String EUR = "EUR";

    public ConversionRate getConversionRate(String providerCode, String currencyCodeSource, String currencyCodeDestination) {
        if (providerCode.isBlank() || currencyCodeSource.isBlank() || currencyCodeDestination.isBlank()) return null;
        if ((currencyService.getCurrencyByCode(currencyCodeSource) == null) ||
                (currencyService.getCurrencyByCode(currencyCodeDestination) == null)) return null;
        if (rateProviderService.getRateProvider(providerCode) != null) {
            ConversionRate conversionRate = conversionRateRepository.findAll().stream().filter(
                    cr -> cr.getProvider().getProviderCode().equals(providerCode)
                            && cr.getSource().getCode().equals(currencyCodeSource)
                            && cr.getDestination().getCode().equals(currencyCodeDestination)
            ).findFirst().orElse(null);
            if (conversionRate != null) return conversionRate;
            if (GOOD_PROVIDER.equals(providerCode)) {
                if (RUB.equals(currencyCodeSource) && USD.equals(currencyCodeDestination)) {
                    return addNewRubToUsdGood(providerCode);
                } else if (USD.equals(currencyCodeSource) && RUB.equals(currencyCodeDestination)) {
                    return addNewUsdToRubGood(providerCode);
                } else if (RUB.equals(currencyCodeSource) && EUR.equals(currencyCodeDestination)) {
                    return addNewRubToEurGood(providerCode);
                } else if (EUR.equals(currencyCodeSource) && RUB.equals(currencyCodeDestination)) {
                    return addNewEurToRubGood(providerCode);
                } else if (USD.equals(currencyCodeSource) && EUR.equals(currencyCodeDestination)) {
                    return addNewUsdToEurGood(providerCode);
                } else if (EUR.equals(currencyCodeSource) && USD.equals(currencyCodeDestination)) {
                    return addNewEurToUsdGood(providerCode);
                } else {
                    throw new IllegalArgumentException("Unknown currencies");
                }
            } else {
                // Write some mock code for other providers
                log.error("The provider {} is not yet supported", providerCode);
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean updateConversionRates(String providerCode) {
        if (providerCode.isBlank()) return false;
        RateProvider provider = rateProviderService.getRateProvider(providerCode);
        if (provider == null) {
            log.error("The provider {} does not exist", providerCode);
            return false;
        } else {
            if (GOOD_PROVIDER.equals(providerCode)) {
                // Mock code for the good provider
                log.info("EUR to RUB conversion rate has been updated");
                addNewEurToRubGood(providerCode);

                log.info("RUB to EUR conversion rate has been updated");
                addNewRubToEurGood(providerCode);

                log.info("USD to EUR conversion rate has been updated");
                addNewUsdToEurGood(providerCode);

                log.info("EUR to USD conversion rate has been updated");
                addNewEurToUsdGood(providerCode);

                log.info("RUB to USD conversion rate has been updated");
                addNewRubToUsdGood(providerCode);

                log.info("USD to RUB conversion rate has been updated");
                addNewUsdToRubGood(providerCode);

                return true;
            } else {
                // Write some mock code for other providers
                log.error("The provider {} is not yet supported", providerCode);
                return false;
            }
        }
    }

    private ConversionRate addNewRubToUsdGood(String providerCode) {
        double coefficient = 1 / (30 + (new Random().nextDouble() - 0.5) * 2);
        return conversionRateRepository.save(new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(rateProviderService.getRateProvider(providerCode))
                .source(currencyService.getCurrencyByCode(RUB))
                .destination(currencyService.getCurrencyByCode(USD))
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal(coefficient))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())

                // TODO add logic of cron based rate update
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build());
    }

    private ConversionRate addNewUsdToRubGood(String providerCode) {
        double coefficient = 29 + (new Random().nextDouble() - 0.5) * 2;
        return conversionRateRepository.save(new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(rateProviderService.getRateProvider(providerCode))
                .source(currencyService.getCurrencyByCode(USD))
                .destination(currencyService.getCurrencyByCode(RUB))
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal(coefficient))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())

                // TODO add logic of cron based rate update
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build());
    }

    private ConversionRate addNewRubToEurGood(String providerCode) {
        double coefficient = 1 / (40 + (new Random().nextDouble() - 0.5) * 2);
        return conversionRateRepository.save(new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(rateProviderService.getRateProvider(providerCode))
                .source(currencyService.getCurrencyByCode(RUB))
                .destination(currencyService.getCurrencyByCode(EUR))
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal(coefficient))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())

                // TODO add logic of cron based rate update
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build());
    }

    private ConversionRate addNewEurToRubGood(String providerCode) {
        double coefficient = 39 + (new Random().nextDouble() - 0.5) * 2;
        return conversionRateRepository.save(new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(rateProviderService.getRateProvider(providerCode))
                .source(currencyService.getCurrencyByCode(EUR))
                .destination(currencyService.getCurrencyByCode(RUB))
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal(coefficient))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())

                // TODO add logic of cron based rate update
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build());
    }

    private ConversionRate addNewEurToUsdGood(String providerCode) {
        double coefficient = 1.1 + (new Random().nextDouble() - 0.5) * 2;
        return conversionRateRepository.save(new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(rateProviderService.getRateProvider(providerCode))
                .source(currencyService.getCurrencyByCode(EUR))
                .destination(currencyService.getCurrencyByCode(USD))
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal(coefficient))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())

                // TODO add logic of cron based rate update
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build());
    }

    private ConversionRate addNewUsdToEurGood(String providerCode) {
        double coefficient = 0.95 + (new Random().nextDouble() - 0.5) * 2;
        return conversionRateRepository.save(new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(rateProviderService.getRateProvider(providerCode))
                .source(currencyService.getCurrencyByCode(USD))
                .destination(currencyService.getCurrencyByCode(EUR))
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal(coefficient))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())

                // TODO add logic of cron based rate update
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build());
    }
}
