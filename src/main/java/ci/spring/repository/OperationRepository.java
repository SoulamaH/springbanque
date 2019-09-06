/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.CompteCourant;
import ci.spring.entities.Operation;
import ci.spring.entities.Virement;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author USER
 *
 * repository (represente le package dao) : l'interface OperationRepository
 * contiendra des méthodes propres au traitement du Versement, du retrait ou du
 * virementsur les comptes et elle herite de l'interface JpaRepository de Spring
 * qui a des méthode déjà declarées
 */
public interface OperationRepository extends JpaRepository<Operation, Long> {

    //Déclaration de méthodes
    
    //retourne la liste des numeros de l'operation = numOperation
    public Object findByNumOperation(Long numOperation); 


//    // pour la paggination
//    @Query("SELECT o FROM Operation o WHERE o.Compte.numeroCompte=:x, ORDER BY o.dateOperation DESC")
//    public Page<Operation>listOperation(@Param("x")String numeroCompte, Pageable pageable);
}
