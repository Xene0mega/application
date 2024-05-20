package com.vente.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vente.application.entities.Client;

public interface ClientDao  extends JpaRepository<Client, Long> {

}
