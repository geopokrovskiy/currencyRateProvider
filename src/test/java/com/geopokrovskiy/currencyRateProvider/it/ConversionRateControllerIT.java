package com.geopokrovskiy.currencyRateProvider.it;

import com.geopokrovskiy.currencyRateProvider.configuration.TestDatabaseConfiguration;
import com.geopokrovskiy.dto.ConversionRatesRefreshPostRequest;
import com.geopokrovskiy.repository.ConversionRateRepository;
import com.geopokrovskiy.repository.ShedlockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestDatabaseConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ConversionRateControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Autowired
    private ShedlockRepository shedlockRepository;

    private final String GOOD_PROVIDER = "PR1";
    private final String CURRENCY_SOURCE1 = "CS1";
    private final String CURRENCY_DESTINATION1 = "CD1";

    @BeforeEach
    public void setUp() throws Exception {
        conversionRateRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void testGetConversionRateOk() throws Exception {

        int numberOfLines = conversionRateRepository.findAll().size();
        assertEquals(0, numberOfLines);

        mockMvc.perform(get("/api/v1/conversion-rates/" + GOOD_PROVIDER + "/" + CURRENCY_SOURCE1 + "/" + CURRENCY_DESTINATION1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(10))
                .andExpect(jsonPath("$.source_code").value(CURRENCY_SOURCE1))
                .andExpect(jsonPath("$.destination_code").value(CURRENCY_DESTINATION1));

        // number of entries should be increased by 1
        numberOfLines = conversionRateRepository.findAll().size();
        assertEquals(1, numberOfLines);

        mockMvc.perform(get("/api/v1/conversion-rates/" + GOOD_PROVIDER + "/" + CURRENCY_DESTINATION1 + "/" + CURRENCY_SOURCE1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(10))
                .andExpect(jsonPath("$.source_code").value(CURRENCY_DESTINATION1))
                .andExpect(jsonPath("$.destination_code").value(CURRENCY_SOURCE1));

        // number of entries should be increased by 1
        numberOfLines = conversionRateRepository.findAll().size();
        assertEquals(2, numberOfLines);

    }

    @Test
    public void testUpdateConversionRateOk() throws Exception {
        int numberOfLines = conversionRateRepository.findAll().size();
        assertEquals(0, numberOfLines);

        ConversionRatesRefreshPostRequest request = new ConversionRatesRefreshPostRequest();
        request.setProviderCode(GOOD_PROVIDER);

        String requestJson = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(post("/api/v1/conversion-rates/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Conversion rates have been successfully updated"));

        numberOfLines = conversionRateRepository.findAll().size();
        assertEquals(2, numberOfLines);

        // Once again
        mockMvc.perform(post("/api/v1/conversion-rates/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Conversion rates have been successfully updated"));
        numberOfLines = conversionRateRepository.findAll().size();
        assertEquals(4, numberOfLines);

        // No entries in shedlock table for the moment
        int shedlockLines = shedlockRepository.findAll().size();
        assertEquals(0, shedlockLines);

        // Must be automatically updated in 1 minute
        Thread.sleep(61000);
        numberOfLines = conversionRateRepository.findAll().size();
        assertEquals(6, numberOfLines);

        // One line in shedlock table should appear
        shedlockLines = shedlockRepository.findAll().size();
        assertEquals(1, shedlockLines);
    }
}
