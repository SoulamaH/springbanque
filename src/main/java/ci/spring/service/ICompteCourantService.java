/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.CompteCourant;
import java.util.List;

/**
 *
 * @author USER
 *
 * interface Service : zone de declaration des méthodes du traitement metiers
 * propre au service Compte
 *
 */
public interface ICompteCourantService  {
    /**
     * methode CRUD
     * @param compteCourant
     * @return 
     */
    public CompteCourant create(CompteCourant compteCourant);
    
    public CompteCourant readOne(String id);
    
    public CompteCourant update(CompteCourant compteCourant);
    
    public void delete(String id);
    
    public List<CompteCourant>readAll();
    
    //methode de verification de l'existance du numero de compte dans la BD
    public Boolean verifNumeroCompte(String numero);

    // methode de generation du numero de compte
    public String genererNumero();

    //methode 
    public Compte ReadOneByNumeroCompte(String numeroCompte);

    // retourne le solde du compte
    public Double readOneBySoldeCompte(String soldeCompte);

    // verifie le solde
    public Boolean verificationSolde(Double montant, String compte);

    // liste des compte en fonction du client
    public List<CompteCourant> readAllByClient(Client client);
}
