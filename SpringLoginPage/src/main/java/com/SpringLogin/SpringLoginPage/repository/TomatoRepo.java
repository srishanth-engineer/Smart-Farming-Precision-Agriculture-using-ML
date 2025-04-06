package com.SpringLogin.SpringLoginPage.repository;

import com.SpringLogin.SpringLoginPage.model.Tomato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TomatoRepo extends JpaRepository<Tomato, String> {
}
