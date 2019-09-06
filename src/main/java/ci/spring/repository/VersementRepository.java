/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.Conseiller;
import ci.spring.entities.Versement;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 *
 * repository (represente le package dao) : l'interface VersementRepository
 * contiendra des méthodes propres au traitement du Versement sur les comptes et
 * elle herite de l'interface JpaRepository de Spring qui a des méthode déjà
 * declarées
 */
@Repository
public interface VersementRepository extends JpaRepository<Versement, Long> {

    //Déclaration de méthodes
    
    // retourne la liste des clients en passsant par le compte
    public List<Versement> findByCompteClient(Client c);

    //retourne la liste des numeros de l'operation = numOperation
    public Versement findByNumOperation(Long numOperation);

    // retourne la liste des comptes
    public List<Versement> findByCompte(Compte compte);

    // retourne la lste des versements par conseiller
    public List<Versement>findByCompteClientConseiller(Conseiller conseiller);


}
