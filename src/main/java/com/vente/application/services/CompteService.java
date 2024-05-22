package com.vente.application.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vente.application.entities.Client;
import com.vente.application.entities.Compte;
import com.vente.application.repository.ClientDao;
import com.vente.application.repository.CompteDao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompteService {

    private final CompteDao compteDao;

    
    private final ClientDao clientDao;
    
    
 public List<Compte>getAllComptes(){
		
		return compteDao.findAll();
	}
	

    public Optional<Compte> getCompteByClientId(Long idClient) {
        Optional<Client> client = clientDao.findById(idClient);
        
        if (client.isPresent()) {
        	
            return compteDao.findById(idClient);
        } else {
        	
            throw new EntityNotFoundException("Client non trouvé avec l'ID : " + idClient);
        }
    }


    public Compte creerCompte(Client client) {
    	//enregistre client
    	clientDao.save(client);
    	
    	//creer compte et associer client
    	Compte compte = new Compte();
    	compte.setClient(client);
    	
    	//enregistre le compte
    	compteDao.save(compte);

    	// Marquer le compte comme activé
        compte.setStatutCompte(true); 
        
        
        return compteDao.save(compte);
    }

    public Compte modifierCompte(Long idCompte, Compte compte) {
        Optional<Compte> compteExistant = compteDao.findById(idCompte);
        if (compteExistant.isPresent()) {
            Compte newCompte = compteExistant.get();
            
            newCompte.setStatutCompte(compte.isStatutCompte());
            return compteDao.save( newCompte);
        } else {
            throw new EntityNotFoundException("Compte non trouvé avec l'ID : " + idCompte);
        }
    }

    public void deleteCompte(Long idCompte) {
        compteDao.deleteById(idCompte);
    }
    public void deleteAllCompte() {
        compteDao.deleteAll();
    }

    public void activerCompte(Long idCompte) {
    	
        Optional<Compte> compteExistant = compteDao.findById(idCompte);
        
        if (compteExistant.isPresent()) {
        	
            Compte compte = compteExistant.get();
            
            compte.setStatutCompte(true);
            
            compteDao.save(compte);
        } else {
            throw new EntityNotFoundException("Compte non trouvé avec l'ID : " + idCompte);
        }
    }

    public void desactiverCompte(Long idCompte) {
    	
        Optional<Compte> compteExistant = compteDao.findById(idCompte);
        
        if (compteExistant.isPresent()) {
            Compte compte = compteExistant.get();
            compte.setStatutCompte(false);
            
            compteDao.save(compte);
        } else {
            throw new EntityNotFoundException("Compte non trouvé avec l'ID : " + idCompte);
        }
    }
    
}