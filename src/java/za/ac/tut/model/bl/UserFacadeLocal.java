package za.ac.tut.model.bl;

import java.util.List;
import javax.ejb.Local;
import za.ac.tut.model.entity.User;

/**
 *
 * @author Ebrahim
 */
@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    
    User findByUsername(String username);
    
    User findByEmail(String email);
    
    List<User> findByRole(User.Role role);

}
