package com.vente.application.services;

import java.util.List;  
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vente.application.entities.FactureProduit;
import com.vente.application.repository.FactureProduitDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FactureProduitService {

		@Autowired
	  	private FactureProduitDao factureProduitDao;
	
		
		
	    public List<FactureProduit>getAllFactureProduit(){
			
			return factureProduitDao.findAll();
		}
	    public Optional<FactureProduit> getFactureByIdProduit(Long idFactureProduit){
			
			return factureProduitDao.findById(idFactureProduit);
		}
	    public FactureProduit creerFactureProduit(FactureProduit factureProduit) {
	        // Vérifier si la facture est valide
	        if (factureProduit == null) {
	            throw new IllegalArgumentException("La facture fournie est nulle. Impossible de l'enregistrer.");
	        }
	        
	        // Vérifier si la commande, le produit et le client sont valides
	        if (factureProduit.getCommande() == null || factureProduit.getProduit() == null || factureProduit.getClient() == null) {
	            throw new IllegalArgumentException("Les informations de la facture sont incomplètes. Impossible de l'enregistrer.");
	        }
	        
	        // Enregistrer la facture en base de données
	        return factureProduitDao.save(factureProduit);
	    }
	    
	    
	    
		public FactureProduit modifierFactureProduit(Long idFactureProduit, FactureProduit factureProduit) {
	        Optional<FactureProduit> optionalFactureProduit = factureProduitDao.findById(idFactureProduit);
	        if (optionalFactureProduit.isPresent()) {
	            FactureProduit factureProduitExistant = optionalFactureProduit.get();
	            factureProduit.setMontantPaiementFactureProduit(0.0);
	       
	            return factureProduitDao.save(factureProduitExistant);
	        } else {
	            throw new RuntimeException("Facture non trouvée avec l'ID : " + idFactureProduit);
	        }
	    }
		
		
		
		public void deleteFactureProduit(Long idFactureProduit) {
			factureProduitDao.deleteById(idFactureProduit);
		}

		public void deleteAllFactureProduit() {
			factureProduitDao.deleteAll();
			
		}
		
}
