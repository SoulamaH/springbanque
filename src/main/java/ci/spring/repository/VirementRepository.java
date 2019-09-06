/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.Conseiller;
import ci.spring.entities.Virement;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 *
 * repository (represente le package dao) : l'interface VirementRepository
 * contiendra des méthodes propres au traitement du Virement et elle herite de
 * l'interface JpaRepository de Spring qui a des méthode déjà declarées
 */
@Repository
public interface VirementRepository extends JpaRepository<Virement, Long> {

    //Déclaration de méthodes
    
    //retourne la liste des numeros de l'operation = numOperation
    public Virement findByNumOperation(Long numOperation);

    // retourne la liste de reference et typeVirement
    public Optional<List<Virement>> findByReferenceAndTypeVirement(String reference, String typVirement);

    // retourne la liste des clients en passsant par le compte
    public List<Virement> findByCompteClient(Client c);

    // Retourne la liste compteclient et typeVirement
    public List<Virement> findByCompteClientAndTypeVirement(Client c, String typVirement);

    // la liste de virement par conseiller
    public List<Virement> findByCompteClientConseiller (Conseiller conseiller);

}
