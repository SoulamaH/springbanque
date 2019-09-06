package ci.spring.repository;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.Conseiller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, String> {

    //retourner le nombre de compte
    public int countByClient(Client client);

    // liste des comptes par conseiller
    public Optional<List<Compte>> findByNumeroCompteAndClientConseiller(String numeroCompte, Conseiller conseiller);

}
