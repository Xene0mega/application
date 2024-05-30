package com.vente.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vente.application.entities.Panier;
import com.vente.application.entities.PanierProduit;
import com.vente.application.entities.Produit;
import com.vente.application.repository.PanierDao;
import com.vente.application.repository.PanierProduitDao;
import com.vente.application.repository.ProduitDao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PanierService {

	@Autowired
    private PanierDao panierDao;
	
	@Autowired
	private PanierProduitDao panierProduitDao;
	
	
	@Autowired
	private ProduitDao produitDao;

 
    public List<Panier> getAllPaniers() {
        return panierDao.findAll();
    }

    public Optional<Panier> getPanierById(Long idPanier) {
        return panierDao.findById(idPanier);
    }

    public Panier creerPanier(Panier panier) {
        return panierDao.save(panier);
    }

    public Panier modifierPanier(Long idPanier, Panier panier) {
    	
        Optional<Panier> panierOptional = panierDao.findById(idPanier);
        
        if (panierOptional.isPresent()) {
            Panier panierExistant = panierOptional.get();
           
            panierExistant.setPrixTotalPanier(panier.getPrixTotalPanier());
            panierExistant.setQuantiteTotalProduitPanier(panier.getQuantiteTotalProduitPanier());
            panierExistant.setDateCreationPanier(new java.sql.Date(System.currentTimeMillis()));
            panierExistant.setProduits(panier.getProduits());
            
            return panierDao.save(panierExistant);
        } else {
            throw new EntityNotFoundException("Panier non trouvé avec l'ID : " + idPanier);
        }
    }

    public void deletePanier(Long idPanier) {
        panierDao.deleteById(idPanier);
    }
    public void deleteAllPanier() {
        panierDao.deleteAll();
    }

    public boolean ajouterProduitAuPanier(Long idProduit, Long idCategorie) {
        try {
            // Récupérer le panier actuel de l'utilisateur (à implémenter selon votre logique)
            Panier panier = getCurrentUserPanier();

            // Si le panier est nouveau (transient), il doit être sauvegardé d'abord
            if (panier.getIdPanier() == null) {
                panier = panierDao.save(panier);
            }

            // Récupérer le produit
            Produit produit = produitDao.findById(idProduit)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé"));

            // Créer une nouvelle entrée PanierProduit
            PanierProduit panierProduit = new PanierProduit();
            panierProduit.setPanier(panier);
            panierProduit.setProduit(produit);
            panierProduit.setIdCategorie(idCategorie);
            panierProduit.setQuantiteProduitEnStock(1); // Assigner une valeur initiale


            // Sauvegarder l'entrée PanierProduit
            panierProduitDao.save(panierProduit);

            // Mettre à jour les totaux du panier
            panier.setQuantiteTotalProduitPanier(panier.getQuantiteTotalProduitPanier() + 1);
            panier.setPrixTotalPanier(panier.getPrixTotalPanier() + produit.getPrixProduit());

            // Sauvegarder le panier mis à jour
            panierDao.save(panier);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Panier getCurrentUserPanier() {
        Optional<Panier> optionalPanier = panierDao.findById(1L); 
        
        if (optionalPanier.isPresent()) {
            return optionalPanier.get();
        } else {
            Panier newPanier = new Panier();
            newPanier.setDateCreationPanier(new java.sql.Date(System.currentTimeMillis()));
            newPanier.setPrixTotalPanier(0.0);
            newPanier.setQuantiteTotalProduitPanier(0);
            return newPanier;
        }
    }
    
   

    /*public void supprimerProduitDuPanier(Panier panier, Produit produit) {
        List<PanierProduit> panierProduits = panierProduitService.getAllPanierProduits();
        for (PanierProduit panierProduit : panierProduits) {
            if (panierProduit.getPanier().equals(panier) && panierProduit.getProduit().equals(produit)) {
                panierProduitService.deletePanierProduit(panierProduit.getIdPanierProduit());
                panier.setPrixTotalPanier(panier.getPrixTotalPanier() - produit.getPrixProduit());
                panier.setQuantiteTotalProduitPanier(panier.getQuantiteTotalProduitPanier() - 1);
                enregistrerPanier(panier);
                break;
            }
        }
}*/
}