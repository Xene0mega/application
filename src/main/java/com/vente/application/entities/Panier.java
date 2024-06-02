package com.vente.application.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name ="panier")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPanier")
    private Long idPanier;
    
    @Column(name = "prixTotalPanier", nullable = false, length = 20)
    private double prixTotalPanier;
    
    @Column(name = "quantiteTotalPanier", nullable = false, length = 20)
    private int quantiteTotalProduitPanier;
    
    @Column(name = "dateCreationPanier", nullable = false)
    private Date dateCreationPanier;
  
      @OneToMany(mappedBy = "panier")
    private List<PanierProduit> produits;
      
     
}