package com.vente.application.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "categorie")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Categorie {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="idCategorie")
		private Long idCategorie;
		
		@Column(name="nomCategorie", nullable=false, length=20, unique=true)
		String nomCategorie;
		
		@Column(name="imageCategorie", nullable=false, unique=true)
		String imageCategorie;
		
		@OneToMany(mappedBy = "categorie")
		private List<Produit> produits;

	
}



