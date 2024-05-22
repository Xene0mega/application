package com.vente.application.controllers;

import com.vente.application.entities.Client;
import com.vente.application.entities.Commande;
import com.vente.application.entities.Produit;
import com.vente.application.services.ClientService;
import com.vente.application.services.CommandeService;
import com.vente.application.services.ProduitService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/Commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    
    @Autowired
    private ProduitService produitService;
    
    @Autowired
    private ClientService clientService;

    @GetMapping("/commandeForm")
    public ModelAndView afficherPageCommande(@RequestParam("idProduit") Long idProduit, @RequestParam("idClient") Long idClient) {
        ModelAndView modelAndView = new ModelAndView("passerCommande");
        
        Optional<Produit> produit = produitService.getProduitById(idProduit);
        Optional<Client> client = clientService.getClientById(idClient);
        if (produit.isPresent()&& client.isPresent()) {
            Commande commande = new Commande();
            
            commande.setProduit(produit.get());
            commande.setClient(new Client()); // Assurez-vous que le client est initialisé correctement
            modelAndView.addObject("commande", commande); // Utilisez "commande" au lieu de "passerCommande"
            modelAndView.addObject("produit", produit.get());
            modelAndView.addObject("client", client.get()); // Ajoutez le client au modèle
        } else {
            modelAndView.addObject("errorMessage", "Produit non trouvé");
        }
        return modelAndView;
    }


    @PostMapping("/passerCommande")
    public ResponseEntity<String> creerCommande(Commande commande, Long idCommande, Long idClient, Long idProduit) {
    	 try {
    	        commandeService.creerCommande(commande, idClient, idProduit);
    	        
    	         idCommande = commande.getIdCommande();
    	        

    		  return ResponseEntity.ok(idCommande.toString());
             
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de lors de l'envoi de la commande");
         }
     }
    
    @GetMapping("/AllCommandes")
    public List<Commande>getAllCommandes(){
		
		return commandeService.getAllCommandes();
	}

    @PutMapping("/modifierCommande/{idCommande}")
    public Commande modifierCommande(@PathVariable Long idCommande, @RequestBody Commande commande) {
        return commandeService.modifierCommande(idCommande, commande);
    }

    @DeleteMapping("/DeleteCommande/{idCommande}")
    public void deleteCommande(@PathVariable Long idCommande) {
        commandeService.deleteCommande(idCommande);
    }
    @DeleteMapping("/DeleteAllCommande")
    public void deleteAllCommande() {
        commandeService.deleteAllCommande();
    }
    

	
    
}
