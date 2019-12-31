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
 * Criteria class for the {@link com.sgaraba.library.domain.Autor} entity. This class is used
 * in {@link com.sgaraba.library.web.rest.AutorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AutorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter sobrenome;

    private LongFilter livroId;

    public AutorCriteria(){
    }

    public AutorCriteria(AutorCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sobrenome = other.sobrenome == null ? null : other.sobrenome.copy();
        this.livroId = other.livroId == null ? null : other.livroId.copy();
    }

    @Override
    public AutorCriteria copy() {
        return new AutorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(StringFilter sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LongFilter getLivroId() {
        return livroId;
    }

    public void setLivroId(LongFilter livroId) {
        this.livroId = livroId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AutorCriteria that = (AutorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sobrenome, that.sobrenome) &&
            Objects.equals(livroId, that.livroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        sobrenome,
        livroId
        );
    }

    @Override
    public String toString() {
        return "AutorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (sobrenome != null ? "sobrenome=" + sobrenome + ", " : "") +
                (livroId != null ? "livroId=" + livroId + ", " : "") +
            "}";
    }

}
