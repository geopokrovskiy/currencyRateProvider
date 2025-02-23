package com.geopokrovskiy.rest;

import com.geopokrovskiy.dto.ConversionRate;
import com.geopokrovskiy.dto.ConversionRatesRefreshPost200Response;
import com.geopokrovskiy.dto.ConversionRatesRefreshPostRequest;
import com.geopokrovskiy.mapper.ConversionRateMapper;
import com.geopokrovskiy.service.ConversionRateService;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/v1/conversion-rates")
public class ConversionRateController {
    private final ConversionRateService conversionRateService;
    private final ConversionRateMapper conversionRateMapper;

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

    @GetMapping("/{providerCode}/{sourceCode}/{destinationCode}")
    public ResponseEntity<ConversionRate> getConversionRate(@PathVariable String providerCode,
                                                            @PathVariable String sourceCode,
                                                            @PathVariable String destinationCode) {
        com.geopokrovskiy.entity.ConversionRate conversionRate = conversionRateService.getConversionRate(providerCode, sourceCode, destinationCode);
        if (conversionRate != null) {
            ConversionRate response = conversionRateMapper.map(conversionRate);
            response.setProviderCode(providerCode);
            response.setSourceCode(sourceCode);
            response.setDestinationCode(destinationCode);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }

    }
}
