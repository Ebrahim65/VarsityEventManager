/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package za.ac.tut.model.bl;

import java.util.List;
import javax.ejb.Local;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.Registration;
import za.ac.tut.model.entity.User;

/**
 *
 * @author Ebrahim
 */
@Local
public interface RegistrationFacadeLocal {

    void create(Registration registration);

    void edit(Registration registration);

    void remove(Registration registration);

    Registration find(Object id);

    List<Registration> findAll();

    List<Registration> findRange(int[] range);

    int count();
    
    List<Registration> findByUser(User user);
    
    List<Registration> findByEvent(Event event);
    
    boolean isUserRegisteredForEvent(User user, Event event);
    
}
