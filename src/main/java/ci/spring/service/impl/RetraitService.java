/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.entities.Client;
import ci.spring.entities.Conseiller;
import ci.spring.entities.Retrait;
import ci.spring.repository.RetraitRepository;
import ci.spring.service.IClientService;
import ci.spring.service.ICompteService;
import ci.spring.service.INumeroCompteService;
import ci.spring.service.IRetraitService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * classe retraitService contient les traitement metiers liés au retrait
 *
 * @author User
 */
@Service
public class RetraitService implements IRetraitService {

    // injection par interfaces
    @Autowired
    private RetraitRepository repository;

    @Autowired
    private IClientService clientService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private  CompteCourantService courantService;

    @Autowired
    private  CompteEpargneService epargneService;

    @Autowired
    private INumeroCompteService numeroCompteService;

    @Autowired
    ICompteService compteService;


    // methode permettant de faire le retrait
    @Override
    public Retrait create(Retrait t) {

        if (t != null) {

            // verifi que le compte n'est pas null
            if (operationService.typeCompte(t.getCompte().getNumeroCompte()) != null){

                    // condition sur le solde
                    if (operationService.verifSolde(t.getCompte().getNumeroCompte(),t.getMontant())){

                        // condition sur retrait
                        if (operationService.updateSolde(t.getCompte().getNumeroCompte(), t.getMontant(), "retrait") ){

                            t.setReference(numeroCompteService.generatedNumeroCompte());
                            return repository.save(t);
                        }
                    }
            }
        }
        return null;
    }

    // liste totale des retraits
    @Override
    public List<Retrait> readAll() {
        return repository.findAll();
    }

    // retourne un element par le numOpretation
    @Override
    public Retrait readOne(Long numOperation) {

        if (numOperation > 0) {
            return repository.findByNumOperation(numOperation);
        }
        return null;

    }

    // modifier un retrait
    @Override
    public Retrait update(Retrait t) {

        if (t != null) {
            return repository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Retrait t) {

        if (t != null) {
            return repository.save(t) != null;
        }
        return false;

    }

    // effectuer un retrait
    @Override
    public String effectuerRetrait(String numerCpt, Double montant, Conseiller conseiller) {

        Retrait retrait = new Retrait();
        // condition sur le numeroCompte n'est pas vide et different de null et montant > 0
        if (!StringUtils.isEmpty(numerCpt) && montant > 0){

            if (operationService.typeCompte(numerCpt).equals("courant")){
                retrait.setCompte(courantService.ReadOneByNumeroCompte(numerCpt));

            }else  if (operationService.typeCompte(numerCpt).equals("epargne")){
                retrait.setCompte(epargneService.ReadOneByNumeroCompte(numerCpt));
            }
        }

        retrait.setMontant(montant); // affectation du montant
        retrait = creatRetrait(retrait,conseiller); // creation
        return retrait != null ? retrait.getReference() : null;
    }

    @Override
    public List<Retrait> readAllRetraitByClient(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            if (client != null) {
                return repository.findByCompteClient(client);
            }
        }
        return null;

    }

    /**
     * methode retournant la liste des retraits par conseiller
     *
     * @param conseiller
     * @return
     */
    @Override
    public List<Retrait> readAllByCompteClientConseiller(Conseiller conseiller) {
        return repository.findByCompteClientConseiller(conseiller);
    }

    /**
     * methode prmettant de  verifier que le compte appartient
     * au client du conseiller connecté
     *
     * @param retrait
     * @param conseiller
     * @return
     */
    @Override
    public Retrait creatRetrait(Retrait retrait, Conseiller conseiller) {

        // verifi que le compte appartient au client du client du conseiller connecté
        if (compteService.readAllByNumeroCompteAndClientConseiller(retrait.getCompte().getNumeroCompte(),conseiller) !=null){
            return  create(retrait);
        }
        return null;
    }


}
