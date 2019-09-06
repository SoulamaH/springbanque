/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author USER
 * domaine : Classe Operation cette classe est abstraite contenant les proprietes de
 * l'operation sera implemntée par les classes Retrait, Versement et Virement
 *
 */

@Entity(name = "Operation")
@Table(name = "OPERATION")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_OP", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class Operation implements Serializable{
    
    //Proprietes
    @Id 
    @GeneratedValue
    private Long numOperation; // represente l'id


//    @Column(name = "numoperation")
//    private String numOperation;
    
    @Column(name = "montant")
    private Double montant;

    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();

    @Column(name = "datemodification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification = new Date();
    
    // liaison
    @ManyToOne
    @JoinColumn(name = "code_cpte") // colonne de jointure
    private Compte compte;

    public Operation( Double montant, Compte compte) {
        this.montant = montant;
        this.compte = compte;
    }

   
   
}
