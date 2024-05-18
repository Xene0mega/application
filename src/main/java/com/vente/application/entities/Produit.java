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
@Table(name = "produit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idproduit")
	private Long idproduit;
   
	@Column(name = "nom", nullable = false, length = 100)
	private String nomProduit;
	
	@Column(name = "description", nullable = false, length = 1000)
	private String descriptionProduit;
	
	@Column(name = "image", nullable = false, unique=true)
	private String imageProduit;

	@Column(name = "prix", nullable = false)
	private double prixProduit;

	@Column(name = "quantite_en_stock", nullable = false)
	private int quantiteEnStockProduit;
	
    @ManyToOne
	@JoinColumn(name ="categorie_id", referencedColumnName="id_categorie")
	private Categorie categorie;

}
