/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author USER
 *
 * domaine : Classe Compte cette classe est abstraite contenant les proprietes du
 * compte elle sera implemntée par les classes CompteCourant et CompteEpargne
 *
 */
@Entity(name= "Compte")
@Table(name= "COMPTE")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2)
public abstract class Compte implements Serializable {

    //Propriétés
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) pas besoin de ca pour "id" en string
    @Column(name = "numerocompte")
    private String numeroCompte; //represente "id"

    @Column(name = "soldecompte")
    private Double soldeCompte;

    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();

    @Column(name = "datemodification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification = new Date();

    // liason
    @ManyToOne
    @JoinColumn(name = "code_cli") // nom de la colonne de jointure
    private Client client;

    @OneToMany(mappedBy = "compte")
    private Collection<Operation> Operation; 


    public Compte(Double soldeCompte, Client client) {
        this.soldeCompte = soldeCompte;
        this.client = client;
    }

    // constructeur sans les deux dates
    public Compte(String numeroCompte, Double soldeCompte, Client client) {
        this.numeroCompte = numeroCompte;
        this.soldeCompte = soldeCompte;
        this.client = client;
    }


}
