package com.vente.application.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vente.application.entities.Client;
import com.vente.application.services.ClientService;

@RestController
@RequestMapping("/Clients")
public class ClientController {
    
	@Autowired
    private ClientService clientService;

    @GetMapping("/AllClients")
    public List<Client> getAllClients() {
   	 	return clientService.getAllClients(); 
    }

    @GetMapping("/RechercherClient/{idClient}")
    public Optional<Client> getClientById(Long idClient) {
        return clientService.getClientById(idClient);
    }

    @PostMapping("/creerClient")
    public Client creerClient(@RequestBody Client client) {
        return clientService.creerClient(client);
    }

    @PutMapping("/modifierClient/{idClient}")
    public Client modifierClient(@PathVariable Long idClient, @RequestBody Client client) {
        return clientService.modifierClient(idClient, client);
    }

    @DeleteMapping("/DeleteClient/{idClient}")
    public void deleteClient(@PathVariable Long idClient) {
        clientService.deleteClient(idClient);
    }
    @DeleteMapping("/DeleteAllClient")
    public void deleteAllClient() {
        clientService.deleteAllClient();
     }
}
