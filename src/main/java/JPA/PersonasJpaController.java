/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import DAO.Clientes;
import DAO.ContratoAlq;
import DAO.MovimientoProp;
import DAO.Personas;
import DAO.Usuarios;
import DAO.exceptions.IllegalOrphanException;
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
public class PersonasJpaController implements Serializable {

    public PersonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;



    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personas personas) {
        if (personas.getMovimientoPropCollection() == null) {
            personas.setMovimientoPropCollection(new ArrayList<MovimientoProp>());
        }
        if (personas.getContratoAlqCollection() == null) {
            personas.setContratoAlqCollection(new ArrayList<ContratoAlq>());
        }
        if (personas.getContratoAlqCollection1() == null) {
            personas.setContratoAlqCollection1(new ArrayList<ContratoAlq>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes = personas.getClientes();
            if (clientes != null) {
                clientes = em.getReference(clientes.getClass(), clientes.getIdPersona());
                personas.setClientes(clientes);
            }
            Usuarios usuarios = personas.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getIdPersona());
                personas.setUsuarios(usuarios);
            }
            Collection<MovimientoProp> attachedMovimientoPropCollection = new ArrayList<MovimientoProp>();
            for (MovimientoProp movimientoPropCollectionMovimientoPropToAttach : personas.getMovimientoPropCollection()) {
                movimientoPropCollectionMovimientoPropToAttach = em.getReference(movimientoPropCollectionMovimientoPropToAttach.getClass(), movimientoPropCollectionMovimientoPropToAttach.getIdMov());
                attachedMovimientoPropCollection.add(movimientoPropCollectionMovimientoPropToAttach);
            }
            personas.setMovimientoPropCollection(attachedMovimientoPropCollection);
            Collection<ContratoAlq> attachedContratoAlqCollection = new ArrayList<ContratoAlq>();
            for (ContratoAlq contratoAlqCollectionContratoAlqToAttach : personas.getContratoAlqCollection()) {
                contratoAlqCollectionContratoAlqToAttach = em.getReference(contratoAlqCollectionContratoAlqToAttach.getClass(), contratoAlqCollectionContratoAlqToAttach.getId());
                attachedContratoAlqCollection.add(contratoAlqCollectionContratoAlqToAttach);
            }
            personas.setContratoAlqCollection(attachedContratoAlqCollection);
            Collection<ContratoAlq> attachedContratoAlqCollection1 = new ArrayList<ContratoAlq>();
            for (ContratoAlq contratoAlqCollection1ContratoAlqToAttach : personas.getContratoAlqCollection1()) {
                contratoAlqCollection1ContratoAlqToAttach = em.getReference(contratoAlqCollection1ContratoAlqToAttach.getClass(), contratoAlqCollection1ContratoAlqToAttach.getId());
                attachedContratoAlqCollection1.add(contratoAlqCollection1ContratoAlqToAttach);
            }
            personas.setContratoAlqCollection1(attachedContratoAlqCollection1);
            em.persist(personas);
            if (clientes != null) {
                Personas oldPersonasOfClientes = clientes.getPersonas();
                if (oldPersonasOfClientes != null) {
                    oldPersonasOfClientes.setClientes(null);
                    oldPersonasOfClientes = em.merge(oldPersonasOfClientes);
                }
                clientes.setPersonas(personas);
                clientes = em.merge(clientes);
            }
            if (usuarios != null) {
                Personas oldPersonasOfUsuarios = usuarios.getPersonas();
                if (oldPersonasOfUsuarios != null) {
                    oldPersonasOfUsuarios.setUsuarios(null);
                    oldPersonasOfUsuarios = em.merge(oldPersonasOfUsuarios);
                }
                usuarios.setPersonas(personas);
                usuarios = em.merge(usuarios);
            }
            for (MovimientoProp movimientoPropCollectionMovimientoProp : personas.getMovimientoPropCollection()) {
                Personas oldIdPropOfMovimientoPropCollectionMovimientoProp = movimientoPropCollectionMovimientoProp.getIdProp();
                movimientoPropCollectionMovimientoProp.setIdProp(personas);
                movimientoPropCollectionMovimientoProp = em.merge(movimientoPropCollectionMovimientoProp);
                if (oldIdPropOfMovimientoPropCollectionMovimientoProp != null) {
                    oldIdPropOfMovimientoPropCollectionMovimientoProp.getMovimientoPropCollection().remove(movimientoPropCollectionMovimientoProp);
                    oldIdPropOfMovimientoPropCollectionMovimientoProp = em.merge(oldIdPropOfMovimientoPropCollectionMovimientoProp);
                }
            }
            for (ContratoAlq contratoAlqCollectionContratoAlq : personas.getContratoAlqCollection()) {
                Personas oldIdPropOfContratoAlqCollectionContratoAlq = contratoAlqCollectionContratoAlq.getIdProp();
                contratoAlqCollectionContratoAlq.setIdProp(personas);
                contratoAlqCollectionContratoAlq = em.merge(contratoAlqCollectionContratoAlq);
                if (oldIdPropOfContratoAlqCollectionContratoAlq != null) {
                    oldIdPropOfContratoAlqCollectionContratoAlq.getContratoAlqCollection().remove(contratoAlqCollectionContratoAlq);
                    oldIdPropOfContratoAlqCollectionContratoAlq = em.merge(oldIdPropOfContratoAlqCollectionContratoAlq);
                }
            }
            for (ContratoAlq contratoAlqCollection1ContratoAlq : personas.getContratoAlqCollection1()) {
                Personas oldIdInqOfContratoAlqCollection1ContratoAlq = contratoAlqCollection1ContratoAlq.getIdInq();
                contratoAlqCollection1ContratoAlq.setIdInq(personas);
                contratoAlqCollection1ContratoAlq = em.merge(contratoAlqCollection1ContratoAlq);
                if (oldIdInqOfContratoAlqCollection1ContratoAlq != null) {
                    oldIdInqOfContratoAlqCollection1ContratoAlq.getContratoAlqCollection1().remove(contratoAlqCollection1ContratoAlq);
                    oldIdInqOfContratoAlqCollection1ContratoAlq = em.merge(oldIdInqOfContratoAlqCollection1ContratoAlq);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personas personas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas persistentPersonas = em.find(Personas.class, personas.getId());
            Clientes clientesOld = persistentPersonas.getClientes();
            Clientes clientesNew = personas.getClientes();
            Usuarios usuariosOld = persistentPersonas.getUsuarios();
            Usuarios usuariosNew = personas.getUsuarios();
            Collection<MovimientoProp> movimientoPropCollectionOld = persistentPersonas.getMovimientoPropCollection();
            Collection<MovimientoProp> movimientoPropCollectionNew = personas.getMovimientoPropCollection();
            Collection<ContratoAlq> contratoAlqCollectionOld = persistentPersonas.getContratoAlqCollection();
            Collection<ContratoAlq> contratoAlqCollectionNew = personas.getContratoAlqCollection();
            Collection<ContratoAlq> contratoAlqCollection1Old = persistentPersonas.getContratoAlqCollection1();
            Collection<ContratoAlq> contratoAlqCollection1New = personas.getContratoAlqCollection1();
            List<String> illegalOrphanMessages = null;
            if (clientesOld != null && !clientesOld.equals(clientesNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Clientes " + clientesOld + " since its personas field is not nullable.");
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Usuarios " + usuariosOld + " since its personas field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientesNew != null) {
                clientesNew = em.getReference(clientesNew.getClass(), clientesNew.getIdPersona());
                personas.setClientes(clientesNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getIdPersona());
                personas.setUsuarios(usuariosNew);
            }
            Collection<MovimientoProp> attachedMovimientoPropCollectionNew = new ArrayList<MovimientoProp>();
            for (MovimientoProp movimientoPropCollectionNewMovimientoPropToAttach : movimientoPropCollectionNew) {
                movimientoPropCollectionNewMovimientoPropToAttach = em.getReference(movimientoPropCollectionNewMovimientoPropToAttach.getClass(), movimientoPropCollectionNewMovimientoPropToAttach.getIdMov());
                attachedMovimientoPropCollectionNew.add(movimientoPropCollectionNewMovimientoPropToAttach);
            }
            movimientoPropCollectionNew = attachedMovimientoPropCollectionNew;
            personas.setMovimientoPropCollection(movimientoPropCollectionNew);
            Collection<ContratoAlq> attachedContratoAlqCollectionNew = new ArrayList<ContratoAlq>();
            for (ContratoAlq contratoAlqCollectionNewContratoAlqToAttach : contratoAlqCollectionNew) {
                contratoAlqCollectionNewContratoAlqToAttach = em.getReference(contratoAlqCollectionNewContratoAlqToAttach.getClass(), contratoAlqCollectionNewContratoAlqToAttach.getId());
                attachedContratoAlqCollectionNew.add(contratoAlqCollectionNewContratoAlqToAttach);
            }
            contratoAlqCollectionNew = attachedContratoAlqCollectionNew;
            personas.setContratoAlqCollection(contratoAlqCollectionNew);
            Collection<ContratoAlq> attachedContratoAlqCollection1New = new ArrayList<ContratoAlq>();
            for (ContratoAlq contratoAlqCollection1NewContratoAlqToAttach : contratoAlqCollection1New) {
                contratoAlqCollection1NewContratoAlqToAttach = em.getReference(contratoAlqCollection1NewContratoAlqToAttach.getClass(), contratoAlqCollection1NewContratoAlqToAttach.getId());
                attachedContratoAlqCollection1New.add(contratoAlqCollection1NewContratoAlqToAttach);
            }
            contratoAlqCollection1New = attachedContratoAlqCollection1New;
            personas.setContratoAlqCollection1(contratoAlqCollection1New);
            personas = em.merge(personas);
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                Personas oldPersonasOfClientes = clientesNew.getPersonas();
                if (oldPersonasOfClientes != null) {
                    oldPersonasOfClientes.setClientes(null);
                    oldPersonasOfClientes = em.merge(oldPersonasOfClientes);
                }
                clientesNew.setPersonas(personas);
                clientesNew = em.merge(clientesNew);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                Personas oldPersonasOfUsuarios = usuariosNew.getPersonas();
                if (oldPersonasOfUsuarios != null) {
                    oldPersonasOfUsuarios.setUsuarios(null);
                    oldPersonasOfUsuarios = em.merge(oldPersonasOfUsuarios);
                }
                usuariosNew.setPersonas(personas);
                usuariosNew = em.merge(usuariosNew);
            }
            for (MovimientoProp movimientoPropCollectionOldMovimientoProp : movimientoPropCollectionOld) {
                if (!movimientoPropCollectionNew.contains(movimientoPropCollectionOldMovimientoProp)) {
                    movimientoPropCollectionOldMovimientoProp.setIdProp(null);
                    movimientoPropCollectionOldMovimientoProp = em.merge(movimientoPropCollectionOldMovimientoProp);
                }
            }
            for (MovimientoProp movimientoPropCollectionNewMovimientoProp : movimientoPropCollectionNew) {
                if (!movimientoPropCollectionOld.contains(movimientoPropCollectionNewMovimientoProp)) {
                    Personas oldIdPropOfMovimientoPropCollectionNewMovimientoProp = movimientoPropCollectionNewMovimientoProp.getIdProp();
                    movimientoPropCollectionNewMovimientoProp.setIdProp(personas);
                    movimientoPropCollectionNewMovimientoProp = em.merge(movimientoPropCollectionNewMovimientoProp);
                    if (oldIdPropOfMovimientoPropCollectionNewMovimientoProp != null && !oldIdPropOfMovimientoPropCollectionNewMovimientoProp.equals(personas)) {
                        oldIdPropOfMovimientoPropCollectionNewMovimientoProp.getMovimientoPropCollection().remove(movimientoPropCollectionNewMovimientoProp);
                        oldIdPropOfMovimientoPropCollectionNewMovimientoProp = em.merge(oldIdPropOfMovimientoPropCollectionNewMovimientoProp);
                    }
                }
            }
            for (ContratoAlq contratoAlqCollectionOldContratoAlq : contratoAlqCollectionOld) {
                if (!contratoAlqCollectionNew.contains(contratoAlqCollectionOldContratoAlq)) {
                    contratoAlqCollectionOldContratoAlq.setIdProp(null);
                    contratoAlqCollectionOldContratoAlq = em.merge(contratoAlqCollectionOldContratoAlq);
                }
            }
            for (ContratoAlq contratoAlqCollectionNewContratoAlq : contratoAlqCollectionNew) {
                if (!contratoAlqCollectionOld.contains(contratoAlqCollectionNewContratoAlq)) {
                    Personas oldIdPropOfContratoAlqCollectionNewContratoAlq = contratoAlqCollectionNewContratoAlq.getIdProp();
                    contratoAlqCollectionNewContratoAlq.setIdProp(personas);
                    contratoAlqCollectionNewContratoAlq = em.merge(contratoAlqCollectionNewContratoAlq);
                    if (oldIdPropOfContratoAlqCollectionNewContratoAlq != null && !oldIdPropOfContratoAlqCollectionNewContratoAlq.equals(personas)) {
                        oldIdPropOfContratoAlqCollectionNewContratoAlq.getContratoAlqCollection().remove(contratoAlqCollectionNewContratoAlq);
                        oldIdPropOfContratoAlqCollectionNewContratoAlq = em.merge(oldIdPropOfContratoAlqCollectionNewContratoAlq);
                    }
                }
            }
            for (ContratoAlq contratoAlqCollection1OldContratoAlq : contratoAlqCollection1Old) {
                if (!contratoAlqCollection1New.contains(contratoAlqCollection1OldContratoAlq)) {
                    contratoAlqCollection1OldContratoAlq.setIdInq(null);
                    contratoAlqCollection1OldContratoAlq = em.merge(contratoAlqCollection1OldContratoAlq);
                }
            }
            for (ContratoAlq contratoAlqCollection1NewContratoAlq : contratoAlqCollection1New) {
                if (!contratoAlqCollection1Old.contains(contratoAlqCollection1NewContratoAlq)) {
                    Personas oldIdInqOfContratoAlqCollection1NewContratoAlq = contratoAlqCollection1NewContratoAlq.getIdInq();
                    contratoAlqCollection1NewContratoAlq.setIdInq(personas);
                    contratoAlqCollection1NewContratoAlq = em.merge(contratoAlqCollection1NewContratoAlq);
                    if (oldIdInqOfContratoAlqCollection1NewContratoAlq != null && !oldIdInqOfContratoAlqCollection1NewContratoAlq.equals(personas)) {
                        oldIdInqOfContratoAlqCollection1NewContratoAlq.getContratoAlqCollection1().remove(contratoAlqCollection1NewContratoAlq);
                        oldIdInqOfContratoAlqCollection1NewContratoAlq = em.merge(oldIdInqOfContratoAlqCollection1NewContratoAlq);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personas.getId();
                if (findPersonas(id) == null) {
                    throw new NonexistentEntityException("The personas with id " + id + " no longer exists.");
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
            Personas personas;
            try {
                personas = em.getReference(Personas.class, id);
                personas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Clientes clientesOrphanCheck = personas.getClientes();
            if (clientesOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Clientes " + clientesOrphanCheck + " in its clientes field has a non-nullable personas field.");
            }
            Usuarios usuariosOrphanCheck = personas.getUsuarios();
            if (usuariosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Usuarios " + usuariosOrphanCheck + " in its usuarios field has a non-nullable personas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<MovimientoProp> movimientoPropCollection = personas.getMovimientoPropCollection();
            for (MovimientoProp movimientoPropCollectionMovimientoProp : movimientoPropCollection) {
                movimientoPropCollectionMovimientoProp.setIdProp(null);
                movimientoPropCollectionMovimientoProp = em.merge(movimientoPropCollectionMovimientoProp);
            }
            Collection<ContratoAlq> contratoAlqCollection = personas.getContratoAlqCollection();
            for (ContratoAlq contratoAlqCollectionContratoAlq : contratoAlqCollection) {
                contratoAlqCollectionContratoAlq.setIdProp(null);
                contratoAlqCollectionContratoAlq = em.merge(contratoAlqCollectionContratoAlq);
            }
            Collection<ContratoAlq> contratoAlqCollection1 = personas.getContratoAlqCollection1();
            for (ContratoAlq contratoAlqCollection1ContratoAlq : contratoAlqCollection1) {
                contratoAlqCollection1ContratoAlq.setIdInq(null);
                contratoAlqCollection1ContratoAlq = em.merge(contratoAlqCollection1ContratoAlq);
            }
            em.remove(personas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personas> findPersonasEntities() {
        return findPersonasEntities(true, -1, -1);
    }

    public List<Personas> findPersonasEntities(int maxResults, int firstResult) {
        return findPersonasEntities(false, maxResults, firstResult);
    }

    private List<Personas> findPersonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personas.class));
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

    public Personas findPersonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personas> rt = cq.from(Personas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
