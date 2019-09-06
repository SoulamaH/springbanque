/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.CompteCourant;
import ci.spring.repository.CompteCourantRepository;
import ci.spring.service.ICompteCourantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CompteCourantService redefinir les methodes de l'interface
 * ICompteCourantService
 *
 * @author USER
 */
@Service
public class CompteCourantService implements ICompteCourantService {

    // injection par interface
    @Autowired
    private CompteCourantRepository compteCourantRepository;

    @Autowired
    private NumeroCompteService numeroCompteService;

    private CompteCourant compteCourant;

    /**
     * enregistrementd'un objet(compteCourant)
     *
     * @param compteCourant
     * @return
     */
    @Override
    public CompteCourant create(CompteCourant compteCourant) {
        return this.compteCourantRepository.save(compteCourant);
    }

    /**
     * lecture d'un élément par son id
     *
     * @param id
     * @return
     */
    @Override
    public CompteCourant readOne(String id) {
        return this.compteCourantRepository.findByNumeroCompte(id).orElse(null);
    }

    /**
     * permet de faire la modification sur un objet
     *
     * @param compteCourant
     * @return
     */
    @Override
    public CompteCourant update(CompteCourant compteCourant) {
        return this.compteCourantRepository.save(compteCourant);
    }

    /**
     * Supprime un element par son id
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        this.compteCourantRepository.deleteById(id);
    }

    /**
     * retourne la liste de tous les enregistement
     *
     * @return
     */
    @Override
    public List<CompteCourant> readAll() {
        return this.compteCourantRepository.findAll();
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
        return compteCourantRepository.getOne(numeroCompte);
    }

    /**
     * retourne le solde
     *
     * @param soldeCompte
     * @return
     */
    @Override
    public Double readOneBySoldeCompte(String soldeCompte) {
        return compteCourantRepository.readOneBySoldeCompte(Double.valueOf(soldeCompte));
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
        this.compteCourant = (CompteCourant) ReadOneByNumeroCompte(compte);

        // condition sur le solde
        /* On verifie si le niveau du solde dans la BD est ">=" au montant recuperépar la vue
          mais d'abord on verifie si compte est different de null*/
        if (compteCourant != null) {
            if (compteCourant.getSoldeCompte() >= montant) {
                return false;
            }
        }
        return true;
    }

    /**
     *  methode retournant la liste des comptes courant en fonction du client
     *
     * @param client
     * @return
     */
    @Override
    public List<CompteCourant> readAllByClient(Client client) {
        return compteCourantRepository.findByClient(client);
    }


}
