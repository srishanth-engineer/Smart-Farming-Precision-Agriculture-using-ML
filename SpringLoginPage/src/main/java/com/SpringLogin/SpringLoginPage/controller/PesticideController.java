package com.SpringLogin.SpringLoginPage.controller;


import com.SpringLogin.SpringLoginPage.model.Pesticide;
import com.SpringLogin.SpringLoginPage.service.PesticideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PesticideController {

    @Autowired
    private PesticideService pesticideService;
    @PostMapping("/PestRec")
    public ResponseEntity<String> findPesticide(@RequestBody Map<String, String> request){
        String pest = request.get("pest");
        return ResponseEntity.ok(pesticideService.findPesticide(pest));
    }
}
