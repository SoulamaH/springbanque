/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.entities.Client;
import ci.spring.entities.Conseiller;
import ci.spring.repository.ClientRepository;
import ci.spring.repository.CompteRepository;
import ci.spring.service.IClientService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClientService redefinir les methodes de l'interface IClientService
 *
 * @author USER
 *
 */
@Service
public class ClientService implements IClientService {

    // Injection par interface
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompteService compteService;

    /**
     * Enregistre un objet(Client) si celui ci est différent de nul sinon null
     *
     * @param client
     * @return
     */
    @Override
    public Client create(Client client) {
        return this.clientRepository.save(client);
    }


    /**
     * Renvoie un élément par l'id
     *
     * @param id
     * @return
     */
    @Override
    public Client readOne(Long id) {
        return this.clientRepository.getOne(id);
    }

    /**
     * supprime l'élément par son id dans la BD
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        this.clientRepository.deleteById(id);
    }

    /**
     * retourne un élément Enregistré
     *
     * @param client
     * @return
     */
    @Override
    public Client update(Client client) {
        return this.clientRepository.save(client);
    }

    /**
     * retourne tous les éléments enregistrés
     *
     * @return
     */
    @Override
    public List<Client> readAll() {
        return this.clientRepository.findAll();
    }
    
    /**
     * retourne la liste des enregistrements fait par un conseiller
     * @param conseiller
     * @return 
     */
    @Override
    public List<Client> findByConseiller(Conseiller conseiller) {
        return this.clientRepository.findByConseiller(conseiller);
    }

    /**
     * retourne une liste de map de client et un entier pour le nombre de client
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllClientAndNbreCmpt(Conseiller conseiller) {

        List<Map<String, Object>> listMap = new LinkedList<>();
        Map<String, Object> map = null;

        //
        for (Client client: findByConseiller(conseiller)) {
            map = new HashMap<>();
            map.put("client", client);
            map.put("nbrCompte", compteService.getNbreCompteByClient(client));
            listMap.add(map);
        }
        return listMap;
    }
}
