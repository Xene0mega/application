package com.vente.application.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "paiement")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Paiement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "idPaiement")
	private Long idPaiement;

	 
	@ManyToOne
	@JoinColumn(name ="CLIENT_ID", referencedColumnName = "idclient")
	private Client client;
	
    	
	@Column(name = "montantPaiement", nullable = false, length = 20)
	private double montantPaiement;
	
	@OneToOne
    @JoinColumn(name = "COMMANDE_ID", referencedColumnName = "idCommande")
    private Commande commande;
	
}

