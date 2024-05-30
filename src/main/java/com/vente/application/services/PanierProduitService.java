package com.vente.application.services;

import com.vente.application.entities.PanierProduit;
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
        panierProduitDao.deleteById(idPanierProduit);
    }
    public void deleteAllPanierProduit() {
    	panierProduitDao.deleteAll();
    }
}
