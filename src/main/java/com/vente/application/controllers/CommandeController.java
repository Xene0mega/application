package com.vente.application.controllers;

import com.vente.application.entities.Client; 
import com.vente.application.entities.Commande;
import com.vente.application.entities.Produit;
import com.vente.application.services.ClientService;
import com.vente.application.services.CommandeService;
import com.vente.application.services.ProduitService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ModelAndView afficherFormulaireCommande(Long idClient, Long idProduit) {
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


    @PostMapping("/creerCommande")
    public ModelAndView creerCommande(@ModelAttribute("commande") Commande commande, 
                                      @RequestParam("idProduit") Long idProduit,
                                      @RequestParam("idClient") Long idClient,
                                      @RequestParam("dateLivraisonCommande") Date dateLivraisonCommande) {
        // Récupérer le produit et le client à partir des ID
        Optional<Produit> produitOptional = produitService.getProduitById(idProduit);
        Optional<Client> clientOptional = clientService.getClientById(idClient);
        
        if (produitOptional.isPresent() && clientOptional.isPresent()) {
            Produit produit = produitOptional.get();
            Client client = clientOptional.get();
            
            // Lier le produit et le client à la commande
            commande.setProduit(produit);
            commande.setClient(client);
            
         // Définir la date de livraison saisie par le client
            commande.setDateLivraisonCommande(dateLivraisonCommande);
            // Enregistrer la commande
            Commande nouvelleCommande = commandeService.creerCommande(commande);
            
            // Récupérer l'ID de la commande enregistrée
            Long idCommande = nouvelleCommande.getIdCommande();
            
            // Rediriger vers la page de facture avec l'ID de la commande
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("idCommande", idCommande);
            modelAndView.setViewName("redirect:/Facture/AfficherFacture?idCommande=" + idCommande);
            
            return modelAndView;
        } else {
            // Gérer le cas où le produit ou le client n'est pas trouvé
            // Par exemple, rediriger vers une page d'erreur
            return new ModelAndView("redirect:/erreur");
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
