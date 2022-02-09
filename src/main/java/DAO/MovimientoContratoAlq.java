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
@Table(name = "movimiento_contrato_alq")
@NamedQueries({
    @NamedQuery(name = "MovimientoContratoAlq.findAll", query = "SELECT m FROM MovimientoContratoAlq m"),
    @NamedQuery(name = "MovimientoContratoAlq.findByIdMov", query = "SELECT m FROM MovimientoContratoAlq m WHERE m.idMov = :idMov"),
    @NamedQuery(name = "MovimientoContratoAlq.findByTipo", query = "SELECT m FROM MovimientoContratoAlq m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "MovimientoContratoAlq.findByMonto", query = "SELECT m FROM MovimientoContratoAlq m WHERE m.monto = :monto")})
public class MovimientoContratoAlq implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_mov")
    private Integer idMov;
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Float monto;
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne
    private ContratoAlq idContrato;
    @JoinColumn(name = "id_mov", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Movimientos movimientos;

    public MovimientoContratoAlq() {
    }

    public MovimientoContratoAlq(Integer idMov) {
        this.idMov = idMov;
    }

    public Integer getIdMov() {
        return idMov;
    }

    public void setIdMov(Integer idMov) {
        this.idMov = idMov;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public ContratoAlq getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoAlq idContrato) {
        this.idContrato = idContrato;
    }

    public Movimientos getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
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
        if (!(object instanceof MovimientoContratoAlq)) {
            return false;
        }
        MovimientoContratoAlq other = (MovimientoContratoAlq) object;
        if ((this.idMov == null && other.idMov != null) || (this.idMov != null && !this.idMov.equals(other.idMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.MovimientoContratoAlq[ idMov=" + idMov + " ]";
    }
    
}
