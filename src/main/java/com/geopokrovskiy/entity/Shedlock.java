package com.geopokrovskiy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shedlock")
public class Shedlock {
    @Id
    private String name;

    @Column
    private LocalDateTime lockedUntil;

    @Column
    private LocalDateTime lockedAt;

    @Column
    private String lockedBy;
}
