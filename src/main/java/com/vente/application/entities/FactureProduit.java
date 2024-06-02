package com.vente.application.entities;


import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "factureProduit")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FactureProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactureProduit")
    private Long idFactureProduit;
    
    @Column(name = "montantPaiementFactureProduit", nullable = false)
    private Double montantPaiementFactureProduit;

    @OneToOne
    @JoinColumn(name = "COMMANDE_ID", referencedColumnName="idCommande", nullable = false)
    private Commande commande;

    @OneToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "idClient", nullable=false)
    private Client client;

    @OneToOne
    @JoinColumn(name = "PRODUIT_ID", referencedColumnName="idProduit",  nullable = false)
    private Produit produit;
    
    @ManyToOne
	 @JoinColumn(name = "CATEGORIE_ID", referencedColumnName = "idCategorie")
	 private Categorie categorie;
	

}