package com.vente.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Client;

public interface ClientDao  extends JpaRepository<Client, Long> {
	
	Optional<Client> findByEmailClientAndMotDePasseClient(String emailClient, String motDePasseClient);
}
