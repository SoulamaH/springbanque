/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.entities;

import java.util.Collection;
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
 *domaine : classe CompteCourant heritant de Compte
 */
@Entity(name = "CompteCourant")
@Table(name = "COMPTECOURANT")
@NoArgsConstructor
@DiscriminatorValue("CC")
public class CompteCourant extends Compte {

    
    // Constructeur
    public CompteCourant(String numeroCompte, Double soldeCompte, Client client) {
        super(numeroCompte, soldeCompte, client);
    }
}
