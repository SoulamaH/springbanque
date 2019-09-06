/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.spring.service;

import ci.spring.entities.Conseiller;
import java.util.List;

/**
 * Interface IConseiller elle contiendra des méthodes declarées qui seront
 * propres aux traitements metier du Conseiller.
 * 
 * @author USER
 */
public interface IConseillerService  {
    
    public Conseiller create(Conseiller conseiller);
    
    public Conseiller readOne(Long id);
    
    public Conseiller update(Conseiller conseiller);
    
    public void delete(Long id);
    
    public List<Conseiller>readAll();

    // methode retournant le conseiller par userName
    public Conseiller readOneByUserName(String userName);
    
    
}
