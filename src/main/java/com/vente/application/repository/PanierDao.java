package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Panier;

public interface PanierDao extends JpaRepository<Panier, Long> {


}
