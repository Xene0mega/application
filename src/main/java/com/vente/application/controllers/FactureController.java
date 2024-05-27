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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vente.application.entities.Categorie;
import com.vente.application.entities.Client;
import com.vente.application.entities.Commande;
import com.vente.application.entities.Facture;
import com.vente.application.entities.Produit;
import com.vente.application.services.CategorieService;
import com.vente.application.services.ClientService;
import com.vente.application.services.CommandeService;
import com.vente.application.services.FactureService;
import com.vente.application.services.ProduitService;

@RestController
@RequestMapping("/Facture")
public class FactureController {
	
	 @Autowired
	    private CommandeService commandeService;
	 
	 @Autowired
	    private FactureService factureService;
	 
	 @Autowired
	    private ClientService clientService;
	 
	 @Autowired
	    private ProduitService produitService;
	 
	 @Autowired
	 private CategorieService categorieService;
	 
	 

	 @GetMapping("/AfficherFacture")
	 public ModelAndView afficherFacture(@RequestParam("idCommande") Long idCommande, @RequestParam("idCategorie") Long idCategorie) {
	     ModelAndView modelAndView = new ModelAndView("facture");
	     
	     // Récupérer la commande par son ID
	     Optional<Commande> commandeOptional = commandeService.getCommandeById(idCommande);
	     
	     if (commandeOptional.isPresent()) {
	         Commande commande = commandeOptional.get();
	         
	         // Récupérer le produit et le client associés à la commande
	         Produit produit = commande.getProduit();
	         Client client = commande.getClient();
	         Categorie categorie = commande.getCategorie();
	         
	         // Créer une facture en utilisant les détails de la commande
	         Facture facture = new Facture();
	         facture.setCommande(commande);
	         facture.setMontantPaiementFacture(commande.getPrixTotalCommande());
	         
	         // Ajouter les objets au modèle
	         modelAndView.addObject("commande", commande);
	         modelAndView.addObject("produit", produit);
	         modelAndView.addObject("client", client);
	         modelAndView.addObject("facture", facture);
	         modelAndView.addObject("categorie", categorie); // Ajouter l'ID de la catégorie au modèle
	         
	     } else {
	         modelAndView.addObject("errorMessage", "Commande non trouvée");
	     }
	     
	     return modelAndView;
	 }
	 @PostMapping("/creerFacture")
	 public ModelAndView enregistrerFacture(@RequestParam("idCommande") Long idCommande,
			                                @RequestParam("idClient") Long idClient, 
											@RequestParam("idProduit") Long idProduit,
											@RequestParam("idCategorie") Long idCategorie,
											@RequestParam("montantPaiementFacture") double montantPaiementFacture) 
	 {
	     // Récupérer la commande par son ID
	     Optional<Commande> commandeOptional = commandeService.getCommandeById(idCommande);
	     Optional<Client> clientOptional = clientService.getClientById(idClient);
	     Optional<Produit> produitOptional = produitService.getProduitById(idProduit);
	     Optional<Categorie> categorieOptional = categorieService.getCategorieById(idCategorie);


	     if (commandeOptional!=null && clientOptional!=null && produitOptional!=null) {
	    	 
	         Commande commande = commandeOptional.get();
             Client client = clientOptional.get();
             Produit produit = produitOptional.get();
             Categorie categorie = categorieOptional.get();
	         // Créer une facture en utilisant les détails de la commande
	         Facture facture = new Facture();
	         facture.setMontantPaiementFacture(montantPaiementFacture);
	         facture.setCommande(commande);
	         facture.setClient(client);
	         facture.setProduit(produit);
	         facture.setCategorie(categorie); // Définir l'ID de la catégorie dans la facture


	         // Enregistrer la facture
	         factureService.creerFacture(facture);

	         ModelAndView modelAndView = new ModelAndView("redirect:/Categories/voirTout");
	         return modelAndView;
	     } else {
	         ModelAndView modelAndView = new ModelAndView("redirect:/erreur");
	         return modelAndView;
	     }
	 }

	    @GetMapping("/AllFactures")
	    public ModelAndView getAllFactures(){
	    	
	    	 List<Facture> listeFacture = factureService.getAllFactures();
	    	 ModelAndView modelAndView;
	    	 
	    	  if (listeFacture.isEmpty()) {
	    		 
	    	        modelAndView = new ModelAndView("aucuneFacture"); 
	    	        modelAndView.addObject("message", "Aucune facture disponible.");
	    	    } else {
	    	        // Si la liste des factures n'est pas vide, affichez la liste des factures
	    	        modelAndView = new ModelAndView("listeFacture");
	    	        modelAndView.addObject("listeFacture", listeFacture);
	    	    }
	    	  return modelAndView;
	    	  
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
