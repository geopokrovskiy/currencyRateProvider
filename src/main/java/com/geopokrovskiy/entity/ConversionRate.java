package com.geopokrovskiy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conversion_rates")
public class ConversionRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "source_code", referencedColumnName = "code", nullable = false)
    private Currency source;

    @ManyToOne
    @JoinColumn(name = "destination_code", referencedColumnName = "code", nullable = false)
    private Currency destination;

    @Column(nullable = false)
    private LocalDateTime rateBeginTime;

    @Column(nullable = false)
    private LocalDateTime rateEndTime;

    @Column(nullable = false)
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(name = "provider_code", nullable = false)
    private RateProvider provider;

    @Column(nullable = false)
    private BigDecimal multiplier;

    @Column(nullable = false)
    private BigDecimal systemRate;
}
