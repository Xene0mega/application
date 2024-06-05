package com.vente.application.services;

import java.util.List;  

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vente.application.entities.Produit;
import com.vente.application.repository.ProduitDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProduitService {
	
	@Autowired
  	private ProduitDao produitDao;
	
	
	
    public List<Produit>getAllProduits(){
		
		return produitDao.findAll();
	}
    public Optional<Produit> getProduitById(Long idProduit){

		Optional<Produit> produit = produitDao.findById(idProduit);
		
        if(!produit.isPresent()) {

			throw new IllegalArgumentException("Categorie non trouvé avec ID: " + idProduit);
		}
        return produit;
    }
	
	public Produit creerProduit(Produit produit) {
		return produitDao.save(produit);
	}
	
	public Produit modifierProduit(Long idProduit, Produit produit) {
		Optional <Produit> produitExistant = produitDao.findById(idProduit);
		if(produitExistant.isPresent()) {
			Produit newProduit = produitExistant.get();
			
			newProduit.setNomProduit(produit.getNomProduit());
			newProduit.setDescriptionProduit(produit.getDescriptionProduit());
			newProduit.setImageProduit(produit.getImageProduit());
			newProduit.setPrixProduit(produit.getPrixProduit());
			newProduit.setQuantiteEnStockProduit(produit.getQuantiteEnStockProduit());
			newProduit.setCategorie(produit.getCategorie());		
			
			return produitDao.save(newProduit);
		}else {
		return null;
			
		}
		
	}
	public void deleteProduit(Long idProduit) {
		produitDao.deleteById(idProduit);
	}
	public void deleteAllProduit() {
		produitDao.deleteAll();
	}
	
	public List<Produit> getAllProduitsByCategorieId(Long idCategorie){
		return produitDao.findByCategorie_IdCategorie(idCategorie);
	}

}
