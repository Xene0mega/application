package com.vente.application.entities;


import java.sql.Date; 

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

@Table(name = "facturePanier")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturePanier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFacturePanier")
    private Long idFacturePanier;
    
    @Column(name = "montantPaiementFacturePanier", nullable = false)
    private double montantPaiementFacturePanier;

    @OneToOne
    @JoinColumn(name = "PANIER_ID", referencedColumnName="idPanier", nullable = false)
    private Panier panier;
    
    @OneToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "idClient", nullable=false)
    private Client client;

    @Column(name = "dateFacturePanier", nullable = false)
    private Date dateFacturePanier;
    
    
    @Column(name = "dateLivraisonPanier", nullable = false)
    private Date dateLivraisonPanier;
    
    @Column(name = "fraisLivraisonPanier", nullable = false)
    private Double fraisLivraisonPanier;
}
