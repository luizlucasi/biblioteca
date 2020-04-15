package com.sgaraba.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_cliente")
    private String codCliente;

    @ManyToOne
    @JsonIgnoreProperties("clientes")
    private Contrato contrato;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public Cliente codCliente(String codCliente) {
        this.codCliente = codCliente;
        return this;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public Cliente contrato(Contrato contrato) {
        this.contrato = contrato;
        return this;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", codCliente='" + getCodCliente() + "'" +
            "}";
    }
}
