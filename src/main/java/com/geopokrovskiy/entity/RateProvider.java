package com.geopokrovskiy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "rate_providers")
public class RateProvider {
    @Id
    private String providerCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @Column(nullable = false, unique = true)
    private String providerName;

    @Column
    private String description;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private BigDecimal defaultMultiplier = BigDecimal.ONE;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provider")
    private List<ConversionRate> conversionRates;
}

