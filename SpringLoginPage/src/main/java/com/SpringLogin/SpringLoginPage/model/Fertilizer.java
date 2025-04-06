package com.SpringLogin.SpringLoginPage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Fertilizers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private float humidity;

    @Column(nullable = false)
    private float moisture;

    @Column(nullable = false)
    private String soil_type;

    @Column(nullable = false)
    private String crop_type;

    @Column(nullable = false)
    private float nitrogen;

    @Column(nullable = false)
    private float potassium;

    @Column(nullable = false)
    private float phosphorous;
}
