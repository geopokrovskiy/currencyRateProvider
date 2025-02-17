package com.geopokrovskiy.rest;

import com.geopokrovskiy.dto.RateProvider;
import com.geopokrovskiy.mapper.RateProviderMapper;
import com.geopokrovskiy.service.RateProviderService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/v1/rate-providers")
public class ProviderController {
    private final RateProviderService rateProviderService;
    private final RateProviderMapper rateProviderMapper;

    @GetMapping()
    public List<RateProvider> getProviders() {
        return rateProviderService.getProviderList().stream().map(rateProviderMapper::map).toList();
    }
}
