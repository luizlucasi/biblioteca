package com.sgaraba.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Contrato.
 */
@Entity
@Table(name = "contrato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo_cliente_sap")
    private String codigoClienteSap;

    @Column(name = "numero_contrato_sap")
    private String numeroContratoSap;

    @Column(name = "numero_contrato_tpz")
    private String numeroContratoTpz;

    @Column(name = "nome")
    private String nome;

    @Column(name = "status")
    private String status;

    @Column(name = "data_assinatura")
    private LocalDate dataAssinatura;

    @Column(name = "data_reajuste")
    private LocalDate dataReajuste;

    @Column(name = "data_termino")
    private LocalDate dataTermino;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @Column(name = "vigencia")
    private Integer vigencia;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private NotaFiscal notaFiscal;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private Circuito circuito;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoClienteSap() {
        return codigoClienteSap;
    }

    public Contrato codigoClienteSap(String codigoClienteSap) {
        this.codigoClienteSap = codigoClienteSap;
        return this;
    }

    public void setCodigoClienteSap(String codigoClienteSap) {
        this.codigoClienteSap = codigoClienteSap;
    }

    public String getNumeroContratoSap() {
        return numeroContratoSap;
    }

    public Contrato numeroContratoSap(String numeroContratoSap) {
        this.numeroContratoSap = numeroContratoSap;
        return this;
    }

    public void setNumeroContratoSap(String numeroContratoSap) {
        this.numeroContratoSap = numeroContratoSap;
    }

    public String getNumeroContratoTpz() {
        return numeroContratoTpz;
    }

    public Contrato numeroContratoTpz(String numeroContratoTpz) {
        this.numeroContratoTpz = numeroContratoTpz;
        return this;
    }

    public void setNumeroContratoTpz(String numeroContratoTpz) {
        this.numeroContratoTpz = numeroContratoTpz;
    }

    public String getNome() {
        return nome;
    }

    public Contrato nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public Contrato status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAssinatura() {
        return dataAssinatura;
    }

    public Contrato dataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
        return this;
    }

    public void setDataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public LocalDate getDataReajuste() {
        return dataReajuste;
    }

    public Contrato dataReajuste(LocalDate dataReajuste) {
        this.dataReajuste = dataReajuste;
        return this;
    }

    public void setDataReajuste(LocalDate dataReajuste) {
        this.dataReajuste = dataReajuste;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public Contrato dataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
        return this;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getEstado() {
        return estado;
    }

    public Contrato estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Contrato cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public Contrato inscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
        return this;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public Contrato vigencia(Integer vigencia) {
        this.vigencia = vigencia;
        return this;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public Contrato notaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
        return this;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Circuito getCircuito() {
        return circuito;
    }

    public Contrato circuito(Circuito circuito) {
        this.circuito = circuito;
        return this;
    }

    public void setCircuito(Circuito circuito) {
        this.circuito = circuito;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrato)) {
            return false;
        }
        return id != null && id.equals(((Contrato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contrato{" +
            "id=" + getId() +
            ", codigoClienteSap='" + getCodigoClienteSap() + "'" +
            ", numeroContratoSap='" + getNumeroContratoSap() + "'" +
            ", numeroContratoTpz='" + getNumeroContratoTpz() + "'" +
            ", nome='" + getNome() + "'" +
            ", status='" + getStatus() + "'" +
            ", dataAssinatura='" + getDataAssinatura() + "'" +
            ", dataReajuste='" + getDataReajuste() + "'" +
            ", dataTermino='" + getDataTermino() + "'" +
            ", estado='" + getEstado() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", inscricaoEstadual='" + getInscricaoEstadual() + "'" +
            ", vigencia=" + getVigencia() +
            "}";
    }
}
