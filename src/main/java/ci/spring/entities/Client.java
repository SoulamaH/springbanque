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

import lombok.*;

/**
 *
 * @author USER
 *
 * domaine : classe Client contient les propriétés du client
 */
@Entity(name = "Client")
@Table(name = "CLIENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client implements Serializable{

    // propriétés
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "tel")
    private String tel;

    @Column(name = "localite")
    private String localite;
    
    @Column(name = "mail")
    private String mail;

    @Column(name = "datecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();

    @Column(name = "datemodification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification = new Date();

   //    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
//    private Collection<Compte> comptes;
    
    //liaison du client et du conseiller
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "idConseiller")// nom de la colonne dans la BD
    private Conseiller conseiller; 
    
    // Constructeur sans id
    public Client(String nom, String prenom, String tel, String localite, String mail, Conseiller conseiller) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.localite = localite;
        this.mail = mail;
        this.conseiller = conseiller;
    }
}
