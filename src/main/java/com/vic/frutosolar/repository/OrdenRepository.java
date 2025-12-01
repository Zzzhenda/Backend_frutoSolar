package com.vic.frutosolar.repository;
import com.vic.frutosolar.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {}