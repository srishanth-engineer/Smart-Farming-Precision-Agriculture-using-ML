package com.SpringLogin.SpringLoginPage.controller;

import com.SpringLogin.SpringLoginPage.model.CropRecommendation;
import com.SpringLogin.SpringLoginPage.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping("/cropRec")
    public ResponseEntity<String> CropRecommendation(@RequestBody CropRecommendation croprec){
        System.out.println("Entered Crop Controller");
        return ResponseEntity.ok((cropService.PredictBestCrop(croprec)));
    }


}
