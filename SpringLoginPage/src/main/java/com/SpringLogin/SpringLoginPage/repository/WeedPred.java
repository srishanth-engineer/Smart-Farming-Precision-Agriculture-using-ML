package com.SpringLogin.SpringLoginPage.repository;

import com.SpringLogin.SpringLoginPage.model.WeedPredtictor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeedPred extends JpaRepository<WeedPredtictor, String> {
}
