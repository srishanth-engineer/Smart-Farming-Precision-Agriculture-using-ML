package com.SpringLogin.SpringLoginPage.controller;


import com.SpringLogin.SpringLoginPage.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PestController {
    @Autowired
    PestService pestService;
    @PostMapping("/pestPred")
    public ResponseEntity<String> pestPrediction(@RequestParam("file")MultipartFile file){
        return  ResponseEntity.ok(pestService.predictPest(file));
    }
}
