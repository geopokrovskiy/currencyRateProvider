package com.geopokrovskiy.rest;

import com.geopokrovskiy.dto.ConversionRatesRefreshPost200Response;
import com.geopokrovskiy.dto.ConversionRatesRefreshPostRequest;
import com.geopokrovskiy.service.ConversionRateService;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/api/v1/conversion-rates")
public class ConversionRateController {
    private final ConversionRateService conversionRateService;

    @PostMapping("/refresh")
    public ResponseEntity<ConversionRatesRefreshPost200Response> refreshConversionRates(@RequestBody ConversionRatesRefreshPostRequest request) {
        String provider = request.getProviderCode();
        if (conversionRateService.updateConversionRates(provider)) {
            return new ResponseEntity<>(new ConversionRatesRefreshPost200Response()
                    .message("Conversion rates have been successfully updated"),
                    HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ConversionRatesRefreshPost200Response()
                    .message("Server error"),
                    HttpStatusCode.valueOf(500));
        }
    }
}
