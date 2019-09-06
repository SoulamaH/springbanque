/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.Conseiller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 * 
 * repository (represente le package dao) : l'interface ClientRepository contiendra des méthodes propres 
 * au traitement Client et elle herite de l'interface JpaRepository de Spring qui a des méthode déjàdeclarées
 * 
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    
    // retourne la liste des enregistrement par conseiller
    public List<Client> findByConseiller(Conseiller conseiller);
}
