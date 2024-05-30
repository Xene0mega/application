package com.vente.application.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vente.application.entities.Panier;
import com.vente.application.entities.PanierProduit;
import com.vente.application.entities.Produit;
import com.vente.application.services.PanierService;
import com.vente.application.services.ProduitService;


@Controller
@RequestMapping("/Panier")
public class PanierController {

    @Autowired
    private PanierService panierService;
    

    @Autowired
    private ProduitService produitService;
    
    
    @GetMapping("/AfficherPanier")
    public ModelAndView afficherPanier() {
        Panier panier = panierService.getCurrentUserPanier();
        List<PanierProduit> panierProduits = panier.getProduits();

        ModelAndView modelAndView = new ModelAndView("panier");
        modelAndView.addObject("panierProduits", panierProduits);
        modelAndView.addObject("prixTotalPanier", panier.getPrixTotalPanier());
        modelAndView.addObject("quantiteTotalProduitPanier", panier.getQuantiteTotalProduitPanier());

        return modelAndView;
    }

    @PostMapping("/ajouterProduit")
    @ResponseBody
    public ResponseEntity<String> ajouterProduitAuPanier(@RequestParam Long idProduit, @RequestParam Long idCategorie){
    	
        boolean success = panierService.ajouterProduitAuPanier(idProduit, idCategorie);
        if (success) {
            return ResponseEntity.ok("Produit ajouté au panier avec succès");
        } else {
            return ResponseEntity.status(500).body("Erreur lors de l'ajout au panier");
        }
    }

    
    @PostMapping("/CreerPanier")
    public Panier creerPanier(@RequestBody Panier panier) {
    	
        return panierService.creerPanier(panier);
    }

    @PutMapping("/modifierPanier/{idPanier}")
    public Panier modifierPanier(@PathVariable Long idPanier, @RequestBody Panier panier) {
        return panierService.modifierPanier(idPanier, panier);
    }

    @DeleteMapping("/DeletePanier/{idPanier}")
    public void deletePanier(@PathVariable Long idPanier) {
        panierService.deletePanier(idPanier);
    }
    
    @DeleteMapping("/DeleteAllPanier")
    public void deleteAllPanier() {
        panierService.deleteAllPanier();
    }
        
       
    }
