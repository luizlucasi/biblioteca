package com.sgaraba.library.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NotaFiscal.
 */
@Entity
@Table(name = "nota_fiscal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NotaFiscal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "competencia")
    private String competencia;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "servico")
    private String servico;

    @Column(name = "condicao_pagamento")
    private String condicaoPagamento;

    @Column(name = "total_nf")
    private Double totalNF;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public NotaFiscal numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCompetencia() {
        return competencia;
    }

    public NotaFiscal competencia(String competencia) {
        this.competencia = competencia;
        return this;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getTipo() {
        return tipo;
    }

    public NotaFiscal tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getServico() {
        return servico;
    }

    public NotaFiscal servico(String servico) {
        this.servico = servico;
        return this;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public NotaFiscal condicaoPagamento(String condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
        return this;
    }

    public void setCondicaoPagamento(String condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public Double getTotalNF() {
        return totalNF;
    }

    public NotaFiscal totalNF(Double totalNF) {
        this.totalNF = totalNF;
        return this;
    }

    public void setTotalNF(Double totalNF) {
        this.totalNF = totalNF;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotaFiscal)) {
            return false;
        }
        return id != null && id.equals(((NotaFiscal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NotaFiscal{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", competencia='" + getCompetencia() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", servico='" + getServico() + "'" +
            ", condicaoPagamento='" + getCondicaoPagamento() + "'" +
            ", totalNF=" + getTotalNF() +
            "}";
    }
}
