package com.geopokrovskiy.repository;

import com.geopokrovskiy.entity.ConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversionRateRepository extends JpaRepository<ConversionRate, Long> {

    @Override
    @Query("SELECT cr FROM ConversionRate cr ORDER by cr.modifiedAt")
    List<ConversionRate> findAll();
}
