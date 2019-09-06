/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Conseiller;
import ci.spring.entities.Retrait;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 * 
 * Traitement metier propre au Retrait 
 * Declaration des méthodes à implementer
 * dans les classes qui en hériteront
 */
public interface IRetraitService {
    
     public Retrait create(Retrait t);

    public List<Retrait> readAll();

    public Retrait readOne(Long numOperation);

    public Retrait update(Retrait t);

    public Boolean delete(Retrait t);

    // faire un retrait
    public String effectuerRetrait (String numerCpt, Double montant, Conseiller conseiller);
    
    public List<Retrait> readAllRetraitByClient(Long idClient);// liste de retrait par client

    // retourne la liste des retraits par conseiller
    public List<Retrait>readAllByCompteClientConseiller(Conseiller conseiller);

    // verifi que le compte appartient au client du conseiller connecté
    public Retrait creatRetrait (Retrait retrait, Conseiller conseiller);
}
