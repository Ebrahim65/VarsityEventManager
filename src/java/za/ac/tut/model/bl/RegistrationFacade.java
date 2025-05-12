package za.ac.tut.model.bl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.Registration;
import za.ac.tut.model.entity.User;

/**
 *
 * @author Ebrahim
 */
@Stateless
public class RegistrationFacade extends AbstractFacade<Registration> implements RegistrationFacadeLocal {

    @PersistenceContext(unitName = "VarsityEventManagerEJBModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistrationFacade() {
        super(Registration.class);
    }

    @Override
    public List<Registration> findByUser(User user) {
        return em.createQuery("SELECT r FROM Registration r WHERE r.user = :user", Registration.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Registration> findByEvent(Event event) {
        return em.createQuery("SELECT r FROM Registration r WHERE r.event = :event", Registration.class)
                .setParameter("event", event)
                .getResultList();
    }

    @Override
    public boolean isUserRegisteredForEvent(User user, Event event) {
        Long count = em.createQuery(
                "SELECT COUNT(r) FROM Registration r WHERE r.user = :user AND r.event = :event", Long.class)
                .setParameter("user", user)
                .setParameter("event", event)
                .getSingleResult();
        return count > 0;
    }
}
