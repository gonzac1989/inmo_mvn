/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import DAO.ContratoAlq;
import DAO.MovimientoContratoAlq;
import DAO.Movimientos;
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
public class MovimientoContratoAlqJpaController implements Serializable {

    public MovimientoContratoAlqJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MovimientoContratoAlq movimientoContratoAlq) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Movimientos movimientosOrphanCheck = movimientoContratoAlq.getMovimientos();
        if (movimientosOrphanCheck != null) {
            MovimientoContratoAlq oldMovimientoContratoAlqOfMovimientos = movimientosOrphanCheck.getMovimientoContratoAlq();
            if (oldMovimientoContratoAlqOfMovimientos != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Movimientos " + movimientosOrphanCheck + " already has an item of type MovimientoContratoAlq whose movimientos column cannot be null. Please make another selection for the movimientos field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoAlq idContrato = movimientoContratoAlq.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                movimientoContratoAlq.setIdContrato(idContrato);
            }
            Movimientos movimientos = movimientoContratoAlq.getMovimientos();
            if (movimientos != null) {
                movimientos = em.getReference(movimientos.getClass(), movimientos.getId());
                movimientoContratoAlq.setMovimientos(movimientos);
            }
            em.persist(movimientoContratoAlq);
            if (idContrato != null) {
                idContrato.getMovimientoContratoAlqCollection().add(movimientoContratoAlq);
                idContrato = em.merge(idContrato);
            }
            if (movimientos != null) {
                movimientos.setMovimientoContratoAlq(movimientoContratoAlq);
                movimientos = em.merge(movimientos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMovimientoContratoAlq(movimientoContratoAlq.getIdMov()) != null) {
                throw new PreexistingEntityException("MovimientoContratoAlq " + movimientoContratoAlq + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MovimientoContratoAlq movimientoContratoAlq) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoContratoAlq persistentMovimientoContratoAlq = em.find(MovimientoContratoAlq.class, movimientoContratoAlq.getIdMov());
            ContratoAlq idContratoOld = persistentMovimientoContratoAlq.getIdContrato();
            ContratoAlq idContratoNew = movimientoContratoAlq.getIdContrato();
            Movimientos movimientosOld = persistentMovimientoContratoAlq.getMovimientos();
            Movimientos movimientosNew = movimientoContratoAlq.getMovimientos();
            List<String> illegalOrphanMessages = null;
            if (movimientosNew != null && !movimientosNew.equals(movimientosOld)) {
                MovimientoContratoAlq oldMovimientoContratoAlqOfMovimientos = movimientosNew.getMovimientoContratoAlq();
                if (oldMovimientoContratoAlqOfMovimientos != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Movimientos " + movimientosNew + " already has an item of type MovimientoContratoAlq whose movimientos column cannot be null. Please make another selection for the movimientos field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                movimientoContratoAlq.setIdContrato(idContratoNew);
            }
            if (movimientosNew != null) {
                movimientosNew = em.getReference(movimientosNew.getClass(), movimientosNew.getId());
                movimientoContratoAlq.setMovimientos(movimientosNew);
            }
            movimientoContratoAlq = em.merge(movimientoContratoAlq);
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getMovimientoContratoAlqCollection().remove(movimientoContratoAlq);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getMovimientoContratoAlqCollection().add(movimientoContratoAlq);
                idContratoNew = em.merge(idContratoNew);
            }
            if (movimientosOld != null && !movimientosOld.equals(movimientosNew)) {
                movimientosOld.setMovimientoContratoAlq(null);
                movimientosOld = em.merge(movimientosOld);
            }
            if (movimientosNew != null && !movimientosNew.equals(movimientosOld)) {
                movimientosNew.setMovimientoContratoAlq(movimientoContratoAlq);
                movimientosNew = em.merge(movimientosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimientoContratoAlq.getIdMov();
                if (findMovimientoContratoAlq(id) == null) {
                    throw new NonexistentEntityException("The movimientoContratoAlq with id " + id + " no longer exists.");
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
            MovimientoContratoAlq movimientoContratoAlq;
            try {
                movimientoContratoAlq = em.getReference(MovimientoContratoAlq.class, id);
                movimientoContratoAlq.getIdMov();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimientoContratoAlq with id " + id + " no longer exists.", enfe);
            }
            ContratoAlq idContrato = movimientoContratoAlq.getIdContrato();
            if (idContrato != null) {
                idContrato.getMovimientoContratoAlqCollection().remove(movimientoContratoAlq);
                idContrato = em.merge(idContrato);
            }
            Movimientos movimientos = movimientoContratoAlq.getMovimientos();
            if (movimientos != null) {
                movimientos.setMovimientoContratoAlq(null);
                movimientos = em.merge(movimientos);
            }
            em.remove(movimientoContratoAlq);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MovimientoContratoAlq> findMovimientoContratoAlqEntities() {
        return findMovimientoContratoAlqEntities(true, -1, -1);
    }

    public List<MovimientoContratoAlq> findMovimientoContratoAlqEntities(int maxResults, int firstResult) {
        return findMovimientoContratoAlqEntities(false, maxResults, firstResult);
    }

    private List<MovimientoContratoAlq> findMovimientoContratoAlqEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MovimientoContratoAlq.class));
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

    public MovimientoContratoAlq findMovimientoContratoAlq(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MovimientoContratoAlq.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientoContratoAlqCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MovimientoContratoAlq> rt = cq.from(MovimientoContratoAlq.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
