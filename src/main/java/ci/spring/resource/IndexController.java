/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.resource;

import ci.spring.entities.Conseiller;
import ci.spring.service.IClientService;
import ci.spring.service.impl.ConseillerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USER
 * classe controller, elle servira de vue d'accueille
 */
/*
 * On peut acceder aux methodes graces a la valeur /
*/
@Controller
@RequestMapping(value = "/")
public class IndexController {
    
    // injections par interface
    @Autowired
    private IClientService clientService;

    @Autowired
    private ConseillerService conseillerService;
    
    /**
     * Méthode permettant de faire une redirection sur la methode
     * redirectIndex pour l'affichage de notre première page
     *
     * @return la page index.html
     */
    @GetMapping(value = "")
    //@PreAuthorize("hasRole('ADMIN')")
    public String index(HttpServletRequest request) {
        // redirection a l'URL : index
        return "redirect:/index";
    }
    
    /**
     * Méthode permettant de retourner l'interface de connexion
     * 
     */
    @GetMapping(value = "/login")
    public String login() {
        return "login/login";
    }

    /**
     * Envoie d'information à la vue grâce à la variable model
     * par lephenomène de clé et valeur
     *
     * methode permettant de retourner la liste des clients
     * dans le tableau de l'index
     *
     * @return
     */
    // redirection sur l'index
    @GetMapping(value = "/index")
    public String redictIndex(Model model, HttpSession session){

        // declaration et recuperation de la clé
        Object alerteClient = session.getAttribute("alertclient");

        // condition sur l'existence de l'objet
        if (alerteClient != null){
            model.addAttribute("alertclient",alerteClient); // envoie de l'objet a la vue
            session.removeAttribute("alertclient"); // suppression de l'objet de la ssession
        }
        // envoie d'info sur la vue grace a la clé listeclient
        // et la recuperation des données par clientService.getAllClientAndNbreCmpt()
        model.addAttribute("listeclient",clientService.getAllClientAndNbreCmpt((Conseiller)session.getAttribute("user")));
        return "index/index";
    }


}
