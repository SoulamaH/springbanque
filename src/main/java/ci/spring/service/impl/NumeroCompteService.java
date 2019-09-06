/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service.impl;

import ci.spring.service.INumeroCompteService;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * NumeroCompteService redefini les methodes de l'interface
 * INumeroCompteService
 *
 * @author USER
 */
@Service
public class NumeroCompteService implements INumeroCompteService {

    /**
     * methode permettant de generer un numero qui sera assigné au compte
     *
     * @return code
     */
    // methode generant le numero de compte
    @Override
    public String generatedNumeroCompte() {

        String code = "0123456789AZERTYUIOPQSDFGHJKLMWXCVBN";

        // initialisation de la variable
        String codeGenere = "";

        // Objet permettant de faire des tries aléatoir
        Random aleatoir = new Random();

        for (int compt = 1; compt <= 6; compt++) {

            codeGenere = codeGenere + code.charAt(aleatoir.nextInt(code.length()));
        }
        return codeGenere;
    }

}