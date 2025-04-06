package com.SpringLogin.SpringLoginPage.service;

import com.SpringLogin.SpringLoginPage.model.Pesticide;
import com.SpringLogin.SpringLoginPage.repository.PesticideRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PesticideService {

    @Autowired
    private PesticideRec pesticideRecRepo;
    public String findPesticide(String pest) {
        Optional<Pesticide> pesticide= pesticideRecRepo.findByPestName(pest);
        return pesticide.map(Pesticide::getPesticideName)
                .orElse("No fertilizer found for this pest.");
    }
}
