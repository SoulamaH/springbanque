/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.entities;

import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author USER
 * 
 * domaine : Classe CompteEpargne herite de Compte
 */
@Entity(name= "CompteEpargne")
@Table(name= "COMPTEEPARGNE")
@NoArgsConstructor
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte{


    //
    public CompteEpargne(String numeroCompte, Double soldeCompte, Client client) {
        super(numeroCompte, soldeCompte, client);
    }


}
