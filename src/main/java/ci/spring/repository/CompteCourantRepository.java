/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.CompteCourant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 *
 * repository (represente le package dao) : l'interface CompteCourantRepository
 * contiendra des méthodes propres au traitement du CompteCourant et elle herite
 * de l'interface JpaRepository de Spring qui a des méthode déjà declarées
 *
 */
@Repository
public interface CompteCourantRepository extends JpaRepository<CompteCourant, String> {

    // retourne le solde
    public Double readOneBySoldeCompte(Double solde);

    public Optional<CompteCourant> findByNumeroCompte(String numCpt); // methoe que jai commenté dou cest ce package qui doit etre importé a la place de operationRepo

    // retourne la liste des compte par  client
    public List<CompteCourant> findByClient(Client client); // methode que jai commenté se trouvant dans operRepository


}
