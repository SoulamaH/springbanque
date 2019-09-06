/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.resource;

import ci.spring.entities.Conseiller;
import ci.spring.entities.Versement;
import ci.spring.service.IOperationService;
import ci.spring.service.IVersementService;
import ci.spring.service.impl.OperationService;
import ci.spring.service.impl.VersementService;
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

/**
 * classe controller, elle permettra d'atteindre nos vues liées aux versements
 * grâce à l'annotation "@RequestMapping" et le chemin ("/") qui est la racine
 * <p>
 * RequestMapping : por dire qu'elle rend la classe disponible pour toutes les méthodes
 *
 * @author USER
 */
@Controller
@RequestMapping("/")
public class VersementController {

    @Autowired
    IVersementService versementService;

    @Autowired
    IOperationService operationService;

    private List<Versement> listversement;

    private final Logger logger = LoggerFactory.getLogger(VirementController.class);

    /**
     * Cette méthode permet d'afficher la vue du versement
     *
     * @return tableau recapitulatif
     */
    @GetMapping("/versement")
    public String ajoutVersement(Model model, HttpSession session) {

        // declaration et recuperation de la clé venant de la methode retraitCompte
        Object alerteVersement = session.getAttribute("alertversement");

        // condition sur l'existence de l'objet
        if (alerteVersement != null) {
            model.addAttribute("alertversement", alerteVersement); // envoie de l'objet a la vue pour affichge sur message
            session.removeAttribute("alertversement"); // suppression de l'objet de la ssession
        }

        // affectation
        listversement = versementService.readAllByCompteClientConseiller((Conseiller) session.getAttribute("user"));

        logger.info("********** VersementController **** " + listversement);
        // verif de la liste
        if (!listversement.isEmpty()) {
            model.addAttribute("listeversement", listversement);
        }
        return "operations/versement/recapitulatifversement";
    }

    /**
     * methode petmettant de retourner sur la vue pour un versement
     *
     * @return le formulaire
     */
    @GetMapping("/versementcompte")
    public String afficherVersement() {
        return "operations/versement/tables";
    }

    /**
     * methode permettant de faire un versement
     *
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/saveversement")
    public String versementCompte(Model model, HttpServletRequest request) {

        // declaration puis recuperation des infos depuis la vue
        String compteVersement = request.getParameter("compteverser");
        Double montantVerser = Double.valueOf(request.getParameter("montant"));

        // condition si le compte est different de null et n'est pas vide
        if (!StringUtils.isEmpty(compteVersement)) {
            // condition pour l'enregistrement
            if (versementService.effectuerVersement(compteVersement, montantVerser) != null) {
                // injection du message et la clé apres la transation
//                request.getSession().setAttribute("alertversement", "Versement effectué avec succes ...");
                return "redirect:/versement";
            }
        }

        // on reste sur la vue avec les infos au cas ou l'enregistrement
        // c'est mal passé
        model.addAttribute("compteversement", compteVersement);
        model.addAttribute("montant", compteVersement);
        return "operations/retrait/tables";
    }
}
