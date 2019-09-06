/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.CompteEpargne;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 *
 * repository (represente le package dao) : l'interface CompteEpargneRepository
 * contiendra des méthodes propres au traitement du CompteEpargne et elle herite
 * de l'interface JpaRepository de Spring qui a des méthode déjà declarées
 *
 */
@Repository
public interface CompteEpargneRepository extends JpaRepository<CompteEpargne, String> {

    // Déclarations de Methodes
    /*retourne le solde*/
    public Double readOneBySoldeCompte(Double solde);

    //retourne la liste totale des numeros de compte
    public Optional<CompteEpargne> findByNumeroCompte(String numCpt);

    // la liste des clients en fonction du compte
    public List<CompteEpargne> findByClient(Client client);


}
