package com.SpringLogin.SpringLoginPage.repository;

import com.SpringLogin.SpringLoginPage.model.CropRecNutrients;
import com.SpringLogin.SpringLoginPage.model.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FertilizerRec extends JpaRepository<Fertilizer, Integer> {
}
