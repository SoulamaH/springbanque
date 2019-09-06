/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.CompteEpargne;
import ci.spring.repository.CompteEpargneRepository;
import ci.spring.service.ICompteEpargneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CompteEpargnetService redefinir les methodes de l'interface
 * ICompteEpargneService
 *
 * @author USER
 */
@Service
public class CompteEpargneService implements ICompteEpargneService {

    // injection par interface
    @Autowired
    private CompteEpargneRepository compteEpargneRepository;

    @Autowired
    private NumeroCompteService numeroCompteService;

    private CompteEpargne compteEpargne;

    /**
     * enregistrementd'un objet(compteCourant)
     *
     * @return
     */
    @Override
    public CompteEpargne create(CompteEpargne compteEpargne) {
        // condition permettant de ne pas avoir deux comptes epargne
        if(!readAllByclient(compteEpargne.getClient()).isEmpty()){
            return null;
        }
        return this.compteEpargneRepository.save(compteEpargne);
    }

    /**
     * lecture d'un élément par son id
     *
     * @param id
     * @return
     */
    @Override
    public CompteEpargne readOne(String id) {
        return this.compteEpargneRepository.findByNumeroCompte(id).orElse(null);
    }

    /**
     * permet de faire la modification sur un objet
     *
     * @param compteEpargne
     * @return
     */
    @Override
    public CompteEpargne update(CompteEpargne compteEpargne) {
        return this.compteEpargneRepository.save(compteEpargne);
    }

    /**
     * Supprime un element par son id
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        this.compteEpargneRepository.deleteById(id);
    }

    /**
     * retourne la liste de tous les enregistement
     *
     * @return
     */
    @Override
    public List<CompteEpargne> readAll() {
        return this.compteEpargneRepository.findAll();
    }

    /**
     * verifie qu'il n'y est pas de doublon de numero de compte dans la BD
     *
     * @param numero
     * @return
     */
    @Override
    public Boolean verifNumeroCompte(String numero) {

//        if (readOne(numeroCompteService.generatedNumeroCompte()) != null) {
//            return true;
//        }
//        return false;

        /*code proposé par l'IDE idem que celui du haut*/
        return readOne(numero) != null;
    }

    /**
     * genère de nouveau numero de reference tant que celui ci existe dans la BD
     *
     * @return
     */
    @Override
    public String genererNumero() {
        String numeGenerer = numeroCompteService.generatedNumeroCompte();

        // verifie qu'il n y est pas de doublon
        while (verifNumeroCompte(numeGenerer)) {
            numeGenerer = numeroCompteService.generatedNumeroCompte();
        }
        return numeGenerer;
    }

    /**
     * lecture par numero de compte qui represante l'id
     *
     * @param numeroCompte
     * @return
     */
    @Override
    public Compte ReadOneByNumeroCompte(String numeroCompte) {
        return compteEpargneRepository.getOne(numeroCompte);
    }

    /**
     * retourne le solde
     *
     * @param compte
     * @return
     */
    @Override
    public Double readOneBySoldeCompte(String compte) {
        compteEpargne = compteEpargneRepository.getOne(compte);
        if(compteEpargne != null){
            return compteEpargne.getSoldeCompte();
        }
        return null;
    }

    /**
     * verifie le niveau de solde dans la BD au montant qui sera envoie par la
     * vue
     *
     * @param montant
     * @param compte
     * @return
     */
    @Override
    public Boolean verificationSolde(Double montant, String compte) {

        // Récuperation du compte par son numero
        this.compteEpargne = (CompteEpargne) ReadOneByNumeroCompte(compte);

        // condition sur le solde
        /* On verifie si le niveau du solde dans la BD est ">=" au montant recuperépar la vue
          mais d'abord on verifie si compte est different de null*/
        if (compteEpargne != null) {
            if (compteEpargne.getSoldeCompte() >= montant) {
                return false;
            }
        }
        return true;
    }

    /**
     * methode retournant la liste des comptes epargne en fonction du client
     *
     * @param client
     * @return
     */
    @Override
    public List<CompteEpargne> readAllByclient(Client client) {
        return compteEpargneRepository.findByClient(client);
    }


}
