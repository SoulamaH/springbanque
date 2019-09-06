/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.CompteEpargne;
import java.util.List;

/**
 *interface Service : zone de declaration des méthodes du traitement metiers
 * propre au service Compte
 * 
 * @author USER
 */
public interface ICompteEpargneService {
    
     /**
     * methode CRUD
     * @param compteEpargne
     * @return 
     */
    public CompteEpargne create(CompteEpargne compteEpargne);
    
    public CompteEpargne readOne(String id);
    
    public CompteEpargne update(CompteEpargne compteEpargne);
    
    public void delete(String id);
    
    public List<CompteEpargne>readAll();
    
    //methode de verification de l'existance du numero de compte dans la BD
    public Boolean verifNumeroCompte(String numeroCompte);

    // methode de generation du numero de compte
    public String genererNumero();

    //methode 
    public Compte ReadOneByNumeroCompte(String numeroCompte);

    // retourne le solde du compte
    public Double readOneBySoldeCompte(String compte);

    // verifie le solde
    public Boolean verificationSolde(Double montant, String compte);

    // retourner liste des comptes en fonction du client
    public List<CompteEpargne> readAllByclient(Client client);
}
