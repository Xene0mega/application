package com.vente.application.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "facture")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFacture")
    private Long idFacture;

    @OneToOne
    @JoinColumn(name = "COMMANDE_ID", nullable = false)
    private Commande commande;

    @OneToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "idClient", nullable=false)
    private Client client;

    @OneToOne
    @JoinColumn(name = "PRODUIT_ID", nullable = false)
    private Produit produit;

    @Column(name = "montantPaiement", nullable = false)
    private double montantPaiementFacture;

}