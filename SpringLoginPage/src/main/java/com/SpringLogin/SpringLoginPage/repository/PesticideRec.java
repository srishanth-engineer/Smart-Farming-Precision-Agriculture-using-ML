package com.SpringLogin.SpringLoginPage.repository;

import com.SpringLogin.SpringLoginPage.model.Pesticide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PesticideRec extends JpaRepository<Pesticide, Integer> {

    Optional<Pesticide> findByPestName(String pestName);
}
