package com.vente.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vente.application.entities.Categorie;
import com.vente.application.entities.Produit;
import com.vente.application.repository.CategorieDao;
import com.vente.application.repository.ProduitDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategorieService {

   @Autowired
   private CategorieDao categorieDao;
   
   @Autowired
   private ProduitDao produitDao;
   
   
   public List<Categorie> getCategories(){
		
		return categorieDao.findAll();
	}
	
	public Optional<Categorie> getCategorieById(Long idCategorie){
		
		return categorieDao.findById(idCategorie);
	}
	
	public Categorie creerCategorie( Categorie categorie) {
		return categorieDao.save(categorie);
	}
	
	public Categorie modifierCategorie( Long idCategorie, Categorie categorie) {
		Optional <Categorie> categorieExistant = categorieDao.findById(idCategorie);
		if(categorieExistant.isPresent()) {
			Categorie newCategorie = categorieExistant.get();
			newCategorie.setImageCategorie(categorie.getImageCategorie());
			newCategorie.setNomCategorie(categorie.getNomCategorie());
			newCategorie.setProduits(categorie.getProduits());
			return categorieDao.save(newCategorie);
		}else {
		return null;		
		}
		
	}
	public void deleteCategorie(Long idCategorie) {
		categorieDao.deleteById(idCategorie);
	}
	public void deleteAllCategorie() {
		categorieDao.findAll();
	}
	
	public Categorie ajoutProduitCategorie(Long idCategorie, Long idProduit) {
		Categorie categorie = categorieDao.findById(idCategorie).orElse(null);
		Produit produit = produitDao.findById(idProduit).orElse(null);
		
		if(categorie!= null && produit!= null) {
			categorie.getProduits().add(produit);
			
			categorieDao.save(categorie);
		}else {
			throw new IllegalArgumentException("Categorie ou Produit non trouvé");
		}
		return categorie;
		
	}
	public Categorie deleteProduitCategorie(Long idCategorie, Long idProduit) {
		Categorie categorie = categorieDao.findById(idCategorie).orElse(null);
		Produit produit = produitDao.findById(idProduit).orElse(null);
		
		if(categorie!= null && produit!= null) {
			categorie.getProduits().remove(produit);
			
			categorieDao.save(categorie);
		}else {
			throw new IllegalArgumentException("Categorie ou Produit non trouvé");
		}
		return categorie;
	}
	public Categorie findAllProduitCategorie(Long idCategorie, Long idProduit) {

		Categorie categorie = categorieDao.findById(idCategorie).orElse(null);
		
        if(categorie!= null) {       	 
        	categorie.getProduits();
			
		}else {
			throw new IllegalArgumentException("Categorie non trouvé avec ID: " + idCategorie);
		}
        return categorie;
    }

}



