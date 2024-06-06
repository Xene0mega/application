package com.vente.application.services;

import java.sql.Date; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vente.application.entities.FacturePanier;
import com.vente.application.entities.Panier;
import com.vente.application.repository.FacturePanierDao;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FacturePanierService {
	
	@Autowired
	private FacturePanierDao facturePanierDao;

	
    public List<FacturePanier>getAllFacturePanier(){
		
		return facturePanierDao.findAll();
	}
    public Optional<FacturePanier> getFacturePanierById(Long idFacture){
		
		return facturePanierDao.findById(idFacture);
	}
    public FacturePanier creerFacturePanier(FacturePanier facturePanier, Panier panier) { 
   
	        
	        
		   Date dateLivraisonPanier = facturePanier.getDateLivraisonPanier(); // Récupérer la date de livraison saisie par le client
		    facturePanier.setDateLivraisonPanier(dateLivraisonPanier);
	        
		   Date dateFacturePanier =new java.sql.Date(System.currentTimeMillis());
		   facturePanier.setDateFacturePanier(dateFacturePanier);
		   

            int nbreProduitPanier= panier.getProduits().size();
            
		    Double fraisLivraisonPanierU = 1000.0;
		    
		    Double fraisLivraisonPanier = nbreProduitPanier*fraisLivraisonPanierU;
		    
		    facturePanier.setFraisLivraisonPanier(fraisLivraisonPanier);
		    
		    Double montantPaiementFacturePanier = panier.getPrixTotalPanier()+ fraisLivraisonPanierU;
		    		facturePanier.setMontantPaiementFacturePanier(montantPaiementFacturePanier);
		       
        // Vérifier si la commande, le produit et le client sont valides
        if ( facturePanier.getPanier() == null || facturePanier.getClient() == null) {
            throw new IllegalArgumentException("Les informations de la facture sont incomplètes. Impossible de l'enregistrer.");
        }
        
        // Enregistrer la facture en base de données
        return facturePanierDao.save(facturePanier);
    }
    
    public Double FraisLivraisonPanier(Panier panier) {
    	
    	int nbreProduitPanier= panier.getProduits().size();
    	   
	    Double fraisLivraisonPanierU = 1000.0;
	    
	    Double fraisLivraisonPanier = nbreProduitPanier*fraisLivraisonPanierU;
		 
    	return fraisLivraisonPanier;
    	
    }
    public Double MontantPaiementFacturePanier(Panier panier){
    	
    	  int nbreProduitPanier= panier.getProduits().size();
          
		    Double fraisLivraisonPanierU = 1000.0;
		    
		    Double fraisLivraisonPanier = nbreProduitPanier*fraisLivraisonPanierU;
		    		    
		    Double montantPaiementFacturePanier = panier.getPrixTotalPanier()+ fraisLivraisonPanier;
		    return montantPaiementFacturePanier;
    }
    
    
    
    
	public FacturePanier modifierFacturePanier(Long idFacturePanier, FacturePanier facturePanier) {
        Optional<FacturePanier> facturePanierOptional = facturePanierDao.findById(idFacturePanier);
        if (facturePanierOptional.isPresent()) {
            FacturePanier facturePanierExistant = facturePanierOptional.get();
            facturePanier.setClient(facturePanier.getClient());
            facturePanier.setMontantPaiementFacturePanier(facturePanier.getMontantPaiementFacturePanier());
            facturePanier.setDateLivraisonPanier(new java.sql.Date(System.currentTimeMillis()));
            facturePanier.setPanier(facturePanier.getPanier());
            
       
            return facturePanierDao.save(facturePanierExistant);
        } else {
            throw new RuntimeException("Facture non trouvée avec l'ID : " + idFacturePanier);
        }
    }
	
	
	
	public void deleteFacturePanier(Long idFacturePanier) {
		facturePanierDao.deleteById(idFacturePanier);
	}

	public void deleteAllFacturePanier() {
		facturePanierDao.deleteAll();
		
	}
	
	public void mettreAjourFacturePanier(FacturePanier facturePanier) {
	    facturePanierDao.save(facturePanier);
	}

	  
}
