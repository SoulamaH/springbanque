package ci.spring.resource;

import ci.spring.entities.Client;
import ci.spring.entities.CompteCourant;
import ci.spring.entities.CompteEpargne;
import ci.spring.entities.Conseiller;
import ci.spring.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * classe controleur grace à l'annotation "@Controller",
 * elle permet d'atteindre nos vue liées aux profil de l'user grâce
 * à l'annotation "@RequestMapping" et le chemin ("/profil")
 * RequestMapping : pour dire qu'elle rend la classe disponible pour toutes les méthodes
 *
 * @author USER
 */
@Controller
@RequestMapping("/profile")
public class ProfilController {

    // injection par interface
    @Autowired
    private ClientService clientService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private CompteCourantService compteCourantService;

    @Autowired
    private CompteEpargneService compteEpargneService;

    @Autowired
    private NumeroCompteService numeroCompteService;

    // declarations d'objets
    private Client client;


    /**
     * cette méthode permet de faire un update ou un ajout
     * methode deux en un
     */
    @GetMapping("")
    public String voirProfil(HttpSession session, Model model) {

        // condition pour rester sur le formulaire au cas ou le solde n'est pas respecté
        // dans la methode enregistreAndModifier
        if (session.getAttribute("solde") != null) {

            model.addAttribute("client", session.getAttribute("client"));
            model.addAttribute("typecompte", session.getAttribute("typecompte"));
            model.addAttribute("solde", session.getAttribute("solde"));

            // alert d'echec
            model.addAttribute("alertesolde", session.getAttribute("alertesolde"));

            // suppression de la session
            session.removeAttribute("client");
            session.removeAttribute("typecompte");
            session.removeAttribute("solde");

            // condition pour un update = modification
        } else if (session.getAttribute("updateclient") != null) {
            Object object = session.getAttribute("updateclient");
            // declaration d'objet et affectation pour l'alerte
            Object objectAlert = session.getAttribute("alertajoute");

            if (object != null) {
                client = (Client) object; // recuperation de données
                model.addAttribute("client", client); // recuperation et envoie de données sur la vue
                // System.out.println("********* CLIENT **** " + client);
                model.addAttribute("getcompte", compteService.getCompteByClient(client)); // recuperation des comptes par client
                // System.out.println("********* RECUPERATION DE COMPT/CLIENT **** " + compteService.getCompteByClient(client));
                session.removeAttribute("updateclient"); // suppression d'objet dans la session
            }

            // conditon sur l'objet pour l'alerte
            if (objectAlert != null) {
                model.addAttribute("alertajoute", objectAlert); // recuperation et envoie de données sur la vue
                session.removeAttribute("alertajoute"); // suppression d'objet dans la session
            }
        } else {
            // condition pour un enregistrement
            model.addAttribute("client", new Client());
        }

        return "enregistrement/profile";
    }

    /**
     * methode permettant d'envoyer les informtions du client deja
     * enregistrées à la vue "enreg Client" pour un update
     *
     * @param id
     * @param request
     * @return la vue profile par une redirection
     */
    // update (modifier un element)
    @GetMapping("/{id}")
    public String modiferClient(@PathVariable Long id, HttpServletRequest request) {

        // recuperation du client
        client = clientService.readOne(id);
        //System.out.println("************ MODIFIERCLIENT **** " +client);
        // condition si client different de null
        if (client != null) {
            request.getSession().setAttribute("updateclient", client);
            // mettre la clé et le message dans la session

            System.out.println("********  SESSSION ***** " + request.getSession().getAttribute("updateclient"));
        }
        return "redirect:/profile";
    }

//    // methode non utilisée au cas ou elle est supprimée
//    // sa sidebar "ebregistrer client" doit l'etre aussi
//    @GetMapping("/enregistrementprofileclient")
//    public String enregistrerProfil() {
//        return "voirprofiloperation/enregistrementprofile";
//    }

    /**
     * methode permettant d'enregistrerun client ou
     * faire un update si l'id existe
     *
     * @param clientSave
     * @param request
     * @return
     */
    // methode deux en un save et update sur un client
    @PostMapping("/save")
    public String enregistreAndModifier(Client clientSave, HttpServletRequest request, Model model) {

        // declaration pour l'enregistrement
        CompteCourant compteCourant;
        CompteEpargne compteEpargne;
        Client client;

        // recuperation des infos depuis ma vue
        //typecompte doit etre = a la propriete name de ma vu html
        String typeCompte = request.getParameter("typecompte");

        //solde doit etre = a la propriete name de ma vu html
        Double solde = null;
        if (request.getParameter("solde") != null) {
            // recuperation des infos depuis ma vu
            solde = Double.valueOf(request.getParameter("solde"));

            // verification du solde minimun pour la creation du compte
            if (solde >= 1000) {

                Object object = null;

                // si l'id different de null on fait un update
                if (clientSave.getId() != null) {

                    object = clientService.create(clientSave);
                    // condition sur l'objet
                    if (object != null) {
                        request.getSession().setAttribute("alertclient", "Modification effectuée avec succes ...");
                    }

                    // sinon si l'id = null on fait un create = save
                } else {
                    // injection du conseiller dans l'enregistrement par saclé depuis le filtre
                    clientSave.setConseiller((Conseiller) request.getSession().getAttribute("user"));
                    client = clientService.create(clientSave);

                    if (client != null) {

                        if (typeCompte.equals("courant")) {
                            compteCourant = new CompteCourant();
                            compteCourant.setNumeroCompte(numeroCompteService.generatedNumeroCompte());
                            compteCourant.setSoldeCompte(solde);
                            compteCourant.setClient(client);
                            object = compteCourantService.create(compteCourant);// enregistrement
                        } else {
                            compteEpargne = new CompteEpargne();
                            compteEpargne.setNumeroCompte(numeroCompteService.generatedNumeroCompte());
                            compteEpargne.setSoldeCompte(solde);
                            compteEpargne.setClient(client);
                            object = compteEpargneService.create(compteEpargne); // enregistrement
                        }
                    }

                    if (object != null) {
                        request.getSession().setAttribute("alertclient", "Enregistrement client effectué avec succes ...");
                    }
                }

                // on reste sur la vue au cas le montant initial n'est pas respecté
            } else {
                request.getSession().setAttribute("typecompte", typeCompte);
                request.getSession().setAttribute("solde", solde);
                request.getSession().setAttribute("client", clientSave);

                // alert d'echec
                request.getSession().setAttribute("alertesolde", "montant insuffisant pour créer le compte");
                return "redirect:/profile"; // redirection sur le formulaire
            }
        }
        return "redirect:/"; // redirection sur l'index pour voir l'enregistrement
    }

    // methode permettant de faire un ajout de compte
    @PostMapping("/savecompte")
    public String enregistrerCompte(HttpServletRequest request) {
        // declaration pour l'enregistrement
        CompteCourant compteCourant;
        CompteEpargne compteEpargne;

        Long idClient = Long.valueOf(request.getParameter("clientid"));
        client = clientService.readOne(idClient);

        //typecompte doit etre = a la propriete name de ma vu html
        String typeCompte = request.getParameter("typecompte");

        //solde doit etre = a la propriete name de ma vu html
        Double solde = null;
        if (request.getParameter("solde") != null) {
            solde = Double.valueOf(request.getParameter("solde"));
        }

        Object object = null;
        if (typeCompte.equals("courant")) {
            compteCourant = new CompteCourant();
            compteCourant.setNumeroCompte(numeroCompteService.generatedNumeroCompte());
            compteCourant.setDateCreation(new Date());
            compteCourant.setSoldeCompte(solde);
            compteCourant.setClient(client);
            object = compteCourantService.create(compteCourant);// enregistrement
        } else {
            compteEpargne = new CompteEpargne();
            compteEpargne.setNumeroCompte(numeroCompteService.generatedNumeroCompte());
            compteEpargne.setDateCreation(new Date());
            compteEpargne.setSoldeCompte(solde);
            compteEpargne.setClient(client);
            object = compteEpargneService.create(compteEpargne); // enregistrement
        }
        if (object != null) {
            request.getSession().setAttribute("updateclient", client);
            request.getSession().setAttribute("alertajoute", "Enregistrement effectué avec succes ...");
        }
        return "redirect:/profile";
    }

}
