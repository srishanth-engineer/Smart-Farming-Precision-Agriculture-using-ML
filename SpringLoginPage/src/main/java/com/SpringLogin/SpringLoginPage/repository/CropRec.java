package com.SpringLogin.SpringLoginPage.repository;

import com.SpringLogin.SpringLoginPage.model.CropRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRec extends JpaRepository<CropRecommendation, Integer> {

}
