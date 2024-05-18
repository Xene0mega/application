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

@Table(name = "catalogue")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Categorie {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="idcategorie")
		private Long idcategorie;
		
		@Column(name="image", nullable=false, length=20, unique=true)
		String imageCategorie;
		
		@Column(name="nom", nullable=false, length=20, unique=true)
		String nomCategorie;
		
		@OneToMany(mappedBy = "catalogue")
		private List<Produit> produits;

	
}



