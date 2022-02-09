/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author e274263
 */
@Entity
@Table(name = "movimiento_prop")
@NamedQueries({
    @NamedQuery(name = "MovimientoProp.findAll", query = "SELECT m FROM MovimientoProp m"),
    @NamedQuery(name = "MovimientoProp.findByIdMov", query = "SELECT m FROM MovimientoProp m WHERE m.idMov = :idMov")})
public class MovimientoProp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_mov")
    private Integer idMov;
    @JoinColumn(name = "id_mov", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Movimientos movimientos;
    @JoinColumn(name = "id_prop", referencedColumnName = "id")
    @ManyToOne
    private Personas idProp;

    public MovimientoProp() {
    }

    public MovimientoProp(Integer idMov) {
        this.idMov = idMov;
    }

    public Integer getIdMov() {
        return idMov;
    }

    public void setIdMov(Integer idMov) {
        this.idMov = idMov;
    }

    public Movimientos getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
    }

    public Personas getIdProp() {
        return idProp;
    }

    public void setIdProp(Personas idProp) {
        this.idProp = idProp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMov != null ? idMov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientoProp)) {
            return false;
        }
        MovimientoProp other = (MovimientoProp) object;
        if ((this.idMov == null && other.idMov != null) || (this.idMov != null && !this.idMov.equals(other.idMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.MovimientoProp[ idMov=" + idMov + " ]";
    }
    
}
