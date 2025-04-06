package com.SpringLogin.SpringLoginPage.controller;

import com.SpringLogin.SpringLoginPage.model.WeedPredtictor;
import com.SpringLogin.SpringLoginPage.service.WeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class WeedController {
    @Autowired
    WeedService weedService;
//    WeedPredtictor weedPredtictor;

    @PostMapping("/weedPred")
    public ResponseEntity<String> weedPredtion(@RequestParam("file") MultipartFile weed){
        return ResponseEntity.ok(weedService.predictWeed(weed));
    }
}
