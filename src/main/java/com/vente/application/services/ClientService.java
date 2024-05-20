package com.vente.application.services;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vente.application.entities.Client;
import com.vente.application.repository.ClientDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

   @Autowired
   private ClientDao clientDao;
   
   
   public List<Client>getAllClients(){
		
		return clientDao.findAll();
	}
	
	public Optional<Client> getClientById(Long idClient){
		
		return clientDao.findById(idClient);
	}
	
	public Client creerClient(Client client) {
		return clientDao.save(client);
	}
	
	public Client modifierClient( Long idClient, Client client) {
		Optional <Client> clientExistant = clientDao.findById(idClient);
		if(clientExistant.isPresent()) {
			Client newClient = clientExistant.get();
			newClient.setNomClient(client.getNomClient());
			newClient.setPrenomClient(client.getPrenomClient());
			newClient.setEmailClient(client.getEmailClient());
			newClient.setAdresseClient(client.getAdresseClient());
			newClient.setNumeroTelephoneClient(client.getNumeroTelephoneClient());
			
			return clientDao.save(newClient);
		}else {
		return null;
			
		}
		
	}
	public void deleteClient(Long idClient) {
		clientDao.deleteById(idClient);
	}
	public void deleteAllClient() {
		clientDao.deleteAll();
	}
}
