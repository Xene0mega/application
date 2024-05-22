package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Paiement;

public interface PaiementDao extends JpaRepository<Paiement, Long> {

}
