package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.FactureProduit;

public interface FactureProduitDao extends JpaRepository<FactureProduit, Long> {

}
