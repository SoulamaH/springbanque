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
 * domaine : classe Virement heritant de Operation
 */
@Entity(name = "Virement")
@Table(name = "VIREMENT")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("I")
public class Virement extends Operation {

    // Proprietes
    @Column(name = "libelle")
    private String libelle;
    
    
    @Column(name = "reference")
    private String reference;
    
    @Column(name = "typevirement")
    private String typeVirement;

    //constructeur
    public Virement(String libelle, String reference, String typeVirement, Double montant, Compte compte) {
        super( montant, compte);
        this.libelle = libelle;
        this.reference = reference;
        this.typeVirement = typeVirement;
    }    
    
}
