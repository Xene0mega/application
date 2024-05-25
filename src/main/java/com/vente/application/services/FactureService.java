package com.vente.application.services;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vente.application.entities.Client;
import com.vente.application.entities.Commande;
import com.vente.application.entities.Facture;
import com.vente.application.entities.Produit;
import com.vente.application.repository.ClientDao;
import com.vente.application.repository.CommandeDao;
import com.vente.application.repository.FactureDao;
import com.vente.application.repository.ProduitDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FactureService {

		@Autowired
	  	private FactureDao factureDao;
	
		
		
	    public List<Facture>getAllFactures(){
			
			return factureDao.findAll();
		}
	    public Optional<Facture> getFactureById(Long idFacture){
			
			return factureDao.findById(idFacture);
		}
	    public Facture creerFacture(Facture facture) {
	        // Vérifier si la facture est valide
	        if (facture == null) {
	            throw new IllegalArgumentException("La facture fournie est nulle. Impossible de l'enregistrer.");
	        }
	        
	        // Vérifier si la commande, le produit et le client sont valides
	        if (facture.getCommande() == null || facture.getProduit() == null || facture.getClient() == null) {
	            throw new IllegalArgumentException("Les informations de la facture sont incomplètes. Impossible de l'enregistrer.");
	        }
	        
	        // Enregistrer la facture en base de données
	        return factureDao.save(facture);
	    }
	    
	    
		public Facture modifierFacture(Long idFacture, Facture facture) {
	        Optional<Facture> optionalFacture = factureDao.findById(idFacture);
	        if (optionalFacture.isPresent()) {
	            Facture factureExistant = optionalFacture.get();
	            facture.getCommande();
	            facture.getMontantPaiementFacture();
	       
	            return factureDao.save(factureExistant);
	        } else {
	            throw new RuntimeException("Facture non trouvée avec l'ID : " + idFacture);
	        }
	    }
		
		
		
		public void deleteFacture(Long idFacture) {
			factureDao.deleteById(idFacture);
		}

		public void deleteAllFacture() {
			factureDao.deleteAll();
			
		}
		
}
