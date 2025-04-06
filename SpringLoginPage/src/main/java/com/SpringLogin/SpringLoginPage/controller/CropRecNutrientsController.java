package com.SpringLogin.SpringLoginPage.controller;


import com.SpringLogin.SpringLoginPage.model.CropRecNutrients;
import com.SpringLogin.SpringLoginPage.service.CropRecNutrientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CropRecNutrientsController {

    @Autowired
    private CropRecNutrientsService cropRecNutrientsService;

    @PostMapping("/cropRecNutrients")
    public ResponseEntity<String> CropRecSoilNutrients(@RequestBody CropRecNutrients cropRecNutrients){

        return ResponseEntity.ok(cropRecNutrientsService.findBestCrop(cropRecNutrients));
    }

}
