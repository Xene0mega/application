package com.vente.application.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;  
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.vente.application.entities.FactureProduit;
import com.vente.application.entities.FacturePanier;
import com.vente.application.entities.Panier;
import com.vente.application.entities.PanierProduit;
import com.vente.application.entities.Produit;
import com.vente.application.services.CategorieService;
import com.vente.application.services.ClientService;
import com.vente.application.services.CommandeService;
import com.vente.application.services.FacturePanierService;
import com.vente.application.services.FactureProduitService;
import com.vente.application.services.PanierProduitService;
import com.vente.application.services.PanierService;
import com.vente.application.services.ProduitService;

@RestController
@RequestMapping("/Facture")
public class FactureController {
	
	 @Autowired
	    private CommandeService commandeService;
	 
	 @Autowired
	    private FactureProduitService factureProduitService;
	 
	 @Autowired
	    private ClientService clientService;
	 
	 @Autowired
	    private ProduitService produitService;
	 
	 @Autowired
	 private CategorieService categorieService;
	 
	 @Autowired
	 private FacturePanierService facturePanierService;
	 
	 @Autowired
	 private PanierService panierService;
	 
	 

	 @GetMapping("/AfficherFactureProduit")
	 public ModelAndView afficherFactureProduit(@RequestParam("idCommande") Long idCommande, @RequestParam("idCategorie") Long idCategorie) {
	     ModelAndView modelAndView = new ModelAndView("factureProduit");
	     
	     // Récupérer la commande par son ID
	     Optional<Commande> commandeOptional = commandeService.getCommandeById(idCommande);
	     
	     if (commandeOptional.isPresent()) {
	         Commande commande = commandeOptional.get();
	         
	         // Récupérer le produit et le client associés à la commande
	         Produit produit = commande.getProduit();
	         Client client = commande.getClient();
	         Categorie categorie = commande.getCategorie();
	         
	         // Créer une facture en utilisant les détails de la commande
	         FactureProduit factureProduit = new FactureProduit();
	         factureProduit.setCommande(commande);
	         factureProduit.setMontantPaiementFactureProduit(commande.getPrixTotalCommande());
	         
	         // Ajouter les objets au modèle
	         modelAndView.addObject("commande", commande);
	         modelAndView.addObject("produit", produit);
	         modelAndView.addObject("client", client);
	         modelAndView.addObject("factureProduit", factureProduit);
	         modelAndView.addObject("categorie", categorie); // Ajouter l'ID de la catégorie au modèle
	         
	     } else {
	         modelAndView.addObject("errorMessage", "Commande non trouvée");
	     }
	     
	     return modelAndView;
	 }
	 @PostMapping("/creerFactureProduit")
	 public ModelAndView enregistrerFactureProduit(@RequestParam("idCommande") Long idCommande,
			                                @RequestParam("idClient") Long idClient, 
											@RequestParam("idProduit") Long idProduit,
											@RequestParam("idCategorie") Long idCategorie,
											@RequestParam("montantPaiementFactureProduit") double montantPaiementFactureProduit) 
	 {
	    
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
	         FactureProduit factureProduit = new FactureProduit();
	         factureProduit.setMontantPaiementFactureProduit(montantPaiementFactureProduit);
	         factureProduit.setCommande(commande);
	         factureProduit.setClient(client);
	         factureProduit.setProduit(produit);
	         factureProduit.setCategorie(categorie); // Définir l'ID de la catégorie dans la facture


	         // Enregistrer la facture
	         factureProduitService.creerFactureProduit(factureProduit);

	         ModelAndView modelAndView = new ModelAndView("redirect:/Categories/voirTout");
	         return modelAndView;
	     } else {
	         ModelAndView modelAndView = new ModelAndView("redirect:/erreur");
	         return modelAndView;
	     }
	 }
	 
	 
	 
	 

	    @GetMapping("/factureLivraisonPanier")
	        public ModelAndView afficherFormulaireDateLivraisonPanier(@RequestParam("idPanier") Long idPanier, @RequestParam("idClient") Long idClient) {
	            ModelAndView modelAndView = new ModelAndView("factureLivraisonPanier");
	            modelAndView.addObject("idPanier", idPanier);
	            modelAndView.addObject("idClient", idClient);
	            return modelAndView;
	        }
	    
	 
	    @GetMapping("/AfficherFacturePanier")
	    public ModelAndView afficherFacturePanier(@RequestParam("idPanier") Long idPanier, @RequestParam("idClient") Long idClient, Date dateLivraisonPanier){
	        ModelAndView modelAndView = new ModelAndView("facturePanier");

	        Optional<Panier> panierOptional = panierService.getPanierById(idPanier);
	        Optional<Client> clientOptional = clientService.getClientById(idClient);

	        if (panierOptional.isPresent() && clientOptional.isPresent()) {
	            Panier panier = panierOptional.get();
	            Client client = clientOptional.get();
	            
	            List<PanierProduit> produits = panier.getProduits();

	         // Création de la facture
	            FacturePanier facturePanier = new FacturePanier();
	            
	            
	            
	            facturePanier.setPanier(panier);
	            facturePanier.setClient(client); 
	            facturePanier.setDateLivraisonPanier( dateLivraisonPanier);
	            
	            
	            Double fraisLivraisonPanier = facturePanierService.FraisLivraisonPanier(panier);
	            Double montantPaiementFacturePanier = facturePanierService.MontantPaiementFacturePanier(panier);
	            
	            Date dateFacturePanier =new java.sql.Date(System.currentTimeMillis());
	            
	            facturePanier.setFraisLivraisonPanier(fraisLivraisonPanier);
	            facturePanier.setMontantPaiementFacturePanier(montantPaiementFacturePanier);
	            

	            modelAndView.addObject("facturePanier", facturePanier);
	            modelAndView.addObject("client", client);
	            modelAndView.addObject("produits", produits);

	            modelAndView.addObject("dateFacturePanier", dateFacturePanier);
	            modelAndView.addObject("dateLivraisonPanier", dateLivraisonPanier);
	            modelAndView.addObject("fraisLivraisonPanier", fraisLivraisonPanier);
	            modelAndView.addObject("montantPaiementFacturePanier", montantPaiementFacturePanier);
	        } else {
	            modelAndView.addObject("errorMessage", "Commande non trouvée");
	        }

	        return modelAndView;
	    }

	 

		 @PostMapping("/creerFacturePanier")
		 public ModelAndView enregistrerFacturePanier(@RequestParam("idPanier") Long idPanier,
				                                @RequestParam("idClient") Long idClient, 
												@RequestParam("montantPaiementFacturePanier") double montantPaiementFacturePanier,
												@RequestParam("dateLivraisonPanier") Date dateLivraisonPanier,
												@RequestParam("fraisLivraisonPanier") Double fraisLivraisonPanier) 
		 {
		    
		     Optional<Panier> panierOptional = panierService.getPanierById(idPanier);
		     Optional<Client> clientOptional = clientService.getClientById(idClient);


		     if (panierOptional!=null && clientOptional!=null ) {
		    	 
		         Panier panier = panierOptional.get();
	             Client client = clientOptional.get();
	             
		         // Créer une facture en utilisant les détails du panier
		         FacturePanier facturePanier = new FacturePanier();

				   Date dateFacturePanier =new java.sql.Date(System.currentTimeMillis());
				   
		         facturePanier.setDateLivraisonPanier(dateLivraisonPanier);
		        
		         facturePanier.setDateFacturePanier(dateFacturePanier); 
		         facturePanier.setFraisLivraisonPanier(fraisLivraisonPanier);
		         facturePanier.setMontantPaiementFacturePanier(montantPaiementFacturePanier);
		         facturePanier.setPanier(panier);
		         facturePanier.setClient(client);
		        

		         // Enregistrer la facture
		         facturePanierService.creerFacturePanier(facturePanier, panier);

		         ModelAndView modelAndView = new ModelAndView("redirect:/Categories/voirTout");
		         return modelAndView;
		     } else {
		         ModelAndView modelAndView = new ModelAndView("redirect:/erreur");
		         return modelAndView;
		     }
		 }
		 
		 
		 
	

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 @GetMapping("/AllFactures")
		 public ModelAndView afficherToutesLesFactures() {
		     ModelAndView modelAndView = new ModelAndView("listeFacture");
		     
		     // Récupérez les factures de produits et de panier séparément
		     List<FactureProduit> facturesProduit = factureProduitService.getAllFactureProduit();
		     List<FacturePanier> facturesPanier = facturePanierService.getAllFacturePanier();
		     
		     // Ajoutez les factures de produits et de panier à la vue
		     modelAndView.addObject("facturesProduit", facturesProduit);
		     modelAndView.addObject("facturesPanier", facturesPanier);
		     
		     return modelAndView;
		 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	    @GetMapping("/AllFacturesProduits")
	    public ModelAndView getAllFactureProduit(){
	    	
	    	 List<FactureProduit> listeFactureProduit = factureProduitService.getAllFactureProduit();
	    	 ModelAndView modelAndView;
	    	 
	    	        // Si la liste des factures n'est pas vide, affichez la liste des factures
	    	        modelAndView = new ModelAndView("listeFactureProduit");
	    	        modelAndView.addObject("listeFactureProduit", listeFactureProduit);
	    	    
	    	  return modelAndView;
	    	  
	    }
	    

	    @GetMapping("/AllFacturesPaniers")
	    public ModelAndView getAllFacturePanier(){
	    	
	    	 List<FacturePanier> listeFacturePanier = facturePanierService.getAllFacturePanier();
	    	 ModelAndView modelAndView;
	    	 
	    	        // Si la liste des factures n'est pas vide, affichez la liste des factures
	    	        modelAndView = new ModelAndView("listeFacturePanier");
	    	        modelAndView.addObject("listeFacturePanier", listeFacturePanier);
	    	   
	    	  return modelAndView;
	    	  
	    }

	    @PutMapping("/modifierFactureProduit/{idFactureProduit}")
	    public FactureProduit modifierFactureProduit(@PathVariable Long idFactureProduit, @RequestBody FactureProduit factureProduit) {
	        return factureProduitService.modifierFactureProduit(idFactureProduit, factureProduit);
	    }

	    @DeleteMapping("/DeleteFactureProduit/{idFactureProduit}")
	    public void deleteFactureProduit(@PathVariable Long idFactureProduit) {
	        factureProduitService.deleteFactureProduit(idFactureProduit);
	    }
	    @DeleteMapping("/DeleteAllFactureProduit")
	    public void deleteAllFacture() {
	        factureProduitService.deleteAllFactureProduit();
	    }
	    

}
