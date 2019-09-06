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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author USER
 * 
 * domaine : classe Versement heritant de Opereation
 */
@Entity(name = "Versement")
@Table(name = "VERSEMENT")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("V")
public class Versement extends Operation {

    // Proprietes
    @Column(name = "libelle")
    private String libelle;
    
    
    @Column(name = "reference")
    private String reference;
    
    // constructeur
//    public Versement(String libelle, String numOperation, Date dateOperation, Double montant, Compte compte) {
//        super(dateOperation, montant, compte);
//        this.libelle = libelle;
//        this.numOperation = numOperation;
//    }

    public Versement(String libelle, String reference, Double montant, Compte compte) {
        super( montant, compte);
        this.libelle = libelle;
        this.reference = reference;
    }

   
    
}
