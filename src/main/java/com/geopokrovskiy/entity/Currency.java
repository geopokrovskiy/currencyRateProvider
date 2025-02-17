package com.geopokrovskiy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private int isoCode;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private int scale;

    @Column
    private String symbol;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "source")
    private List<ConversionRate> sourceRateList;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destination")
    private List<ConversionRate> destinationRateList;
}
