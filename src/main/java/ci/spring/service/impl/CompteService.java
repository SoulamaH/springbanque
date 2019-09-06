package ci.spring.service.impl;

import ci.spring.entities.*;
import ci.spring.repository.CompteRepository;
import ci.spring.service.ICompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CompteService implements ICompteService {

    //injection par interface
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteCourantService  compteCourantService;
    @Autowired
    private CompteEpargneService compteEpargneService;


    /**
     * retourne le nombre de compte
     * @param client
     * @return
     */
    @Override
    public int getNbreCompteByClient(Client client) {
        // declaration et affectation
        int recupNbrCmpt = compteRepository.countByClient(client);
        return recupNbrCmpt > 0 ? recupNbrCmpt : 0;
    }

    /**
     * mathode retournant la liste de compte en fonction du client
     *
     * @param client
     * @return
     */
    @Override
    public List<Map<String, Object>> getCompteByClient(Client client) {
        // declaration affectations
        List<CompteCourant> listCompteCourants = compteCourantService.readAllByClient(client);
        List<CompteEpargne> listCompteEpargnes = compteEpargneService.readAllByclient(client);

        List<Map<String,Object>> listMap = new LinkedList<>();
        Map<String, Object> map = null;

        // condition courant
        if (!listCompteCourants.isEmpty()){
            for (CompteCourant compteCourant : listCompteCourants) {
                map = new HashMap<>();
                map.put("compte", compteCourant);
                map.put("typecompte","Courant");
                listMap.add(map);
                //System.out.println("************ ");
            }
        }
        // condition epargne
        if (!listCompteEpargnes.isEmpty()){
            for (CompteEpargne compteEpargne : listCompteEpargnes) {
                map = new HashMap<>();
                map.put("compte", compteEpargne);
                map.put("typecompte","Epargne");
                listMap.add(map);
            }
        }
//        System.out.println(" ***** COMPTESERVICE 111 ******");
//        System.out.println(" ***** COMPTESERVICE ******" + listMap);
        return listMap;
    }

    /**
     * retourne la liste de compte par conseiller
     *
     * @param conseiller
     * @return
     */
    @Override
    public List<Compte> readAllByNumeroCompteAndClientConseiller(String numeroCompte,Conseiller conseiller) {
        return compteRepository.findByNumeroCompteAndClientConseiller(numeroCompte,conseiller).orElse(null);
    }
}
