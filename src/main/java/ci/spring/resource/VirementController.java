/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.resource;

import ci.spring.configuration.ConstatnteConfigs;
import ci.spring.entities.Conseiller;
import ci.spring.service.IOperationService;
import ci.spring.service.IVirementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Classe controleur, Grâce à l'annotation @Controller
 * elle permet d'atteindre nos vues liées aux virements
 * grâce à l'annotation "@RequestMapping" et le chemin ("/virement")
 * <p>
 * RequestMapping : pour dire qu'elle rend la classe disponible pour toutes les méthodes
 *
 * @author USER
 */
@Controller
@RequestMapping("")
public class VirementController {

    @Autowired
    IVirementService virementService;

    @Autowired
    IOperationService operationService;

    private Conseiller conseiller;

    private List<Map<String, Object>> listVerifVirement;

    private final Logger logger = LoggerFactory.getLogger(VirementController.class);


    /**
     * tableau des virements effectués
     */
    @GetMapping("/virement")
    public String afficherRecapitulatif(Model model, HttpSession session) {

        // declaration et recuperation de la clé venant de la methode retraitCompte
        Object alerteVirement = session.getAttribute(ConstatnteConfigs.ALERTE);

        // condition sur l'existence de l'objet
        if (alerteVirement != null) {
            model.addAttribute("aletervirement", alerteVirement); // envoie de l'objet a la vue pour affichge sur message
            session.removeAttribute(ConstatnteConfigs.ALERTE); // suppression de l'objet de la ssession
        }

        // message log
        //logger.info("******* VirementController afficherRecapitulatif ***** " +  virementService.listVirement((Conseiller)session.getAttribute("user")));

        // affection de la liste
        listVerifVirement = virementService.listVirement((Conseiller) session.getAttribute("user"));

        // verification de la liste
        if (!listVerifVirement.isEmpty()) {
            model.addAttribute("listevirement", listVerifVirement);
        }
        return "operations/virement/recapitulativirement";
    }

    // methode petmettant de retourner sur la vue pour unvirement
    @GetMapping("/virementcompte")
    public String afficherVirement(Model model) {

        return "operations/virement/tables";
    }

    // metthode permettant de faire un virement
    @PostMapping("/savevirement")
    public String virementCompte(Model model, HttpServletRequest request) {

        // declaration puis recuperation des infos depuis la vue
        String compteDebiter = request.getParameter("comptedebiter");
        String compteCrediter = request.getParameter("comptecrediter");
        Double montantDebiter = Double.valueOf(request.getParameter("montant"));

        // condition si le comptes sont different et vide
        if (!StringUtils.isEmpty(compteDebiter) && !StringUtils.isEmpty(compteCrediter)) {
            // condition si le montant >0
            if (montantDebiter > 0) {
                // condition pour l'enregistrement
                if (virementService.virementCompteACompte(compteDebiter, compteCrediter, montantDebiter) != null) {
                    // injection du message et la clé apres la transation
                    //request.getSession().setAttribute("aletervirement", "Virement effectué avec succes ...");
                    return "redirect:/virement";
                }
            }
        }

        // declaration et recuperation de la clé venant de la methode retraitCompte
        Object alerteVirement = request.getSession().getAttribute(ConstatnteConfigs.ALERTE);

        // condition sur l'existence de l'objet
        if (alerteVirement != null) {
            model.addAttribute("aletervirement", alerteVirement); // envoie de l'objet a la vue pour affichge sur message
            request.removeAttribute(ConstatnteConfigs.ALERTE); // suppression de l'objet de la ssession
        }


        // on reste sur la vue avec les infos au cas ou l'enregistrement
        // c'est mal passé
        model.addAttribute("comptedebiter", compteDebiter);
        model.addAttribute("comptecrediter", compteCrediter);
        model.addAttribute("montant", montantDebiter);
        return "operations/virement/tables";
    }

}
