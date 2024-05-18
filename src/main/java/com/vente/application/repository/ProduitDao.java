package com.vente.application.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository; 
import com.vente.application.entities.Produit;

public interface ProduitDao extends JpaRepository<Produit, Long> {
	
	List<Produit>findByCategorieId(Long idcategorie);
}
