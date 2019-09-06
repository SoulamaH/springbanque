/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.resource;

import ci.spring.entities.Conseiller;
import ci.spring.service.IOperationService;
import ci.spring.service.IRetraitService;
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

/**
 * classe controleur grace à l'annotation "@Controller",
 * elle permet d'atteindre nos vue liées aux retraits grâce
 * à l'annotation "@RequestMapping" et le chemin ("/retrait")
 * RequestMapping : por dire qu'elle rend la classe disponible pour toutes les méthodes
 *
 * @author USER
 */
@Controller
@RequestMapping("/")
public class RetraitController {

    // injection par interface
    @Autowired
    IRetraitService retraitService;

    @Autowired
    IOperationService operationService;

    private Conseiller conseiller;

    private final Logger logger = LoggerFactory.getLogger(VirementController.class);


    /**
     * cette méthode permet d'afficher le tableau recapitulatif pour le retrait
     */
    @GetMapping("/retrait")
    public String ajoutRetrait(Model model, HttpSession session) {

        // declaration et recuperation de la clé venant de la methode retraitCompte
        Object alerteRetrait = session.getAttribute("alertretrait");

        // condition sur l'existence de l'objet
        if (alerteRetrait != null) {
            model.addAttribute("alertretrait", alerteRetrait); // envoie de l'objet a la vue pour affichge sur message
            session.removeAttribute("alertretrait"); // suppression de l'objet de la ssession
        }

        // verification que la liste n'est pas vide
        if (!retraitService.readAll().isEmpty()) {
            model.addAttribute("listeretrait", retraitService.readAllByCompteClientConseiller((Conseiller) session.getAttribute("user")));
        }

        return "operations/retrait/recapitulatiretrait";
    }

    // methode petmettant de retourner sur la vue pour un retrait
    @GetMapping("/retraitcompte")
    public String afficherVirement() {
        return "operations/retrait/tables";
    }

    // methode permettant de faire un retrait
    @PostMapping("/saveretrait")
    public String retraitCompte(Model model, HttpServletRequest request) {

        // declaration puis recuperation des infos depuis la vue
        String compteRetrait = request.getParameter("compteretrait");
        Double montantRetirer = Double.valueOf(request.getParameter("montant"));

        // condition si le compte est different de null et n'est pas vide
        if (!StringUtils.isEmpty(compteRetrait)) {
            // condition si le montant >0
            if (montantRetirer > 0) {
                // condition pour l'enregistrement
                if (retraitService.effectuerRetrait(compteRetrait, montantRetirer, (Conseiller) request.getSession().getAttribute("user")) != null) {
                    // injection de la clé et du message dans l'enregistrement
                    request.getSession().setAttribute("alertretrait", "Retrait effectué avec succes ...");
                    return "redirect:/retrait";
                }
            }
        }

        // on reste sur la vue avec les infos au cas ou l'enregistrement
        // c'est mal passé
        model.addAttribute("compteretrait", compteRetrait);
        model.addAttribute("montant", montantRetirer);
        return "operations/retrait/tables";
    }


}
