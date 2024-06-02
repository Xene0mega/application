package com.vente.application.controllers;

import com.vente.application.entities.Categorie;
import com.vente.application.entities.Client; 
import com.vente.application.entities.Commande;
import com.vente.application.entities.Produit;
import com.vente.application.services.CategorieService;
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
    
    @Autowired
    private CategorieService categorieService;
 


    @GetMapping("/commandeForm")
    public ModelAndView afficherFormulaireCommande(@RequestParam("idProduit") Long idProduit, 
                                                  @RequestParam("idCategorie") Long idCategorie,
                                                  @RequestParam("idClient") Long idClient) {
        ModelAndView modelAndView = new ModelAndView("passerCommande");

        // Récupérer le produit, le client et la catégorie à partir des ID
        Optional<Produit> produitOptional = produitService.getProduitById(idProduit);
        Optional<Client> clientOptional = clientService.getClientById(idClient);
        Optional<Categorie> categorieOptional = categorieService.getCategorieById(idCategorie);

        if (produitOptional.isPresent() && clientOptional.isPresent() && categorieOptional.isPresent()) {
            Produit produit = produitOptional.get();
            Client client = clientOptional.get();
            Categorie categorie = categorieOptional.get();

            // Créer une nouvelle commande
            Commande commande = new Commande();
            commande.setProduit(produit);
            commande.setClient(client);

            // Ajouter les objets au modèle
            modelAndView.addObject("commande", commande);
            modelAndView.addObject("produit", produit);
            modelAndView.addObject("client", client);
            modelAndView.addObject("categorie", categorie);
        } else {
            // Gérer le cas où l'un des éléments n'est pas trouvé
            modelAndView.addObject("errorMessage", "Produit, client ou catégorie non trouvé");
        }

        return modelAndView;
    }



    @PostMapping("/creerCommande")
    public ModelAndView creerCommande(@ModelAttribute("commande") Commande commande, 
                                      @RequestParam("idProduit") Long idProduit,
                                      @RequestParam("idCategorie") Long idCategorie,
                                      @RequestParam("idClient") Long idClient,
                                      @RequestParam("dateLivraisonCommande") Date dateLivraisonCommande) {
        // Récupérer le produit, le client et la catégorie à partir des ID
        Optional<Produit> produitOptional = produitService.getProduitById(idProduit);
        Optional<Client> clientOptional = clientService.getClientById(idClient);
        Optional<Categorie> categorieOptional = categorieService.getCategorieById(idCategorie);

        if (produitOptional.isPresent() && clientOptional.isPresent() && categorieOptional.isPresent()) {
            Produit produit = produitOptional.get();
            Client client = clientOptional.get();
            Categorie categorie = categorieOptional.get();

            // Lier le produit, le client et la catégorie à la commande
            commande.setProduit(produit);
            commande.setClient(client);
            commande.setCategorie(categorie);

            // Définir la date de livraison saisie par le client
            commande.setDateLivraisonCommande(dateLivraisonCommande);

            // Enregistrer la commande
            Commande nouvelleCommande = commandeService.creerCommande(commande);

            // Récupérer l'ID de la commande enregistrée
            Long idCommande = nouvelleCommande.getIdCommande();

            // Rediriger vers la page de facture avec l'ID de la commande et de la catégorie
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("idCommande", idCommande);
            modelAndView.addObject("idCategorie", idCategorie);
            modelAndView.setViewName("redirect:/Facture/AfficherFactureProduit?idCommande=" + idCommande + "&idCategorie=" + idCategorie);

            return modelAndView;
        } else {
            // Gérer le cas où le produit, le client ou la catégorie n'est pas trouvé
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
