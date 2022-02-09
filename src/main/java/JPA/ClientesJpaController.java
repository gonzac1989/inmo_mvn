/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import DAO.Clientes;
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
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Personas personasOrphanCheck = clientes.getPersonas();
        if (personasOrphanCheck != null) {
            Clientes oldClientesOfPersonas = personasOrphanCheck.getClientes();
            if (oldClientesOfPersonas != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Personas " + personasOrphanCheck + " already has an item of type Clientes whose personas column cannot be null. Please make another selection for the personas field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas personas = clientes.getPersonas();
            if (personas != null) {
                personas = em.getReference(personas.getClass(), personas.getId());
                clientes.setPersonas(personas);
            }
            em.persist(clientes);
            if (personas != null) {
                personas.setClientes(clientes);
                personas = em.merge(personas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getIdPersona()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getIdPersona());
            Personas personasOld = persistentClientes.getPersonas();
            Personas personasNew = clientes.getPersonas();
            List<String> illegalOrphanMessages = null;
            if (personasNew != null && !personasNew.equals(personasOld)) {
                Clientes oldClientesOfPersonas = personasNew.getClientes();
                if (oldClientesOfPersonas != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Personas " + personasNew + " already has an item of type Clientes whose personas column cannot be null. Please make another selection for the personas field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personasNew != null) {
                personasNew = em.getReference(personasNew.getClass(), personasNew.getId());
                clientes.setPersonas(personasNew);
            }
            clientes = em.merge(clientes);
            if (personasOld != null && !personasOld.equals(personasNew)) {
                personasOld.setClientes(null);
                personasOld = em.merge(personasOld);
            }
            if (personasNew != null && !personasNew.equals(personasOld)) {
                personasNew.setClientes(clientes);
                personasNew = em.merge(personasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getIdPersona();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            Personas personas = clientes.getPersonas();
            if (personas != null) {
                personas.setClientes(null);
                personas = em.merge(personas);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
