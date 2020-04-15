package com.sgaraba.library.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Circuito.
 */
@Entity
@Table(name = "circuito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Circuito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_controle_designacao")
    private String idControleDesignacao;

    @Column(name = "id_estacao")
    private String idEstacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdControleDesignacao() {
        return idControleDesignacao;
    }

    public Circuito idControleDesignacao(String idControleDesignacao) {
        this.idControleDesignacao = idControleDesignacao;
        return this;
    }

    public void setIdControleDesignacao(String idControleDesignacao) {
        this.idControleDesignacao = idControleDesignacao;
    }

    public String getIdEstacao() {
        return idEstacao;
    }

    public Circuito idEstacao(String idEstacao) {
        this.idEstacao = idEstacao;
        return this;
    }

    public void setIdEstacao(String idEstacao) {
        this.idEstacao = idEstacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Circuito)) {
            return false;
        }
        return id != null && id.equals(((Circuito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Circuito{" +
            "id=" + getId() +
            ", idControleDesignacao='" + getIdControleDesignacao() + "'" +
            ", idEstacao='" + getIdEstacao() + "'" +
            "}";
    }
}
