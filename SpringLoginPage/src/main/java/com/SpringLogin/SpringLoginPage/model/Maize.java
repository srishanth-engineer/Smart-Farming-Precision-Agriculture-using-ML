package com.SpringLogin.SpringLoginPage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Maize")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Maize {
    @Id
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}
