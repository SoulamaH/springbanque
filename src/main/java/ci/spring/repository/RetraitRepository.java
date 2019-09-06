/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.Conseiller;
import ci.spring.entities.Retrait;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 *
 * repository (represente le package dao) : l'interface RetraiRepository
 * contiendra des méthodes propres au traitement du Retrait sur un Compte et
 * elle herite de l'interface JpaRepository de Spring qui a des méthode déjà
 * declarées
 */
@Repository
public interface RetraitRepository extends JpaRepository<Retrait, Long> {

    //Déclaration des méthodes
    
    //retourne la liste des numeros de l'operation = numOperation
    public Retrait findByNumOperation(Long numOperation);

    // retourne la liste des clients en passsant par le compte
    public List<Retrait> findByCompteClient(Client c);

    // retourne la liste par conseiller
    public List<Retrait> findByCompteClientConseiller(Conseiller conseiller);
}
