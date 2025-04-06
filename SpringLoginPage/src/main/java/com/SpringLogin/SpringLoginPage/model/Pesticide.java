package com.SpringLogin.SpringLoginPage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pesticides")  // Match with the table name in SQL
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pesticide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String pestName;   // Changed to String

    @Column(nullable = false)
    private String pesticideName; // Changed to String
}
