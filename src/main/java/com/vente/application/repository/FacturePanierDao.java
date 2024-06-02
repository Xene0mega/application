package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.FacturePanier;

public interface FacturePanierDao extends JpaRepository<FacturePanier, Long> {

}
