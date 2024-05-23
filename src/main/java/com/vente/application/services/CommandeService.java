package com.vente.application.services;

import java.sql.Date;
import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vente.application.entities.Commande;


import com.vente.application.repository.CommandeDao;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommandeService {

	@Autowired
	private CommandeDao commandeDao;
	
   public List<Commande>getAllCommandes(){
		
		return commandeDao.findAll();
	}
	
    public Optional<Commande> getCommandeById(Long idCommande){
		
		return commandeDao.findById(idCommande);
	}
	

	
	public Commande modifierCommande(Long idCommande, Commande commande) {
		Optional <Commande> commandeExistant = commandeDao.findById(idCommande);
		if(commandeExistant.isPresent()) {
			Commande newCommande = commandeExistant.get();
			
			newCommande.setDateCommande(commande.getDateCommande());
			newCommande.setDateLivraisonCommande(commande.getDateLivraisonCommande());
			newCommande.setPrixTotalCommande(commande.getPrixTotalCommande());
			
			return commandeDao.save(newCommande);
		}else {
		throw new RuntimeException("Commande non trouvée avec l'ID : " + idCommande);
		}
	}
		

	  public Commande creerCommande(Commande commande) {
		  
	
		    
	        // Définir la date de commande et de livraison
		  commande.setDateCommande(new java.sql.Date(System.currentTimeMillis()));
	        
	        
		   Date dateLivraisonCommande = commande.getDateLivraisonCommande(); // Récupérer la date de livraison saisie par le client
		    commande.setDateLivraisonCommande(dateLivraisonCommande);
	        
	     // Définir les frais de livraison à 2000
	        commande.setFraisLivraisonCommande(2000.0);
	        
	        
	        Double prixTotalCommande = calculerPrixTotalCommande(commande);
	        commande.setPrixTotalCommande(prixTotalCommande);
	        
	        // Enregistrer la commande dans la base de données
	        return commandeDao.save(commande);
	    }
	  
	  
	   private double calculerPrixTotalCommande(Commande commande) {
		   double prixTotalCommande = 0.0;
	        // Calculer le prix du produit
	       
	        if (commande != null && commande.getProduit() != null) {
	        	 double prixProduit = commande.getProduit().getPrixProduit();
	        // Ajouter les frais de livraison au prix du produit pour obtenir le prix total
	         prixTotalCommande = prixProduit + commande.getFraisLivraisonCommande();
	        
	        }
	        return prixTotalCommande;
	        
	   }
	        
	
	
	
	public void deleteCommande(Long idCommande) {
		commandeDao.deleteById(idCommande);
	}
	public void deleteAllCommande() {
		commandeDao.deleteAll();
	}
	
	
	/*public List<Produit> getProduitsParCommande(Long idcommande) {
		
        Optional<Commande> commandeExistant = commandeDao.findById(idcommande);
        
        if (commandeExistant.isPresent()) {
            Commande commande = commandeExistant.get();
            
            return commande.getProduit();
            
        } else {
            throw new RuntimeException("Commande non trouvée avec l'ID : " + idcommande);
        }
    }

    public void validerCommande(Long idcommande) {
        Optional<Commande> commandeExistant = commandeDao.findById(idcommande);
        if (commandeExistant.isPresent()) {
            Commande commande = commandeExistant.get();
            commande.setEtat("validée");
            commandeDao.save(commande);
        } else {
            throw new RuntimeException("Commande non trouvée avec l'ID : " + idcommande);
        }
    }

    public void annulerCommande(Long idcommande) {
        Optional<Commande> commandeExistant = commandeDao.findById(idcommande);
        if (commandeExistant.isPresent()) {
            Commande commande = commandeExistant.get();
            commande.setEtat("annulée");
            commandeDao.save(commande);
        } else {
            throw new RuntimeException("Commande non trouvée avec l'ID : " + idcommande);
        }
    }*/
	    
	
}

