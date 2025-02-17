package com.geopokrovskiy.mapper;

import com.geopokrovskiy.dto.RateProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RateProviderMapper {
    RateProvider map(com.geopokrovskiy.entity.RateProvider rateProvider);
}
