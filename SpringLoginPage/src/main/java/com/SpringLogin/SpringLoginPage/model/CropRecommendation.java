package com.SpringLogin.SpringLoginPage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="CropRec")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CropRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private float n;

    @Column(nullable = false)
    private float p;

    @Column(nullable = false)
    private float k;

    @Column(nullable = false)
    private float temp;

    @Column(nullable = false)
    private float humidity;

    @Column(nullable = false)
    private float ph;

    @Column(nullable = false)
    private float rainfall;

    @Column(nullable = false)
    private int soilType;



}
