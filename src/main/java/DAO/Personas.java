/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author e274263
 */
@Entity
@Table(name = "personas")
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findById", query = "SELECT p FROM Personas p WHERE p.id = :id"),
    @NamedQuery(name = "Personas.findByNombre", query = "SELECT p FROM Personas p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Personas.findByApellido", query = "SELECT p FROM Personas p WHERE p.apellido = :apellido")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @OneToMany(mappedBy = "idProp")
    private Collection<MovimientoProp> movimientoPropCollection;
    @OneToMany(mappedBy = "idProp")
    private Collection<ContratoAlq> contratoAlqCollection;
    @OneToMany(mappedBy = "idInq")
    private Collection<ContratoAlq> contratoAlqCollection1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "personas")
    private Clientes clientes;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "personas")
    private Usuarios usuarios;

    public Personas() {
    }

    public Personas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Collection<MovimientoProp> getMovimientoPropCollection() {
        return movimientoPropCollection;
    }

    public void setMovimientoPropCollection(Collection<MovimientoProp> movimientoPropCollection) {
        this.movimientoPropCollection = movimientoPropCollection;
    }

    public Collection<ContratoAlq> getContratoAlqCollection() {
        return contratoAlqCollection;
    }

    public void setContratoAlqCollection(Collection<ContratoAlq> contratoAlqCollection) {
        this.contratoAlqCollection = contratoAlqCollection;
    }

    public Collection<ContratoAlq> getContratoAlqCollection1() {
        return contratoAlqCollection1;
    }

    public void setContratoAlqCollection1(Collection<ContratoAlq> contratoAlqCollection1) {
        this.contratoAlqCollection1 = contratoAlqCollection1;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.Personas[ id=" + id + " ]";
    }
    
}
