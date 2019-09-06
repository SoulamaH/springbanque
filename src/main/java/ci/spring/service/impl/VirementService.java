/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.configuration.ConstatnteConfigs;
import ci.spring.entities.*;
import ci.spring.repository.VirementRepository;
import ci.spring.resource.VirementController;
import ci.spring.service.IClientService;
import ci.spring.service.ICompteCourantService;
import ci.spring.service.ICompteEpargneService;
import ci.spring.service.INumeroCompteService;
import ci.spring.service.IOperationService;
import ci.spring.service.IVirementService;

import java.text.SimpleDateFormat;
import java.util.*;

import javafx.scene.input.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 *
 * @author willi
 */
@Service
public class VirementService implements IVirementService{
    
     //les proprietes
    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private ICompteCourantService courantService;

    @Autowired
    private ICompteEpargneService epargneService;

    @Autowired
    private IOperationService operationService;

    @Autowired
    private INumeroCompteService numeroCompteService;
    
    @Autowired
    private IClientService clientService;

    private final Logger logger = LoggerFactory.getLogger(VirementService.class);


    private  HttpSession session;

    // constructeur
    public VirementService(HttpSession session) {
        this.session = session;
    }

    @Override
    public Boolean retirer(Virement virement) {

        if (virement != null) {
            virement.setTypeVirement("RETIRER");
            return create(virement) != null;
        }

        return false;
    }

    @Override
    public String virementCompteACompte(String cptDebit, String cptCredit, Double montant) {

        if (!"".equals(cptDebit) && !"".equals(cptCredit) && montant > 0) {

            if (contrainteCompte(cptDebit, cptCredit)){

                String reference = numeroCompteService.generatedNumeroCompte();
                Virement virementDebit = null;
                Virement virementCredit;

                //creation de l'objet virementdebiter
                if (courantService.verifNumeroCompte(cptDebit)) {
                    CompteCourant courant = courantService.readOne(cptDebit);
                    virementDebit = new Virement();
                    virementDebit.setCompte(courant);
                    //update du compte courant
                    courant.setSoldeCompte(courant.getSoldeCompte() - montant);
                    courantService.update(courant);
                } else {
                    if (epargneService.verifNumeroCompte(cptDebit)){
                        CompteEpargne epargne = epargneService.readOne(cptDebit);
                        virementDebit = new Virement();
                        virementDebit.setCompte(epargne);
                        //update du compte epargne
                        epargne.setSoldeCompte(epargne.getSoldeCompte() - montant);
                        epargneService.update(epargne);
                    }
                }

                //creation de l'objet virementCrediter
                if (courantService.verifNumeroCompte(cptCredit)) {
                    CompteCourant courant = courantService.readOne(cptCredit);
                    virementCredit = new Virement();
                    virementCredit.setCompte(courant);
                    //update du compte courant
                    courant.setSoldeCompte(courant.getSoldeCompte() + montant);
                    courantService.update(courant);

                } else {
                    CompteEpargne epargne = epargneService.readOne(cptCredit);
                    virementCredit = new Virement();
                    virementCredit.setCompte(epargne);
                    //update du compte epargne
                    epargne.setSoldeCompte(epargne.getSoldeCompte() + montant);
                    epargneService.update(epargne);
                }

                virementDebit.setReference(reference);
                virementDebit.setMontant(montant);
                virementDebit.setDateCreation(new Date());

                virementCredit.setReference(reference);
                virementCredit.setMontant(montant);
                virementCredit.setDateCreation(new Date());

                if (retirer(virementDebit).equals(true) && verser(virementCredit).equals(true)) {
                    session.setAttribute(ConstatnteConfigs.ALERTE,"Virement effectué avec succes ..." +reference);
                    return reference;
                }

                return null;
            }
        }

        return null;

    }

    @Override
    public Boolean contrainteCompte(String compteDebiter, String compteCrediter) {

        if (!"".equals(compteDebiter) && !"".equals(compteCrediter)) {
            //les proprietes initialisation
            Boolean responseContrainte = false;

            //recuperer les types comptes des deux comptes
            String typeCompt1 = operationService.typeCompte(compteDebiter);
            String typeCompt2 = operationService.typeCompte(compteCrediter);

            if (typeCompt1 != null && typeCompt2 != null) {

                 responseContrainte = true;

                //
                // verification des comptes
                //Meme numero de compte
                if (compteDebiter.equals(compteCrediter)) {
                    // message
                    session.setAttribute(ConstatnteConfigs.ALERTE,"Compte identique,  Transaction impossible ...");
                    responseContrainte = false;
                }

                //verification des types
                if (typeCompt1.equals("epargne") && typeCompt2.equals("courant")) {
                    session.setAttribute(ConstatnteConfigs.ALERTE, "Compte Epargne est débiteur, virement impossible ...");
                    responseContrainte = false;
                }

                // meme type de compte
                if (typeCompt1.equals("epargne") && typeCompt2.equals("epargne")) {
                    // message
                    session.setAttribute(ConstatnteConfigs.ALERTE, "Compte identique, virement impossible ...");
                    responseContrainte = false;
                }

            }
            return responseContrainte;
        }
        return false;

    }

    @Override
    public List<Virement> readOneVirementVireParRef(String reference) {

        if (!StringUtils.isEmpty(reference)) {
            return virementRepository.findByReferenceAndTypeVirement(reference, "RETIRER").orElse(null);
        }
        return null;

    }

    @Override
    public List<Virement> readOneVirementRecuParRef(String reference) {

        if (!StringUtils.isEmpty(reference)) {
            return virementRepository.findByReferenceAndTypeVirement(reference, "VERSER").orElse(null);
        }
        return null;

    }

    @Override
    public Virement create(Virement t) {

        if (t != null) {
            return virementRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Virement> readAll() {

        return virementRepository.findAll();

    }

    @Override
    public Virement readOne(Long pk) {

        if (pk > 0) {
            return virementRepository.findByNumOperation(pk);
        }
        return null;

    }

    @Override
    public Virement update(Virement t) {

        if (t != null) {
            return virementRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Virement t) {

        if (t != null) {
            return virementRepository.save(t) != null;
        }
        return null;

    }

    @Override
    public Boolean verser(Virement virement) {

        if (virement != null) {
            virement.setTypeVirement("VERSER");
            return create(virement) != null;
        }
        return false;
    }

    @Override
    public List<Virement> readAllVirementByClientAndRetirer(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            if(client!=null){
                return virementRepository.findByCompteClientAndTypeVirement(client, "RETIRER");
            }
        }
        return null;

    }

    /**
     *  liste de map des virements
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listVirement(Conseiller conseiller) {

        logger.info("******* VirementService methode listVirement ***** " );

        // declaration et intentiation
        List<String> listReference = new LinkedList<>();
        List<Map<String, Object>> listVirement = new LinkedList<>();
        Map<String, Object> mapVirement = null;
        List<Virement> autreListVirement;
        Compte compteCrediteur = null;
        Compte compteDebiteur = null;

        // formatter la date
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:s");

        logger.info("******* VirementController listVire Conseiller ***** " + conseiller );
        logger.info("******* VirementController listVire reallVirementsByConseiller ***** " + reallVirementsByConseiller(conseiller) );

        // condition
        for (Virement virement: reallVirementsByConseiller(conseiller) ) {

            logger.info("******* VirementController listVirement boucle for ***** " );

            // si la liste ne contient pas la reference on l'ajoute
            if (!listReference.contains(virement.getReference())){
                listReference.add(virement.getReference());
                // initialisation compte
                compteCrediteur = null;
                compteDebiteur = null;

                // liste initialisation
                autreListVirement= null;
                // recuperation liste de viremente par reference et par type de virement (recu)
                logger.info("******* VirementController listVirement boucle for if reference ***** " + virement.getReference());
                logger.info("******* VirementController listVirement boucle for if liste reference***** " + readOneVirementRecuParRef(virement.getReference()));

                autreListVirement = readOneVirementRecuParRef(virement.getReference());


                 //
                if (!autreListVirement.isEmpty()){
                    // recuperation du compte debiteur
                    compteCrediteur = autreListVirement.get(0).getCompte();

                }

                // compte crediteur
                autreListVirement= null;
                // recuperation liste de viremente par reference et par type de virement (virer)
                autreListVirement = readOneVirementVireParRef(virement.getReference());

                 //
                if (!autreListVirement.isEmpty()){
                    // recuperation du compte debiteur
                    compteDebiteur = autreListVirement.get(0).getCompte();

                }

                // condition sur les deux comptes
                if (compteCrediteur != null && compteDebiteur != null){

                    logger.info(" ****** VirementController listVire boucle if if ");

                    mapVirement = new HashMap<>();
                    mapVirement.put("comptedebiteur",compteDebiteur);
                    mapVirement.put("comptecrediteur",compteCrediteur);
                    mapVirement.put("reference",virement.getReference());
                    mapVirement.put("montant",virement.getMontant());
                    mapVirement.put("dateoperation",dataFormat.format(virement.getDateCreation()));

                    // ajout
                    listVirement.add(mapVirement);
                }
            }

        }
        return listVirement;
    }


    /**
     * retourne la liste des virements par conseiller
     *
     * @param conseiller
     * @return
     */
    @Override
    public List<Virement> reallVirementsByConseiller(Conseiller conseiller) {

        return virementRepository.findByCompteClientConseiller(conseiller);
    }

    @Override
    public List<Virement> readAllVirementByClientAndVerser(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            if(client!=null){
                return virementRepository.findByCompteClientAndTypeVirement(client, "VERSER");
            }
        }
        return null;
    }
    
    
}
