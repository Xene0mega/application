package com.vente.application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "panier_produit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PanierProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPanierProduit")
    private Long idPanierProduit;
    
    @ManyToOne
    @JoinColumn(name = "PANIER_ID", nullable=false, referencedColumnName="idPanier")
    private Panier panier;
    
    @ManyToOne
    @JoinColumn(name = "PRODUIT_ID", nullable=false, referencedColumnName="idProduit")
    private Produit produit;
    
    @Column(name = "CATEGORIE_ID", nullable=false)
    private Long idCategorie;
    
    @Column(name = "quantiteProduitEnStock", nullable = false)
    private int quantiteProduitEnStock;
    
}
