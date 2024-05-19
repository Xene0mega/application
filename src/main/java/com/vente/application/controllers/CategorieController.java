package com.vente.application.controllers; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.vente.application.entities.Categorie;
import com.vente.application.services.CategorieService;

@RestController
@RequestMapping("/Categories")

public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @PostMapping("/ajouterProduit/{idCategorie}/{idProduit}")
    public Categorie ajouterProduitAuCategorie(@RequestParam Long idCategorie, @RequestParam Long idProduit) {
        return categorieService.ajoutProduitCategorie(idCategorie, idProduit);
    }

    @DeleteMapping("/supprimerProduit/{idCategorie}/{idProduit}")
    public void deleteProduitCatalogue(@RequestParam Long idCategorie, @RequestParam Long idProduit) {
        categorieService.deleteProduitCategorie(idCategorie, idProduit);
    }
    
    @GetMapping("/AfficherProduitCategorie/{idCategorie}")
    public ModelAndView afficherToutProduitCategorie(@PathVariable Long idCategorie, Model model) {
    	ModelAndView modelAndView = new ModelAndView("categorie");
    	modelAndView.addObject("categorie", categorieService.findAllProduitCategorie(idCategorie) );
    	
        return modelAndView;
    }
    
    @GetMapping("/voirTout")
    public ModelAndView getAllCatgeories(Model model) {
    	ModelAndView modelAndView = new ModelAndView("voirTout");
    	modelAndView.addObject("voirTout", categorieService.getAllCategories());
    	 return modelAndView;
       
    }
 
    @GetMapping("/RechercherCategorie/{idCategorie}")
    public Optional<Categorie> getCategorieById(@PathVariable Long idCategorie) {
    	
        return  categorieService.getCategorieById(idCategorie);
    }

    @PostMapping("/CreerCategorie")
    public Categorie createCategorie(@RequestBody Categorie categorie) {
    	
        return categorieService.creerCategorie(categorie);
    }

    @PutMapping("/modifierCategorie/{idCategorie}")
    public Categorie modifierCategorie(@PathVariable Long idCategorie, @RequestBody Categorie categorie) {
    	
        return categorieService.modifierCategorie( idCategorie, categorie);
    }

    @DeleteMapping("/DeleteCategorie/{idCategorie}")
    public void deleteCategorie(@PathVariable Long idCategorie) {
        categorieService.deleteCategorie(idCategorie);
    }
    @DeleteMapping("/DeleteAllCategorie")
    public void deleteAllCategorie() {
        categorieService.deleteAllCategories();
    }

    @GetMapping("/Categories")
    public List<Categorie> getAllCategories() {
    	 return categorieService.getAllCategories();
       
    }
}
