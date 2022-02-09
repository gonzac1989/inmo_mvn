/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import DAO.MovimientoContratoAlq;
import DAO.MovimientoProp;
import DAO.Movimientos;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author e274263
 */
public class MovimientosJpaController implements Serializable {

    public MovimientosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movimientos movimientos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoProp movimientoProp = movimientos.getMovimientoProp();
            if (movimientoProp != null) {
                movimientoProp = em.getReference(movimientoProp.getClass(), movimientoProp.getIdMov());
                movimientos.setMovimientoProp(movimientoProp);
            }
            MovimientoContratoAlq movimientoContratoAlq = movimientos.getMovimientoContratoAlq();
            if (movimientoContratoAlq != null) {
                movimientoContratoAlq = em.getReference(movimientoContratoAlq.getClass(), movimientoContratoAlq.getIdMov());
                movimientos.setMovimientoContratoAlq(movimientoContratoAlq);
            }
            em.persist(movimientos);
            if (movimientoProp != null) {
                Movimientos oldMovimientosOfMovimientoProp = movimientoProp.getMovimientos();
                if (oldMovimientosOfMovimientoProp != null) {
                    oldMovimientosOfMovimientoProp.setMovimientoProp(null);
                    oldMovimientosOfMovimientoProp = em.merge(oldMovimientosOfMovimientoProp);
                }
                movimientoProp.setMovimientos(movimientos);
                movimientoProp = em.merge(movimientoProp);
            }
            if (movimientoContratoAlq != null) {
                Movimientos oldMovimientosOfMovimientoContratoAlq = movimientoContratoAlq.getMovimientos();
                if (oldMovimientosOfMovimientoContratoAlq != null) {
                    oldMovimientosOfMovimientoContratoAlq.setMovimientoContratoAlq(null);
                    oldMovimientosOfMovimientoContratoAlq = em.merge(oldMovimientosOfMovimientoContratoAlq);
                }
                movimientoContratoAlq.setMovimientos(movimientos);
                movimientoContratoAlq = em.merge(movimientoContratoAlq);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Movimientos movimientos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimientos persistentMovimientos = em.find(Movimientos.class, movimientos.getId());
            MovimientoProp movimientoPropOld = persistentMovimientos.getMovimientoProp();
            MovimientoProp movimientoPropNew = movimientos.getMovimientoProp();
            MovimientoContratoAlq movimientoContratoAlqOld = persistentMovimientos.getMovimientoContratoAlq();
            MovimientoContratoAlq movimientoContratoAlqNew = movimientos.getMovimientoContratoAlq();
            List<String> illegalOrphanMessages = null;
            if (movimientoPropOld != null && !movimientoPropOld.equals(movimientoPropNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain MovimientoProp " + movimientoPropOld + " since its movimientos field is not nullable.");
            }
            if (movimientoContratoAlqOld != null && !movimientoContratoAlqOld.equals(movimientoContratoAlqNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain MovimientoContratoAlq " + movimientoContratoAlqOld + " since its movimientos field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (movimientoPropNew != null) {
                movimientoPropNew = em.getReference(movimientoPropNew.getClass(), movimientoPropNew.getIdMov());
                movimientos.setMovimientoProp(movimientoPropNew);
            }
            if (movimientoContratoAlqNew != null) {
                movimientoContratoAlqNew = em.getReference(movimientoContratoAlqNew.getClass(), movimientoContratoAlqNew.getIdMov());
                movimientos.setMovimientoContratoAlq(movimientoContratoAlqNew);
            }
            movimientos = em.merge(movimientos);
            if (movimientoPropNew != null && !movimientoPropNew.equals(movimientoPropOld)) {
                Movimientos oldMovimientosOfMovimientoProp = movimientoPropNew.getMovimientos();
                if (oldMovimientosOfMovimientoProp != null) {
                    oldMovimientosOfMovimientoProp.setMovimientoProp(null);
                    oldMovimientosOfMovimientoProp = em.merge(oldMovimientosOfMovimientoProp);
                }
                movimientoPropNew.setMovimientos(movimientos);
                movimientoPropNew = em.merge(movimientoPropNew);
            }
            if (movimientoContratoAlqNew != null && !movimientoContratoAlqNew.equals(movimientoContratoAlqOld)) {
                Movimientos oldMovimientosOfMovimientoContratoAlq = movimientoContratoAlqNew.getMovimientos();
                if (oldMovimientosOfMovimientoContratoAlq != null) {
                    oldMovimientosOfMovimientoContratoAlq.setMovimientoContratoAlq(null);
                    oldMovimientosOfMovimientoContratoAlq = em.merge(oldMovimientosOfMovimientoContratoAlq);
                }
                movimientoContratoAlqNew.setMovimientos(movimientos);
                movimientoContratoAlqNew = em.merge(movimientoContratoAlqNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimientos.getId();
                if (findMovimientos(id) == null) {
                    throw new NonexistentEntityException("The movimientos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimientos movimientos;
            try {
                movimientos = em.getReference(Movimientos.class, id);
                movimientos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimientos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            MovimientoProp movimientoPropOrphanCheck = movimientos.getMovimientoProp();
            if (movimientoPropOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Movimientos (" + movimientos + ") cannot be destroyed since the MovimientoProp " + movimientoPropOrphanCheck + " in its movimientoProp field has a non-nullable movimientos field.");
            }
            MovimientoContratoAlq movimientoContratoAlqOrphanCheck = movimientos.getMovimientoContratoAlq();
            if (movimientoContratoAlqOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Movimientos (" + movimientos + ") cannot be destroyed since the MovimientoContratoAlq " + movimientoContratoAlqOrphanCheck + " in its movimientoContratoAlq field has a non-nullable movimientos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(movimientos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Movimientos> findMovimientosEntities() {
        return findMovimientosEntities(true, -1, -1);
    }

    public List<Movimientos> findMovimientosEntities(int maxResults, int firstResult) {
        return findMovimientosEntities(false, maxResults, firstResult);
    }

    private List<Movimientos> findMovimientosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movimientos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Movimientos findMovimientos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimientos.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movimientos> rt = cq.from(Movimientos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
