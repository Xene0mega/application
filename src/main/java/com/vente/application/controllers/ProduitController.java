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
import org.springframework.web.servlet.ModelAndView;

import com.vente.application.entities.Produit;
import com.vente.application.services.ProduitService;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/Produits")
public class ProduitController {
	 @Autowired
	    private ProduitService produitService;
	 
	 
	    @GetMapping("/AllProduits")
	    public List<Produit> getAllProduits() {
	    	 return produitService.getAllProduits();
	       
	    }
	    @GetMapping("/produitDetail/{idProduit}")
	    public ModelAndView getProduitDetail(@PathVariable Long idProduit, Model model) {
	        Optional<Produit> optionalProduit = produitService.getProduitById(idProduit);
	        ModelAndView modelAndView = new ModelAndView("produitDetail");
	            
	        if (optionalProduit.isPresent()) {
	            Produit produit = optionalProduit.get();
	            modelAndView.addObject("produitDetail", produit);
	            modelAndView.addObject("idCategorie", produit.getCategorie().getIdCategorie());
	        } else {
	            modelAndView.addObject("errorMessage", "Produit non trouvé avec ID: " + idProduit);
	        }
	            
	        return modelAndView;
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
	    @DeleteMapping("/DeleteAllProduit")
	    public void deleteAllProduit() {
	        produitService.deleteAllProduit();
	    }
	    
	    @GetMapping("/Categorie/{idCategorie}")
	    public List<Produit> getProduitsByCategorie(@PathVariable Long idCategorie) {
	    	
	      return produitService.getAllProduitsByCategorieId(idCategorie);
	       
	    }
}
