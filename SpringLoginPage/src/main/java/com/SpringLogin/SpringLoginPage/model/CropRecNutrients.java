package com.SpringLogin.SpringLoginPage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="CropRecNutrients")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class CropRecNutrients {
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
    private float ph;

    @Column(nullable = false)
    private float ec;

    @Column(nullable = false)
    private float s;

    @Column(nullable = false)
    private float cu;

    @Column(nullable = false)
    private float fe;

    @Column(nullable = false)
    private float mn;

    @Column(nullable = false)
    private float zn;

    @Column(nullable = false)
    private float b;

}
