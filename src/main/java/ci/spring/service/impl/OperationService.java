/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.entities.CompteCourant;
import ci.spring.entities.CompteEpargne;
import ci.spring.repository.OperationRepository;
import ci.spring.service.ICompteCourantService;
import ci.spring.service.ICompteEpargneService;
import ci.spring.service.INumeroCompteService;
import ci.spring.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * classe service operation redefir les methode de l'interface IOperationService
 *
 * @author User
 */
@Service
public class OperationService implements IOperationService {

    // injection par interfaces
    @Autowired
    private INumeroCompteService numeroCompteService;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private ICompteCourantService courantService;

    @Autowired
    private ICompteEpargneService epargneService;


    /**
     * methode permettant de generer des numeros
     *
     * @return
     */
    @Override
    public Long generatedNumTransc() {

        Long newNum = Long.parseLong(numeroCompteService.generatedNumeroCompte());

        while (operationRepository.findByNumOperation(newNum) != null) {
            newNum = Long.parseLong(numeroCompteService.generatedNumeroCompte());
        }

        return newNum;

    }

    /**
     * methode permettantde verifier le type de compte
     *
     * @param numeroCpt
     * @return
     */
    @Override
    public String typeCompte(String numeroCpt) {

        if (!"".equals(numeroCpt)) {
            if (courantService.readOne(numeroCpt) != null) {
                return "courant";
            } else if (epargneService.readOne(numeroCpt) != null) {
                return "epargne";
            }
        }

        return null;

    }

    /**
     * methode permettant de verifier le solde, son niveau
     * en ayant le compte
     *
     * @param cpt
     * @param montant
     * @return
     */
    @Override
    public Boolean verifSolde(String cpt, Double montant) {

        String typCompte = typeCompte(cpt);

        if (!"".equals(typCompte)) {
            if (typCompte.equals("courant")) {
                // verification si le  solde >= au monatant renseigné pour effectuer la transaction
                if (courantService.readOne(cpt).getSoldeCompte() >= montant) {
                    return true;
                }
            // verification si le  solde >= au monatant renseigné pour effectuer la transaction
            } else if (typCompte.equals("epargne")) {
                if (epargneService.readOne(cpt).getSoldeCompte() >= montant) {
                    return true;
                }
            }
        }
        return false;
    }

    // mettre a jour le solde

    /**
     * methode permettant de mettre à jour le solde
     * apres la transaction
     *
     * @param numeroCompte
     * @param montant
     * @param typeOperation
     * @return
     */
    @Override
    public Boolean updateSolde(String numeroCompte, Double montant, String typeOperation) {
        String typCompte = typeCompte(numeroCompte);

        // on verifie que l'utilisateur a rentré quelque chose
        if (!"".equals(typCompte)) {

            // si le compte est courant (l'info rentrée par l'utilisateur
            if (typCompte.equals("courant")) {
                // affectation du numeroComptte
                CompteCourant courant = courantService.readOne(numeroCompte);
                switch (typeOperation) {
                    case "retrait":
                        courant.setSoldeCompte(courant.getSoldeCompte() - montant);
                        break;
                    case "versement":
                        courant.setSoldeCompte(courant.getSoldeCompte() + montant);
                        break;
                }

                // mise a jour du solde apres l'operation en cours
                if (courantService.update(courant) != null) {
                    return true;
                }
            }
            // condition sur l'epargne
            else if (typCompte.equals("epargne")) {

                // affectation du numero de compte
                CompteEpargne epargne = epargneService.readOne(numeroCompte);

                switch (typeOperation) {
                    case "retrait":
                        epargne.setSoldeCompte(epargne.getSoldeCompte() - montant);
                        break;
                    case "versement":
                        epargne.setSoldeCompte(epargne.getSoldeCompte() + montant);
                        break;
                }

                // mise a jour du solde apres l'operation en cours
                if (epargneService.update(epargne) != null) {
                    return true;
                }
            }
        }
        return false;
    }

}
