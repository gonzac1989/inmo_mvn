/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import DAO.ContratoAlq;
import DAO.MovimientoContratoAlq;
import DAO.Personas;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author e274263
 */
public class ContratoAlqJpaController implements Serializable {

    public ContratoAlqJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratoAlq contratoAlq) {
        if (contratoAlq.getMovimientoContratoAlqCollection() == null) {
            contratoAlq.setMovimientoContratoAlqCollection(new ArrayList<MovimientoContratoAlq>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas idProp = contratoAlq.getIdProp();
            if (idProp != null) {
                idProp = em.getReference(idProp.getClass(), idProp.getId());
                contratoAlq.setIdProp(idProp);
            }
            Personas idInq = contratoAlq.getIdInq();
            if (idInq != null) {
                idInq = em.getReference(idInq.getClass(), idInq.getId());
                contratoAlq.setIdInq(idInq);
            }
            Collection<MovimientoContratoAlq> attachedMovimientoContratoAlqCollection = new ArrayList<MovimientoContratoAlq>();
            for (MovimientoContratoAlq movimientoContratoAlqCollectionMovimientoContratoAlqToAttach : contratoAlq.getMovimientoContratoAlqCollection()) {
                movimientoContratoAlqCollectionMovimientoContratoAlqToAttach = em.getReference(movimientoContratoAlqCollectionMovimientoContratoAlqToAttach.getClass(), movimientoContratoAlqCollectionMovimientoContratoAlqToAttach.getIdMov());
                attachedMovimientoContratoAlqCollection.add(movimientoContratoAlqCollectionMovimientoContratoAlqToAttach);
            }
            contratoAlq.setMovimientoContratoAlqCollection(attachedMovimientoContratoAlqCollection);
            em.persist(contratoAlq);
            if (idProp != null) {
                idProp.getContratoAlqCollection().add(contratoAlq);
                idProp = em.merge(idProp);
            }
            if (idInq != null) {
                idInq.getContratoAlqCollection().add(contratoAlq);
                idInq = em.merge(idInq);
            }
            for (MovimientoContratoAlq movimientoContratoAlqCollectionMovimientoContratoAlq : contratoAlq.getMovimientoContratoAlqCollection()) {
                ContratoAlq oldIdContratoOfMovimientoContratoAlqCollectionMovimientoContratoAlq = movimientoContratoAlqCollectionMovimientoContratoAlq.getIdContrato();
                movimientoContratoAlqCollectionMovimientoContratoAlq.setIdContrato(contratoAlq);
                movimientoContratoAlqCollectionMovimientoContratoAlq = em.merge(movimientoContratoAlqCollectionMovimientoContratoAlq);
                if (oldIdContratoOfMovimientoContratoAlqCollectionMovimientoContratoAlq != null) {
                    oldIdContratoOfMovimientoContratoAlqCollectionMovimientoContratoAlq.getMovimientoContratoAlqCollection().remove(movimientoContratoAlqCollectionMovimientoContratoAlq);
                    oldIdContratoOfMovimientoContratoAlqCollectionMovimientoContratoAlq = em.merge(oldIdContratoOfMovimientoContratoAlqCollectionMovimientoContratoAlq);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratoAlq contratoAlq) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoAlq persistentContratoAlq = em.find(ContratoAlq.class, contratoAlq.getId());
            Personas idPropOld = persistentContratoAlq.getIdProp();
            Personas idPropNew = contratoAlq.getIdProp();
            Personas idInqOld = persistentContratoAlq.getIdInq();
            Personas idInqNew = contratoAlq.getIdInq();
            Collection<MovimientoContratoAlq> movimientoContratoAlqCollectionOld = persistentContratoAlq.getMovimientoContratoAlqCollection();
            Collection<MovimientoContratoAlq> movimientoContratoAlqCollectionNew = contratoAlq.getMovimientoContratoAlqCollection();
            if (idPropNew != null) {
                idPropNew = em.getReference(idPropNew.getClass(), idPropNew.getId());
                contratoAlq.setIdProp(idPropNew);
            }
            if (idInqNew != null) {
                idInqNew = em.getReference(idInqNew.getClass(), idInqNew.getId());
                contratoAlq.setIdInq(idInqNew);
            }
            Collection<MovimientoContratoAlq> attachedMovimientoContratoAlqCollectionNew = new ArrayList<MovimientoContratoAlq>();
            for (MovimientoContratoAlq movimientoContratoAlqCollectionNewMovimientoContratoAlqToAttach : movimientoContratoAlqCollectionNew) {
                movimientoContratoAlqCollectionNewMovimientoContratoAlqToAttach = em.getReference(movimientoContratoAlqCollectionNewMovimientoContratoAlqToAttach.getClass(), movimientoContratoAlqCollectionNewMovimientoContratoAlqToAttach.getIdMov());
                attachedMovimientoContratoAlqCollectionNew.add(movimientoContratoAlqCollectionNewMovimientoContratoAlqToAttach);
            }
            movimientoContratoAlqCollectionNew = attachedMovimientoContratoAlqCollectionNew;
            contratoAlq.setMovimientoContratoAlqCollection(movimientoContratoAlqCollectionNew);
            contratoAlq = em.merge(contratoAlq);
            if (idPropOld != null && !idPropOld.equals(idPropNew)) {
                idPropOld.getContratoAlqCollection().remove(contratoAlq);
                idPropOld = em.merge(idPropOld);
            }
            if (idPropNew != null && !idPropNew.equals(idPropOld)) {
                idPropNew.getContratoAlqCollection().add(contratoAlq);
                idPropNew = em.merge(idPropNew);
            }
            if (idInqOld != null && !idInqOld.equals(idInqNew)) {
                idInqOld.getContratoAlqCollection().remove(contratoAlq);
                idInqOld = em.merge(idInqOld);
            }
            if (idInqNew != null && !idInqNew.equals(idInqOld)) {
                idInqNew.getContratoAlqCollection().add(contratoAlq);
                idInqNew = em.merge(idInqNew);
            }
            for (MovimientoContratoAlq movimientoContratoAlqCollectionOldMovimientoContratoAlq : movimientoContratoAlqCollectionOld) {
                if (!movimientoContratoAlqCollectionNew.contains(movimientoContratoAlqCollectionOldMovimientoContratoAlq)) {
                    movimientoContratoAlqCollectionOldMovimientoContratoAlq.setIdContrato(null);
                    movimientoContratoAlqCollectionOldMovimientoContratoAlq = em.merge(movimientoContratoAlqCollectionOldMovimientoContratoAlq);
                }
            }
            for (MovimientoContratoAlq movimientoContratoAlqCollectionNewMovimientoContratoAlq : movimientoContratoAlqCollectionNew) {
                if (!movimientoContratoAlqCollectionOld.contains(movimientoContratoAlqCollectionNewMovimientoContratoAlq)) {
                    ContratoAlq oldIdContratoOfMovimientoContratoAlqCollectionNewMovimientoContratoAlq = movimientoContratoAlqCollectionNewMovimientoContratoAlq.getIdContrato();
                    movimientoContratoAlqCollectionNewMovimientoContratoAlq.setIdContrato(contratoAlq);
                    movimientoContratoAlqCollectionNewMovimientoContratoAlq = em.merge(movimientoContratoAlqCollectionNewMovimientoContratoAlq);
                    if (oldIdContratoOfMovimientoContratoAlqCollectionNewMovimientoContratoAlq != null && !oldIdContratoOfMovimientoContratoAlqCollectionNewMovimientoContratoAlq.equals(contratoAlq)) {
                        oldIdContratoOfMovimientoContratoAlqCollectionNewMovimientoContratoAlq.getMovimientoContratoAlqCollection().remove(movimientoContratoAlqCollectionNewMovimientoContratoAlq);
                        oldIdContratoOfMovimientoContratoAlqCollectionNewMovimientoContratoAlq = em.merge(oldIdContratoOfMovimientoContratoAlqCollectionNewMovimientoContratoAlq);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratoAlq.getId();
                if (findContratoAlq(id) == null) {
                    throw new NonexistentEntityException("The contratoAlq with id " + id + " no longer exists.");
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
            ContratoAlq contratoAlq;
            try {
                contratoAlq = em.getReference(ContratoAlq.class, id);
                contratoAlq.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratoAlq with id " + id + " no longer exists.", enfe);
            }
            Personas idProp = contratoAlq.getIdProp();
            if (idProp != null) {
                idProp.getContratoAlqCollection().remove(contratoAlq);
                idProp = em.merge(idProp);
            }
            Personas idInq = contratoAlq.getIdInq();
            if (idInq != null) {
                idInq.getContratoAlqCollection().remove(contratoAlq);
                idInq = em.merge(idInq);
            }
            Collection<MovimientoContratoAlq> movimientoContratoAlqCollection = contratoAlq.getMovimientoContratoAlqCollection();
            for (MovimientoContratoAlq movimientoContratoAlqCollectionMovimientoContratoAlq : movimientoContratoAlqCollection) {
                movimientoContratoAlqCollectionMovimientoContratoAlq.setIdContrato(null);
                movimientoContratoAlqCollectionMovimientoContratoAlq = em.merge(movimientoContratoAlqCollectionMovimientoContratoAlq);
            }
            em.remove(contratoAlq);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratoAlq> findContratoAlqEntities() {
        return findContratoAlqEntities(true, -1, -1);
    }

    public List<ContratoAlq> findContratoAlqEntities(int maxResults, int firstResult) {
        return findContratoAlqEntities(false, maxResults, firstResult);
    }

    private List<ContratoAlq> findContratoAlqEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoAlq.class));
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

    public ContratoAlq findContratoAlq(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoAlq.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoAlqCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoAlq> rt = cq.from(ContratoAlq.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
