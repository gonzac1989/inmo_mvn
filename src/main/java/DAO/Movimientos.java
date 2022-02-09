/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author e274263
 */
@Entity
@Table(name = "movimientos")
@NamedQueries({
    @NamedQuery(name = "Movimientos.findAll", query = "SELECT m FROM Movimientos m"),
    @NamedQuery(name = "Movimientos.findById", query = "SELECT m FROM Movimientos m WHERE m.id = :id"),
    @NamedQuery(name = "Movimientos.findByFecha", query = "SELECT m FROM Movimientos m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "Movimientos.findByMonto", query = "SELECT m FROM Movimientos m WHERE m.monto = :monto"),
    @NamedQuery(name = "Movimientos.findByTipo", query = "SELECT m FROM Movimientos m WHERE m.tipo = :tipo")})
public class Movimientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Float monto;
    @Column(name = "tipo")
    private String tipo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "movimientos")
    private MovimientoProp movimientoProp;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "movimientos")
    private MovimientoContratoAlq movimientoContratoAlq;

    public Movimientos() {
    }

    public Movimientos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public MovimientoProp getMovimientoProp() {
        return movimientoProp;
    }

    public void setMovimientoProp(MovimientoProp movimientoProp) {
        this.movimientoProp = movimientoProp;
    }

    public MovimientoContratoAlq getMovimientoContratoAlq() {
        return movimientoContratoAlq;
    }

    public void setMovimientoContratoAlq(MovimientoContratoAlq movimientoContratoAlq) {
        this.movimientoContratoAlq = movimientoContratoAlq;
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
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.Movimientos[ id=" + id + " ]";
    }
    
}
