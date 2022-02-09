/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import DAO.MovimientoProp;
import DAO.Movimientos;
import DAO.Personas;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
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
public class MovimientoPropJpaController implements Serializable {

    public MovimientoPropJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MovimientoProp movimientoProp) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Movimientos movimientosOrphanCheck = movimientoProp.getMovimientos();
        if (movimientosOrphanCheck != null) {
            MovimientoProp oldMovimientoPropOfMovimientos = movimientosOrphanCheck.getMovimientoProp();
            if (oldMovimientoPropOfMovimientos != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Movimientos " + movimientosOrphanCheck + " already has an item of type MovimientoProp whose movimientos column cannot be null. Please make another selection for the movimientos field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimientos movimientos = movimientoProp.getMovimientos();
            if (movimientos != null) {
                movimientos = em.getReference(movimientos.getClass(), movimientos.getId());
                movimientoProp.setMovimientos(movimientos);
            }
            Personas idProp = movimientoProp.getIdProp();
            if (idProp != null) {
                idProp = em.getReference(idProp.getClass(), idProp.getId());
                movimientoProp.setIdProp(idProp);
            }
            em.persist(movimientoProp);
            if (movimientos != null) {
                movimientos.setMovimientoProp(movimientoProp);
                movimientos = em.merge(movimientos);
            }
            if (idProp != null) {
                idProp.getMovimientoPropCollection().add(movimientoProp);
                idProp = em.merge(idProp);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMovimientoProp(movimientoProp.getIdMov()) != null) {
                throw new PreexistingEntityException("MovimientoProp " + movimientoProp + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MovimientoProp movimientoProp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoProp persistentMovimientoProp = em.find(MovimientoProp.class, movimientoProp.getIdMov());
            Movimientos movimientosOld = persistentMovimientoProp.getMovimientos();
            Movimientos movimientosNew = movimientoProp.getMovimientos();
            Personas idPropOld = persistentMovimientoProp.getIdProp();
            Personas idPropNew = movimientoProp.getIdProp();
            List<String> illegalOrphanMessages = null;
            if (movimientosNew != null && !movimientosNew.equals(movimientosOld)) {
                MovimientoProp oldMovimientoPropOfMovimientos = movimientosNew.getMovimientoProp();
                if (oldMovimientoPropOfMovimientos != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Movimientos " + movimientosNew + " already has an item of type MovimientoProp whose movimientos column cannot be null. Please make another selection for the movimientos field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (movimientosNew != null) {
                movimientosNew = em.getReference(movimientosNew.getClass(), movimientosNew.getId());
                movimientoProp.setMovimientos(movimientosNew);
            }
            if (idPropNew != null) {
                idPropNew = em.getReference(idPropNew.getClass(), idPropNew.getId());
                movimientoProp.setIdProp(idPropNew);
            }
            movimientoProp = em.merge(movimientoProp);
            if (movimientosOld != null && !movimientosOld.equals(movimientosNew)) {
                movimientosOld.setMovimientoProp(null);
                movimientosOld = em.merge(movimientosOld);
            }
            if (movimientosNew != null && !movimientosNew.equals(movimientosOld)) {
                movimientosNew.setMovimientoProp(movimientoProp);
                movimientosNew = em.merge(movimientosNew);
            }
            if (idPropOld != null && !idPropOld.equals(idPropNew)) {
                idPropOld.getMovimientoPropCollection().remove(movimientoProp);
                idPropOld = em.merge(idPropOld);
            }
            if (idPropNew != null && !idPropNew.equals(idPropOld)) {
                idPropNew.getMovimientoPropCollection().add(movimientoProp);
                idPropNew = em.merge(idPropNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimientoProp.getIdMov();
                if (findMovimientoProp(id) == null) {
                    throw new NonexistentEntityException("The movimientoProp with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoProp movimientoProp;
            try {
                movimientoProp = em.getReference(MovimientoProp.class, id);
                movimientoProp.getIdMov();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimientoProp with id " + id + " no longer exists.", enfe);
            }
            Movimientos movimientos = movimientoProp.getMovimientos();
            if (movimientos != null) {
                movimientos.setMovimientoProp(null);
                movimientos = em.merge(movimientos);
            }
            Personas idProp = movimientoProp.getIdProp();
            if (idProp != null) {
                idProp.getMovimientoPropCollection().remove(movimientoProp);
                idProp = em.merge(idProp);
            }
            em.remove(movimientoProp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MovimientoProp> findMovimientoPropEntities() {
        return findMovimientoPropEntities(true, -1, -1);
    }

    public List<MovimientoProp> findMovimientoPropEntities(int maxResults, int firstResult) {
        return findMovimientoPropEntities(false, maxResults, firstResult);
    }

    private List<MovimientoProp> findMovimientoPropEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MovimientoProp.class));
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

    public MovimientoProp findMovimientoProp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MovimientoProp.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientoPropCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MovimientoProp> rt = cq.from(MovimientoProp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
