/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Conseiller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 * 
 * repository (represente le package dao) : l'interface ConseillerRepository 
 * contiendra des méthodes propres au traitement du Conseiller et elle herite
 * de l'interface JpaRepository de Spring qui a des méthode déjà declarées
 * 
 */
@Repository
public interface ConseillerRepository extends JpaRepository<Conseiller, Long>{
    
    // Déclaration de Méthodes
    public Conseiller readOneByUsername(String userName);

}
