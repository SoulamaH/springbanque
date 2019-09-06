/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

/**
 *
 * @author USER
 * 
 * domaine : Classe Retrait heritant de Opreration
 */
@Entity(name= "Retrait")
@Table(name = "RETRAIT")
@Getter
@Setter
@ToString
@NoArgsConstructor
@DiscriminatorValue("R")
public class Retrait extends Operation{
    
    //Propriete
    @Column(name = "libelle")
    private String libelle;

    @Column(name = "reference")
    private String reference;
    
    // constructeur
//    public Retrait(String libelle, String numOperation, Date dateOperation, Double montant, Compte compte) {
//        super(dateOperation, montant, compte);
//        this.libelle = libelle;
//        this.numOperation = numOperation;
//    }

    public Retrait(String libelle, String reference, Double montant, Compte compte) {
        super( montant, compte);
        this.libelle = libelle;
        this.reference = reference;
    }

   
 
}
