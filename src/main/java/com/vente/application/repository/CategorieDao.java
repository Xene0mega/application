package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Categorie;

public interface CategorieDao extends JpaRepository<Categorie, Long> {

}


