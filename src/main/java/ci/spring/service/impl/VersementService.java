/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.entities.Client;
import ci.spring.entities.Conseiller;
import ci.spring.entities.Versement;
import ci.spring.repository.VersementRepository;
import ci.spring.service.ICompteCourantService;
import ci.spring.service.IVersementService;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * versementService contenant les traitements metires propres au versement
 *
 * @author User
 */
@Service
public class VersementService implements IVersementService{
    
    // injections par interface
    @Autowired
    private VersementRepository versementRepository;

    @Autowired
    private NumeroCompteService numeroCompteService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ICompteCourantService courantService;

    @Autowired
    private OperationService operationService;

    @Override
    public List<Versement> readAllVersementByClient(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            if(client!=null){
                return versementRepository.findByCompteClient(client);
            }
        }
        return null;

    }

    @Override
    public List<Versement> readAllVersementParCompte(String compte) {
//
//        if(!"".equals(compte)){
//            return versementRepository.findByCompteAndStatut(compte, Boolean.TRUE)
//        }

        return null;

    }

    // creer un versement
    @Override
    public Versement create(Versement t) {

        if (t != null) {
            // condition le compte
            if (operationService.typeCompte(t.getCompte().getNumeroCompte())!= null){
                // condition sur le versement
                if (operationService.updateSolde(t.getCompte().getNumeroCompte(),t.getMontant(),"versement")){

                    // afectation de la reference
                    t.setReference(numeroCompteService.generatedNumeroCompte());
                    return  versementRepository.save(t);// enregistrement dans la BD
                }
            }
            return versementRepository.save(t);
        }
        return null;
    }

    @Override
    public List<Versement> readAll() {

        return versementRepository.findAll();

    }

    @Override
    public Versement readOne(Long pk) {

        if (pk > 0) {
            return versementRepository.findByNumOperation(pk);
        }
        return null;

    }

    @Override
    public Versement update(Versement t) {

        if (t != null) {
            return versementRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Versement t) {

        if (t != null) {
            return versementRepository.save(t) != null;
        }
        return false;

    }

    // methode permettant d'effectuer un versement
    @Override
    public String effectuerVersement(String numeroCmpte,Double montant) {

        // declaration instantion
        Versement versement = new Versement();

        // condition sur le numeroCompte entré n'est pas vide et different de null
        if (!StringUtils.isEmpty(numeroCmpte)){

            // condition sur le type du compte renseigné "courant" ou "epargne"
            if (operationService.typeCompte(numeroCmpte).equals("courant")){
                versement.setCompte(courantService.ReadOneByNumeroCompte(numeroCmpte));
            }else if (operationService.typeCompte(numeroCmpte).equals("epargne")){
                versement.setCompte(courantService.ReadOneByNumeroCompte(numeroCmpte));
            }
        }

        versement.setMontant(montant); // affectation du montant renseigné depuis la vue
        versement = create(versement); // creation du versement
        return versement != null ? versement.getReference() : null;
    }

    @Override
    public List<Map<String, Object>> listVersement() {
        return null;
    }

    @Override
    public List<Versement> readAllByCompteClientConseiller(Conseiller conseiller) {
        return versementRepository.findByCompteClientConseiller(conseiller);
    }

}
