package com.vente.application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient")
    private Long idClient;

    @Column(name = "nomClient", nullable = false, length = 30)
    private String nomClient;

    @Column(name = "prenomClient", nullable = false, length = 30)
    private String prenomClient;

    @Column(name = "emailClient", nullable = false,length = 50, unique = true)
    private String emailClient;

    @Column(name = "adresseClient",length = 50, nullable = false)
    private String adresseClient;

    @Column(name = "numeroTelephoneClient",length = 15, nullable = false)
    private String numeroTelephoneClient;
    
    
}
  
