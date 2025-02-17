package com.geopokrovskiy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rate_correction_coefficients")
public class RateCorrectionCoefficient {
    @Id
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean archived = false;
    private String sourceCode;
    private String destinationCode;
    private BigDecimal multiplier;
    private String providerCode;
    private String creator;
    private String modifier;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String profileType;
}

