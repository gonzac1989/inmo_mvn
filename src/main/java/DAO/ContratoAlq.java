/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author e274263
 */
@Entity
@Table(name = "contrato_alq")
@NamedQueries({
    @NamedQuery(name = "ContratoAlq.findAll", query = "SELECT c FROM ContratoAlq c"),
    @NamedQuery(name = "ContratoAlq.findById", query = "SELECT c FROM ContratoAlq c WHERE c.id = :id"),
    @NamedQuery(name = "ContratoAlq.findByMonto", query = "SELECT c FROM ContratoAlq c WHERE c.monto = :monto"),
    @NamedQuery(name = "ContratoAlq.findByFechaInicio", query = "SELECT c FROM ContratoAlq c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ContratoAlq.findByPlazo", query = "SELECT c FROM ContratoAlq c WHERE c.plazo = :plazo")})
public class ContratoAlq implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Float monto;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "plazo")
    private Integer plazo;
    @JoinColumn(name = "id_prop", referencedColumnName = "id")
    @ManyToOne
    private Personas idProp;
    @JoinColumn(name = "id_inq", referencedColumnName = "id")
    @ManyToOne
    private Personas idInq;
    @OneToMany(mappedBy = "idContrato")
    private Collection<MovimientoContratoAlq> movimientoContratoAlqCollection;

    public ContratoAlq() {
    }

    public ContratoAlq(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Personas getIdProp() {
        return idProp;
    }

    public void setIdProp(Personas idProp) {
        this.idProp = idProp;
    }

    public Personas getIdInq() {
        return idInq;
    }

    public void setIdInq(Personas idInq) {
        this.idInq = idInq;
    }

    public Collection<MovimientoContratoAlq> getMovimientoContratoAlqCollection() {
        return movimientoContratoAlqCollection;
    }

    public void setMovimientoContratoAlqCollection(Collection<MovimientoContratoAlq> movimientoContratoAlqCollection) {
        this.movimientoContratoAlqCollection = movimientoContratoAlqCollection;
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
        if (!(object instanceof ContratoAlq)) {
            return false;
        }
        ContratoAlq other = (ContratoAlq) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.ContratoAlq[ id=" + id + " ]";
    }
    
}
