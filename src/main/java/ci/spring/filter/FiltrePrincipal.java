/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ci.spring.entities.Conseiller;
import ci.spring.service.impl.ConseillerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * filtre permet de recuperer l'utilisateur connecté
 *
 * @author USER
 */
@Component
@Order(1)
public class FiltrePrincipal implements Filter{

    @Autowired
    private ConseillerService conseillerService;


    @Override
    public void doFilter(ServletRequest request,
      ServletResponse response, 
      FilterChain chain) throws IOException, ServletException {

        //
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Object object = httpRequest.getSession().getAttribute("user");

        // condition sur l'existance de l'objet
        if (object == null){
            //recuperation de l'user connecté
            Conseiller conseiller = conseillerService.readOneByUserName(httpRequest.getRemoteUser());
            // condition sur l'existance de l'objet
            if (conseiller !=  null){
                // affectation de la clé et l'objet à la session
                httpRequest.getSession().setAttribute("user",conseiller);
            }
        }
        chain.doFilter(request, response);
    }
    
}
