package ci.spring.service;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.Conseiller;
import ci.spring.entities.Retrait;

import java.util.List;
import java.util.Map;

public interface ICompteService {

    // retourner le nombre de compte
    public int getNbreCompteByClient(Client client);

    // liste de map d'objet qui retourne le compte enfonction du client
    public List<Map<String, Object>> getCompteByClient(Client client);

    // retourne la liste des compte enregistre par conseiller
    public List<Compte> readAllByNumeroCompteAndClientConseiller(String numeroCompte, Conseiller conseiller) ;
}
