package com.vente.application.controllers;

import java.util.List; 
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vente.application.entities.Produit;
import com.vente.application.services.ProduitService;

@RestController
@RequestMapping("/Produits")
public class ProduitController {
	 @Autowired
	    private ProduitService produitService;
	 
	 
	    @GetMapping("/AllProduits")
	    public List<Produit> getAllProduits() {
	    	 return produitService.getAllProduits();
	       
	    }

	    @GetMapping("/RechercherProduit/{idProduit}")
	    public Optional<Produit> getProduitById(@PathVariable Long idProduit) {
	    	
	        return  produitService.getProduitById(idProduit);
	    }

	    @PostMapping("/CreerProduit")
	    public Produit createProduit(@RequestBody Produit produit) {
	    	
	        return produitService.creerProduit(produit);
	    }

	    @PutMapping("/modifierProduit/{idProduit}")
	    public Produit modifierProduit(@PathVariable Long idProduit, @RequestBody Produit produit) {
	        return produitService.modifierProduit(idProduit, produit);
	    }

	    @DeleteMapping("/DeleteProduit/{idProduit}")
	    public void deleteProduit(@PathVariable Long idProduit) {
	        produitService.deleteProduit(idProduit);
	    }
	    @DeleteMapping("/Tout")
	    public void deleteAllProduit() {
	        produitService.deleteAllProduit();
	    }
	    
	    @GetMapping("/Categorie/{idCategorie}")
	    public List<Produit> getProduitsByCategorie(@PathVariable Long idCategorie) {
	    	
	      return produitService.getAllProduitsByCategorieId(idCategorie);
	       
	    }
}
