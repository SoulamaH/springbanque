/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Client;
import ci.spring.entities.Conseiller;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 *
 * Traitement metier propre au Client 
 * Declaration des méthodes à implementer
 * pour les classes qui en hériteront
 */
public interface IClientService {

    public Client create(Client client);
    
    public Client readOne(Long id);
    
    public Client update(Client client);
    
    public void delete(Long id);
    
    public List<Client>readAll();
    
    // retourne la liste des enregistrement par conseiller
    public List<Client> findByConseiller(Conseiller conseiller);

    // liste de map de client qui retournera un client et un entier
    // pour le nombre de comptes
    public List<Map<String, Object>> getAllClientAndNbreCmpt(Conseiller conseiller);
}
