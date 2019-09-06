/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Operation;
import ci.spring.entities.Retrait;
import ci.spring.entities.Virement;

/**
 * Interface Operation elle contient des méthodes declarées qui sont propres aux
 * traitements metier des operations (Virement, Versement & Retrait)
 *
 * @author USER
 */
public interface IOperationService{
    
    // genere le numero de transaction
    public Long generatedNumTransc();
    
    // retourne le type de compte
    public String typeCompte(String numeroCpt);
    
    // verifie le solde
    public Boolean verifSolde(String cpt, Double montant);

    // mettre a jour le solde
    public Boolean updateSolde(String numeroCompte, Double montant, String typeOperation);
}
