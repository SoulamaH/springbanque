/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Conseiller;
import ci.spring.entities.Versement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 * 
 * Traitement metier propre au Versement 
 * Declaration des méthodes à implementer
 * dans les classes qui en hériteront
 */
public interface IVersementService {

    // liste de versement par client
    public List<Versement> readAllVersementByClient(Long idClient);

    // versement par compte
    public List<Versement> readAllVersementParCompte(String compte);

    public Versement create(Versement t);

    public List<Versement> readAll();

    public Versement readOne(Long pk);

    public Versement update(Versement t);

    public Boolean delete(Versement t);

    // effectuer un versement
    public String effectuerVersement(String numeroCmpte, Double montant);

    // la liste des versement
    public List<Map<String, Object>> listVersement();

    // retourne la liste des versements par conseiller
    public List<Versement> readAllByCompteClientConseiller(Conseiller conseiller);

    
}
