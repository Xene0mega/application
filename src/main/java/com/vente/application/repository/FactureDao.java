package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Facture;

public interface FactureDao extends JpaRepository<Facture, Long> {

}
