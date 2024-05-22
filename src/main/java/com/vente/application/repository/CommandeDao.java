package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Commande;

public interface CommandeDao extends JpaRepository<Commande, Long> {

}
