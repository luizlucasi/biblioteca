package com.sgaraba.library.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Livro.
 */
@Entity
@Table(name = "livro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 13)
    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    private String isbn;

    @NotNull
    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull
    @Size(min = 4, max = 100)
    @Column(name = "editora", length = 100, nullable = false)
    private String editora;

    @Lob
    @Column(name = "capa")
    private byte[] capa;

    @Column(name = "capa_content_type")
    private String capaContentType;

    @Column(name = "tombo")
    private Integer tombo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "livro_autor",
               joinColumns = @JoinColumn(name = "livro_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id"))
    private Set<Autor> autors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public Livro isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNome() {
        return nome;
    }

    public Livro nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEditora() {
        return editora;
    }

    public Livro editora(String editora) {
        this.editora = editora;
        return this;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public byte[] getCapa() {
        return capa;
    }

    public Livro capa(byte[] capa) {
        this.capa = capa;
        return this;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getCapaContentType() {
        return capaContentType;
    }

    public Livro capaContentType(String capaContentType) {
        this.capaContentType = capaContentType;
        return this;
    }

    public void setCapaContentType(String capaContentType) {
        this.capaContentType = capaContentType;
    }

    public Integer getTombo() {
        return tombo;
    }

    public Livro tombo(Integer tombo) {
        this.tombo = tombo;
        return this;
    }

    public void setTombo(Integer tombo) {
        this.tombo = tombo;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public Livro autors(Set<Autor> autors) {
        this.autors = autors;
        return this;
    }

    public Livro addAutor(Autor autor) {
        this.autors.add(autor);
        autor.getLivros().add(this);
        return this;
    }

    public Livro removeAutor(Autor autor) {
        this.autors.remove(autor);
        autor.getLivros().remove(this);
        return this;
    }

    public void setAutors(Set<Autor> autors) {
        this.autors = autors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livro)) {
            return false;
        }
        return id != null && id.equals(((Livro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Livro{" +
            "id=" + getId() +
            ", isbn='" + getIsbn() + "'" +
            ", nome='" + getNome() + "'" +
            ", editora='" + getEditora() + "'" +
            ", capa='" + getCapa() + "'" +
            ", capaContentType='" + getCapaContentType() + "'" +
            ", tombo=" + getTombo() +
            "}";
    }
}
