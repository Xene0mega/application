package com.vente.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vente.application.entities.Facture;
import com.vente.application.repository.FactureDao;

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
		public void validerFacture(Facture facture) {
	        
	        if (facture.getMontantPaiementFacture() <= 0) {
	            throw new IllegalArgumentException("Le montant de la facture doit être positif.");
	        }
	        if (facture.getClient() == null) {
	            throw new IllegalArgumentException("Un client doit être associé à la facture.");
	        }
	    }
		public void deleteAllFacture() {
			factureDao.deleteAll();
			
		}
		
}
