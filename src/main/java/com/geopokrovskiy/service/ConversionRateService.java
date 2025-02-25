package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.ConversionRate;
import com.geopokrovskiy.entity.Currency;
import com.geopokrovskiy.entity.RateProvider;
import com.geopokrovskiy.repository.ConversionRateRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
            List<ConversionRate> conversionRates = conversionRateRepository.findConversionRateByCurrencySourceAndCurrencyDestinationAndProvider
                    (providerCode, currencyCodeSource, currencyCodeDestination);
            if (!conversionRates.isEmpty()) return conversionRates.getFirst();
            if (GOOD_PROVIDER.equals(providerCode)) {
                return addNewConversionRateForProvider1(providerCode, currencyCodeSource, currencyCodeDestination);
            } else {
                // Write some mock code for other providers
                log.error("The provider {} is not yet supported", providerCode);
                return null;
            }
        } else {
            return null;
        }
    }

    @Scheduled(cron = "${interval-in-cron}")
    @SchedulerLock(name = "updateConversionRates", lockAtMostFor = "2m", lockAtLeastFor = "1m")
    public Boolean updateConversionRate() {
        return updateConversionRates(GOOD_PROVIDER);
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
                List<Currency> currencyList = currencyService.getAllCurrencies();
                for (int i = 0; i < currencyList.size(); i++) {
                    for (int j = i + 1; j < currencyList.size(); j++) {
                        Currency currency1 = currencyList.get(i);
                        Currency currency2 = currencyList.get(j);

                        addNewConversionRateForProvider1(providerCode, currency1.getCode(), currency2.getCode());
                        log.info("Conversion rate for {} and {} has been updated", currency1.getCode(), currency2.getCode());

                        addNewConversionRateForProvider1(providerCode, currency2.getCode(), currency1.getCode());
                        log.info("Conversion rate for {} and {} has been updated", currency2.getCode(), currency1.getCode());
                    }
                }

                return true;
            } else {
                // Write some mock code for other providers
                log.error("The provider {} is not yet supported", providerCode);
                return false;
            }
        }
    }

    private ConversionRate addNewConversionRateForProvider1(String providerCode, String currencyCodeSource, String currencyCodeDestination) {
        double coefficient = (30 + (new Random().nextDouble() - 2) * 2);
        Currency currencySource = currencyService.getCurrencyByCode(currencyCodeSource);
        Currency currencyDestination = currencyService.getCurrencyByCode(currencyCodeDestination);
        if (currencyDestination == null || currencySource == null) {
            throw new IllegalArgumentException("The currency destination or source are null");
        }
        return conversionRateRepository.save(new ConversionRate().
                toBuilder().
                createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .provider(rateProviderService.getRateProvider(providerCode))
                .source(currencyService.getCurrencyByCode(currencyCodeSource))
                .destination(currencyService.getCurrencyByCode(currencyCodeDestination))
                .multiplier(BigDecimal.ONE)
                .rate(new BigDecimal(coefficient))
                .systemRate(BigDecimal.ONE)
                .rateBeginTime(LocalDateTime.now())
                .rateEndTime(LocalDateTime.now().plusMinutes(15))
                .build());
    }


}
