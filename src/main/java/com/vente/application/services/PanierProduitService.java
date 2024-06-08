package com.vente.application.services;

import com.vente.application.entities.Panier;
import com.vente.application.entities.PanierProduit;
import com.vente.application.repository.PanierDao;
import com.vente.application.repository.PanierProduitDao;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PanierProduitService {
	
	@Autowired
    private  PanierProduitDao panierProduitDao;
	
	@Autowired
    private  PanierDao panierDao;

    
    public List<PanierProduit> getAllPanierProduits() {
        return panierProduitDao.findAll();
    }

    public Optional<PanierProduit> getPanierProduitById(Long idPanierProduit) {
        return panierProduitDao.findById(idPanierProduit);
    }

    public PanierProduit enregistrerPanierProduit(PanierProduit panierProduit) {
    	
        return panierProduitDao.save(panierProduit);
    }

    public void deletePanierProduit(Long idPanierProduit) {
    	
        PanierProduit panierProduit = panierProduitDao.findById(idPanierProduit).orElseThrow(() -> new RuntimeException("PanierProduit non trouvé"));
        panierProduitDao.deleteById(idPanierProduit);
        // Mettre à jour le prix et la quantité totale du panier
        Panier panier = panierDao.findById(panierProduit.getPanier().getIdPanier()).orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        
        panier.setPrixTotalPanier(panier.getPrixTotalPanier() - (panierProduit.getProduit().getPrixProduit() * panierProduit.getQuantiteProduitEnStockPanier()));
        panier.setQuantiteTotalProduitPanier(panier.getQuantiteTotalProduitPanier() - panierProduit.getQuantiteProduitEnStockPanier());
        panierDao.save(panier);
    }
    public void deleteAllPanierProduit() {
    	panierProduitDao.deleteAll();
    }
}

