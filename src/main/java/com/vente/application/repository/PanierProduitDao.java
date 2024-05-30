package com.vente.application.repository;

import com.vente.application.entities.PanierProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierProduitDao extends JpaRepository<PanierProduit, Long> {
}