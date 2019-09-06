    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.*;

    /**
 *
 * @author USER
 * 
 * domaine : classe Conseiller 
 */
@Entity(name = "Conseiller")
@Getter
@Setter
@Table(name = "conseiller")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Conseiller implements Serializable{

    // proprietes
    @Id
    @Column(name = "username")
    private String username;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "enabled")
    private Boolean enabled;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "contact")
    private String contact;
    
    
    @Column(name = "password")
    private String password;

    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();

    @Column(name = "datemodification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification = new Date();

    //
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER) /*CascadeTypeALL permet de faire un enregistrement*/
    @JoinTable(name = "conseiller_roles", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Roles> roles;

    // constructeur sans "id et Cilent"
    public Conseiller(String nom, String prenom, Boolean enabled, String email, String contact, String username, String password, Set<Roles> roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.prenom = prenom;
        this.enabled = enabled;
        this.contact = contact;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
   
    
}
