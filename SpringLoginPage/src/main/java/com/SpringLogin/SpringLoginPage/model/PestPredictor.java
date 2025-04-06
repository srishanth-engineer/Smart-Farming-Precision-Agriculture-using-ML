package com.SpringLogin.SpringLoginPage.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PestPredictor {
    @Id
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}
