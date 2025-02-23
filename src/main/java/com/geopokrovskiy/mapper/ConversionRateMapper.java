package com.geopokrovskiy.mapper;

import com.geopokrovskiy.dto.ConversionRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversionRateMapper {

    ConversionRate map(com.geopokrovskiy.entity.ConversionRate conversionRate);
}
