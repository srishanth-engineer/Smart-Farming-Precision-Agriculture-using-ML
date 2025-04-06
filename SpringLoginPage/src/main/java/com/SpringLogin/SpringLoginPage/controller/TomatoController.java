package com.SpringLogin.SpringLoginPage.controller;

import com.SpringLogin.SpringLoginPage.service.MaizeService;
import com.SpringLogin.SpringLoginPage.service.TomatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/Disease")
public class TomatoController {
    @Autowired
    TomatoService tomatoService;
    @PostMapping("/tomatoPrediction")
    public ResponseEntity<String> pestPrediction(@RequestParam("file") MultipartFile file){
        System.out.println("Entered tomato controller");
        return  ResponseEntity.ok(tomatoService.predictTomatoDisease(file));
    }
}
