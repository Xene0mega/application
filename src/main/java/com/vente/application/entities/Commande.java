package com.vente.application.entities;

import java.sql.Date;

import jakarta.persistence.CascadeType;
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


@Table(name = "commande")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
	
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idCommande")
	private Long idCommande;
	
	@Column(name = "dateCommande", nullable = false, length = 20)
	private Date dateCommande;
	
	@Column(name = "dateLivraisonCommande", nullable = false, length = 20)
	private Date dateLivraison;
	
	@Column(name = "prixTotalCommande", nullable = false, length = 20)
	private Double prixTotal;
	
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", referencedColumnName = "idClient")
	private Client client;
	
	@OneToOne
	@JoinColumn(name ="PRODUIT_ID", referencedColumnName = "idProduit")
	private Produit produit;
	
	 @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
	 private Paiement paiement;
}