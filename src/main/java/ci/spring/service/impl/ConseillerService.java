/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.entities.Conseiller;
import ci.spring.repository.ConseillerRepository;
import ci.spring.service.IConseillerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ConseillerService redefi les methode de IConseillerService
 *
 * @author USER
 */
@Service
public class ConseillerService implements IConseillerService {

    //injection par repository
    @Autowired
    ConseillerRepository conseillerRepository;

    /**
     * enregistrement d'un conseiller
     *
     * @param conseiller
     * @return
     */
    @Override
    public Conseiller create(Conseiller conseiller) {
        return this.conseillerRepository.save(conseiller);
    }

    /**
     * methode permettant de faire la lecture d'un élément
     *
     * @param id
     * @return
     */
    @Override
    public Conseiller readOne(Long id) {
        return this.conseillerRepository.getOne(id);
    }

    /**
     * methode permettent de modifier un conseiller
     *
     * @param conseiller
     * @return
     */
    @Override
    public Conseiller update(Conseiller conseiller) {
        return this.conseillerRepository.save(conseiller);
    }

    /**
     * methode permettant de supprimer un conseiller
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        this.conseillerRepository.deleteById(id);
    }

    /**
     * retourne la liste totale des conseillers
     *
     * @return
     */
    @Override
    public List<Conseiller> readAll() {
        return this.conseillerRepository.findAll();
    }

    /**
     * retourne les conseillers par UserName
     *
     * @param username
     * @return
     */
    @Override
    public Conseiller readOneByUserName(String userName) {
        return this.conseillerRepository.readOneByUsername(userName);
    }
    
}
