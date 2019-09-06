/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Conseiller;
import ci.spring.entities.Virement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 *
 * Traitement metier propre au Virement 
 * Declaration des méthodes à implementer
 * dans les classes qui en hériteront
 */
public interface IVirementService {

    public Boolean retirer(Virement virement);
    
    public Boolean verser(Virement virement);

    // transaction entre compte
    public String virementCompteACompte(String cptDebit, String cptCredit, Double montant);

    // contrainte avant transaction
    public Boolean contrainteCompte(String cpt1, String cpt2);

    public Virement create(Virement t);

    public List<Virement> readAll();

    public Virement readOne(Long pk);

    public Virement update(Virement t);

    public Boolean delete(Virement t);
    
    // retourne la liste de virement viré par reference
    public List<Virement> readOneVirementVireParRef(String reference);
    
    // retourne la liste de virement recu par reference
    public List<Virement> readOneVirementRecuParRef(String reference);
    
    // 
    public List<Virement> readAllVirementByClientAndVerser(Long idClient);
    
    public List<Virement> readAllVirementByClientAndRetirer(Long idClient);//

    // la liste des virements
    public List<Map<String, Object>> listVirement(Conseiller conseiller);

   // retourne la liste des virements par client
    public  List<Virement> reallVirementsByConseiller(Conseiller conseiller);
    
}
