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

@Table(name = "produit")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProduit")
	private Long idProduit;
   
	@Column(name = "nomProduit", nullable = false, length = 100)
	private String nomProduit;
	
	@Column(name = "descriptionProduit", nullable = false, length = 1000)
	private String descriptionProduit;
	
	@Column(name = "imageProduit", nullable = false, unique=true)
	private String imageProduit;

	@Column(name = "prixProduit", nullable = false)
	private double prixProduit;

	@Column(name = "quantiteEnStock_Produit", nullable = false)
	private int quantiteEnStockProduit;
	
    @ManyToOne
	@JoinColumn(name ="categorie_id", nullable=false, referencedColumnName="idCategorie")
	private Categorie categorie;

}
