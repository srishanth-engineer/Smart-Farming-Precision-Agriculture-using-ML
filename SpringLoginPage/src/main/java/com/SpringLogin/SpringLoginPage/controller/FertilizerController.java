package com.SpringLogin.SpringLoginPage.controller;

import com.SpringLogin.SpringLoginPage.model.Fertilizer;
import com.SpringLogin.SpringLoginPage.service.FertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FertilizerController {

    @Autowired
    private FertilizerService fertilizerService;
    @PostMapping("/fertilizerRec")
    public ResponseEntity<String> findFertilizer(@RequestBody Fertilizer fertilizer){
        return ResponseEntity.ok(fertilizerService.findBestFertilizer(fertilizer));
    }
}


