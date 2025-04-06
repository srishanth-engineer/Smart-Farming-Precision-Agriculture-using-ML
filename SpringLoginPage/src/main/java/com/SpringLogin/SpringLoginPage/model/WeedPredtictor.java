package com.SpringLogin.SpringLoginPage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeedPredtictor {
    @Id
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageDate;


}
