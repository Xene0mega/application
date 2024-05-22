package com.vente.application.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List; 
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vente.application.entities.Client;
import com.vente.application.entities.Commande;
import com.vente.application.entities.Produit;
import com.vente.application.repository.ClientDao;
import com.vente.application.repository.CommandeDao;
import com.vente.application.repository.ProduitDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommandeService {

	private CommandeDao commandeDao;
	
	
	private ClientDao clientDao;
	
	private ProduitDao produitDao;
	
	
	
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
			newCommande.setDateLivraison(commande.getDateLivraison());
			newCommande.setPrixTotal(commande.getPrixTotal());
			
			return commandeDao.save(newCommande);
		}else {
		throw new RuntimeException("Commande non trouvée avec l'ID : " + idCommande);
		}
	}
		

	public Commande creerCommande(Commande commande, Long idClient, Long idProduit) {
        // Vérification de l'existence du client et du produit
        Client client = clientDao.findById(idClient)
                                 .orElseThrow(() -> new IllegalArgumentException("Client non trouvé avec l'ID : " + idClient));
        
        Produit produit = produitDao.findById(idProduit)
                                    .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID : " + idProduit));
        
       /* // Vérifiez si le client et le produit existent dans la base de données
        if (!clientDao.existsById(client.getIdClient())) {
            throw new IllegalArgumentException("Client n'existe pas");
        }
        if (!produitDao.existsById(produit.getIdProduit())) {
            throw new IllegalArgumentException("Produit n'existe pas");
        }*/

        // Attribution du client et du produit à la commande
        commande.setClient(client);
        commande.setProduit(produit);
        commande.setDateCommande(Date.valueOf(LocalDate.now()));

        // Enregistrement de la commande dans la base de données
        return commandeDao.save(commande);
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

