package com.sgaraba.library.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sgaraba.library.domain.Livro} entity. This class is used
 * in {@link com.sgaraba.library.web.rest.LivroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /livros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LivroCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter isbn;

    private StringFilter nome;

    private StringFilter editora;

    private IntegerFilter tombo;

    private LongFilter autorId;

    public LivroCriteria(){
    }

    public LivroCriteria(LivroCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.isbn = other.isbn == null ? null : other.isbn.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.editora = other.editora == null ? null : other.editora.copy();
        this.tombo = other.tombo == null ? null : other.tombo.copy();
        this.autorId = other.autorId == null ? null : other.autorId.copy();
    }

    @Override
    public LivroCriteria copy() {
        return new LivroCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIsbn() {
        return isbn;
    }

    public void setIsbn(StringFilter isbn) {
        this.isbn = isbn;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getEditora() {
        return editora;
    }

    public void setEditora(StringFilter editora) {
        this.editora = editora;
    }

    public IntegerFilter getTombo() {
        return tombo;
    }

    public void setTombo(IntegerFilter tombo) {
        this.tombo = tombo;
    }

    public LongFilter getAutorId() {
        return autorId;
    }

    public void setAutorId(LongFilter autorId) {
        this.autorId = autorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LivroCriteria that = (LivroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(editora, that.editora) &&
            Objects.equals(tombo, that.tombo) &&
            Objects.equals(autorId, that.autorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isbn,
        nome,
        editora,
        tombo,
        autorId
        );
    }

    @Override
    public String toString() {
        return "LivroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isbn != null ? "isbn=" + isbn + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (editora != null ? "editora=" + editora + ", " : "") +
                (tombo != null ? "tombo=" + tombo + ", " : "") +
                (autorId != null ? "autorId=" + autorId + ", " : "") +
            "}";
    }

}
