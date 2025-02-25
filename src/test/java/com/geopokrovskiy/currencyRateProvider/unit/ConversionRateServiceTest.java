package com.geopokrovskiy.currencyRateProvider.unit;

import com.geopokrovskiy.currencyRateProvider.utils.TestUtils;
import com.geopokrovskiy.entity.ConversionRate;
import com.geopokrovskiy.entity.Currency;
import com.geopokrovskiy.entity.RateProvider;
import com.geopokrovskiy.repository.ConversionRateRepository;
import com.geopokrovskiy.repository.CurrencyRepository;
import com.geopokrovskiy.repository.RateProviderRepository;
import com.geopokrovskiy.service.ConversionRateService;
import com.geopokrovskiy.service.CurrencyService;
import com.geopokrovskiy.service.RateProviderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConversionRateServiceTest {

    private static final ConversionRateRepository conversionRateRepository = Mockito.mock(ConversionRateRepository.class);
    private static final CurrencyRepository currencyRepository = Mockito.mock(CurrencyRepository.class);
    private static final RateProviderRepository providerRepository = Mockito.mock(RateProviderRepository.class);

    private static ConversionRateService conversionRateService;
    private static CurrencyService currencyService;
    private static RateProviderService rateProviderService;

    private final String GOOD_PROVIDER = "PR1";
    private final String CURRENCY_SOURCE1 = "CS1";
    private final String CURRENCY_DESTINATION1 = "CD1";


    @BeforeAll
    public static void setUp() throws Exception {
        currencyService = new CurrencyService(currencyRepository);
        rateProviderService = new RateProviderService(providerRepository);
        conversionRateService = new ConversionRateService(rateProviderService, currencyService, conversionRateRepository);
    }

    @Test
    public void testGetConversionRateOk() {

        ConversionRate expectedConversionRate = TestUtils.getCorrectConversionRateSourceToDest();
        RateProvider expectedRateProvider = TestUtils.getCorrectRateProvider();
        Currency expectedSourceCurrency = TestUtils.getCorrectCurrencySource();
        Currency expectedDestinationCurrency = TestUtils.getCorrectCurrencyDestination();

        Mockito.when(conversionRateRepository.findConversionRateByCurrencySourceAndCurrencyDestinationAndProvider(GOOD_PROVIDER, CURRENCY_SOURCE1, CURRENCY_DESTINATION1))
                .thenReturn(List.of(expectedConversionRate));
        Mockito.when(providerRepository.findById(GOOD_PROVIDER)).thenReturn(Optional.of(expectedRateProvider));
        Mockito.when(currencyRepository.findByCode(CURRENCY_SOURCE1)).thenReturn(Optional.of(expectedSourceCurrency));
        Mockito.when(currencyRepository.findByCode(CURRENCY_DESTINATION1)).thenReturn(Optional.of(expectedDestinationCurrency));


        ConversionRate actualConversionRate = conversionRateService.getConversionRate(GOOD_PROVIDER, CURRENCY_SOURCE1, CURRENCY_DESTINATION1);
        assertNotNull(actualConversionRate);
        assertEquals(expectedConversionRate, actualConversionRate);

    }

    @Test
    public void testUpdateConversionRateOk() {
        RateProvider expectedRateProvider = TestUtils.getCorrectRateProvider();
        Currency expectedSourceCurrency = TestUtils.getCorrectCurrencySource();
        Currency expectedDestinationCurrency = TestUtils.getCorrectCurrencyDestination();
        ConversionRate expectedConversionRate = TestUtils.getCorrectConversionRateSourceToDest();

        Mockito.when(currencyRepository.findAll()).thenReturn(List.of(expectedSourceCurrency, expectedDestinationCurrency));
        Mockito.when(currencyRepository.findByCode(CURRENCY_SOURCE1)).thenReturn(Optional.of(expectedSourceCurrency));
        Mockito.when(currencyRepository.findByCode(CURRENCY_DESTINATION1)).thenReturn(Optional.of(expectedDestinationCurrency));
        Mockito.when(providerRepository.findById(GOOD_PROVIDER)).thenReturn(Optional.of(expectedRateProvider));
        Mockito.when(conversionRateRepository.save(Mockito.any(ConversionRate.class))).thenReturn(expectedConversionRate);

        boolean result = conversionRateService.updateConversionRate();


        // Conversion rates CS1 - CD1 and CD1 - CS1 must have been updated
        assertTrue(result);
        Mockito.verify(conversionRateRepository, Mockito.times(2)).save(Mockito.any(ConversionRate.class));

    }


}
