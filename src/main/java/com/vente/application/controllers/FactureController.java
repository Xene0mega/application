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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vente.application.entities.Commande;
import com.vente.application.entities.Facture;
import com.vente.application.services.CommandeService;
import com.vente.application.services.FactureService;

@RestController
@RequestMapping("/Facture")
public class FactureController {
	
	 @Autowired
	    private CommandeService commandeService;
	 
	 @Autowired
	    private FactureService factureService;
	 

	@GetMapping("/AfficherFacture")
	public ModelAndView afficherFacture(@RequestParam("idCommande") Long idCommande) {
		
		ModelAndView modelAndView = new ModelAndView("facture");
		
	    Optional<Commande> commande = commandeService.getCommandeById(idCommande);   
   
        
        if (commande.isPresent()) {
        	
            Facture facture =new Facture();
          
            facture.setCommande(commande.get());
            
            modelAndView.addObject("facture", facture); 
            modelAndView.addObject("commande", commande.get());
   
            
        } else {
            modelAndView.addObject("errorMessage", "commande non trouvé");
        }
        return modelAndView;
    }
	  @PostMapping("/validerFacture")
	    public ResponseEntity<String> validerFacture(Facture facture, Long idCommande) {
	    	 try {
	    		    factureService.validerFacture(facture);

	             return ResponseEntity.ok("facture effectué avec succès");
	             
	         } catch (Exception e) {
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de lors de l'envoi de la commande");
	         }
	     }
	    
	    @GetMapping("/AllFactures")
	    public List<Facture>getAllFactures(){
			
			return factureService.getAllFactures();
		}

	    @PutMapping("/modifierFacture/{idFacture}")
	    public Facture modifierFacture(@PathVariable Long idFacture, @RequestBody Facture facture) {
	        return factureService.modifierFacture(idFacture, facture);
	    }

	    @DeleteMapping("/DeleteFacture/{idFacture}")
	    public void deleteFacture(@PathVariable Long idFacture) {
	        factureService.deleteFacture(idFacture);
	    }
	    @DeleteMapping("/DeleteAllFacture")
	    public void deleteAllFacture() {
	        factureService.deleteAllFacture();
	    }
	    

}
