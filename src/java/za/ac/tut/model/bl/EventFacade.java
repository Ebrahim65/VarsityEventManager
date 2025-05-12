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
public class EventFacade extends AbstractFacade<Event> implements EventFacadeLocal {

    @PersistenceContext(unitName = "VarsityEventManagerEJBModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }

    @Override
    public List<Event> findUpcomingEvents() {
        return em.createQuery("SELECT e FROM Event e WHERE e.dateTime >= CURRENT_DATE ORDER BY e.dateTime ASC", Event.class)
                .getResultList();
    }

    @Override
    public List<Event> findByCategory(String category) {
        return em.createQuery("SELECT e FROM Event e WHERE e.category = :category", Event.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public List<Event> findByOrganizer(User organizer) {
        return em.createQuery("SELECT e FROM Event e WHERE e.organizer = :organizer", Event.class)
                .setParameter("organizer", organizer)
                .getResultList();
    }

}
