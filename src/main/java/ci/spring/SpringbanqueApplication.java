package ci.spring;

import ci.spring.entities.Client;
import ci.spring.entities.Compte;
import ci.spring.entities.CompteCourant;
import ci.spring.entities.CompteEpargne;
import ci.spring.entities.Conseiller;
import ci.spring.entities.Retrait;
import ci.spring.entities.Roles;
import ci.spring.entities.Versement;
import ci.spring.repository.ClientRepository;
import ci.spring.repository.CompteCourantRepository;
import ci.spring.repository.CompteEpargneRepository;
import ci.spring.repository.ConseillerRepository;
import ci.spring.repository.RetraitRepository;
import ci.spring.repository.RolesRepository;
import ci.spring.repository.VersementRepository;
import ci.spring.service.impl.ClientService;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbanqueApplication implements CommandLineRunner {

    // Injections par interface
    @Autowired
    private ConseillerRepository conseillerRepository;

    @Autowired
    private ClientRepository clientRepository; //

    @Autowired
    private CompteCourantRepository compteCourantRepository;

    @Autowired
    private CompteEpargneRepository compteEpargneRepository;
    
    @Autowired
    private RolesRepository rolesRepository;

    /**
     * injuection de linterface operationrepository
     */
   
    @Autowired
    private VersementRepository versementRepository;

    @Autowired
    private RetraitRepository retraitRepository;

    // injection par service 
    @Autowired
    private ClientService clientService;

    
    public static void main(String[] args) {
        SpringApplication.run(SpringbanqueApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //roles
        Set<Roles> roleses = new HashSet<>();
        roleses.add(new Roles("ADMIN"));

        Set<Roles> roleseadmin = new HashSet<>();
        roleseadmin.add(new Roles("SUPERADMIN"));

        // enregistrement consernant le ompte courant avec different conseiller
        Conseiller cons = conseillerRepository.save(new Conseiller("CONSE", "SEILLER", true, "conseiller@gmail.com", "07070707", "proxy", (new BCryptPasswordEncoder()).encode("spring"), roleses));
        Conseiller cons2 = conseillerRepository.save(new Conseiller("CONSE2", "SEILLER2", true, "conseiller2@gmail.com", "08080707", "proxy2", (new BCryptPasswordEncoder()).encode("spring2"), roleses));

        // super admin il se chargera de creer les conseiller
        Conseiller supAdmin = conseillerRepository.save(new Conseiller("SUPER", "ADMIN", true, "superadmin@gmail.com", "07070707", "superadmin", (new BCryptPasswordEncoder()).encode("admin"), roleseadmin));

        //Conseiller cons = conseillerRepository.save(new Conseiller("CONSE", "proxy", "spring"));
        //cons.setRoles(roleses);
        Client c1 = clientService.create(new Client("TESTok", "Test", "01020304","abobo", "test@gmail.com", cons));
        Compte cp1 = compteCourantRepository.save(new CompteCourant("cc1", Double.valueOf(200000), c1));

        // enregistrement consernant le compte Epargne avec different conseiller
        
        //Conseiller cons2 = conseillerRepository.save(new Conseiller("CONSE2", "proxy2", "spring2"));
        //cons2.setRoles(roleses);
        Client c2 = clientService.create(new Client("TESTok2", "Test2", "02030405","anono", "test2@gmail.com", cons2));
        Compte cp2 = compteEpargneRepository.save(new CompteEpargne("ce1", Double.valueOf(300000),  c2));

        // enregistrement d'une opération 
        versementRepository.save(new Versement(null, "VC1", Double.valueOf(5000), cp1));
        versementRepository.save(new Versement(null, "VE1", Double.valueOf(2000), cp1));
        retraitRepository.save(new Retrait(null,"RE1" , Double.valueOf(2000), cp1));

        versementRepository.save(new Versement(null,"VE2", Double.valueOf(3000), cp2));
        versementRepository.save(new Versement(null,"VE2", Double.valueOf(2000), cp2));
        retraitRepository.save(new Retrait(null, "RE2", Double.valueOf(2000), cp2));

        
//        operationRepository.save(new Versement(null, "VC1", new Date(), Double.valueOf(5000), cp1));
//        operationRepository.save(new Versement(null, "VC1", new Date(), Double.valueOf(4000), cp1));
//        operationRepository.save(new Retrait(null, "RC1", new Date(), Double.valueOf(4000), cp1));
//        
//        
//        operationRepository.save(new Versement(null, "VE1", new Date(), Double.valueOf(3000), cp2));
//        operationRepository.save(new Versement(null, "VE1", new Date(), Double.valueOf(2000), cp2));
//        operationRepository.save(new Retrait(null, "RE1", new Date(), Double.valueOf(2000), cp2));

    }

}
