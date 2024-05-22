package com.vente.application.controllers;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vente.application.entities.Client;
import com.vente.application.entities.Compte;
import com.vente.application.services.CompteService;

import ch.qos.logback.core.model.Model;


@RestController
@RequestMapping("/Compte")
public class CompteController {
    
	@Autowired
    private CompteService compteService;

	
	
	@GetMapping("/compteVerification")
	public ModelAndView compteVerification(Model model) {
		ModelAndView modelAndView = new ModelAndView("compteVerification");
		return modelAndView;
	}


    @GetMapping("/AllComptes")
    public List<Compte> getAllComptes() {
   	 	return compteService.getAllComptes(); 
    }

    @GetMapping("/RechercherCompte/{idCompte}")
    public Optional<Compte> getCompteById(Long idCompte) {
        return compteService.getCompteByClientId(idCompte);
    }

    @GetMapping("/creerCompteForm")
    public ModelAndView showCreerCompteForm(Model model) {
    	ModelAndView modelAndView = new ModelAndView("compteCreation");
        modelAndView.addObject("client", new Client());
       
        
        return modelAndView; // Le nom de votre fichier HTML pour le formulaire de création de compte
    }
    
    @PostMapping("/creerCompte")
    public ResponseEntity<String> creerCompte(Client client,Long idClient) {
        try {
        	compteService.creerCompte(client);
             idClient =client.getIdClient();// Assurez-vous que cette méthode retourne l'ID du client créé

            return ResponseEntity.ok(idClient.toString()); // Retournez l'ID du client dans la réponse
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création du compte");
        }
    }

    @PutMapping("/modifierCompte/{idCompte}")
    public Compte modifierCompte(@PathVariable Long idCompte, @RequestBody Compte compte) {
        return compteService.modifierCompte( idCompte,compte);
    }

    @DeleteMapping("/DeleteCompte/{idCompte}")
    public void deleteCompte(@PathVariable Long idCompte) {
        compteService.deleteCompte(idCompte);
    }
    @DeleteMapping("/DeleteAllCompte")
    public void deleteAllCompte() {
        compteService.deleteAllCompte();
     }
}
