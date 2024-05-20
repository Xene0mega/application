package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Compte;

public interface CompteDao extends JpaRepository<Compte, Long> {

}
