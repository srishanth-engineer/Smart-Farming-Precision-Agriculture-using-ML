package com.SpringLogin.SpringLoginPage.controller;

import com.SpringLogin.SpringLoginPage.service.MaizeService;
import com.SpringLogin.SpringLoginPage.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/Disease")
public class MaizeController {
    @Autowired
    MaizeService maizeService;
    @PostMapping("/maizePrediction")
    public ResponseEntity<String> pestPrediction(@RequestParam("file") MultipartFile file){
        System.out.println("Entered maize controller");
        return  ResponseEntity.ok(maizeService.predictMaizeDisease(file));
    }
}
