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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.sgaraba.library.domain.Emprestimo} entity. This class is used
 * in {@link com.sgaraba.library.web.rest.EmprestimoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emprestimos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmprestimoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataEmprestimo;

    private LongFilter livroId;

    private LongFilter alunoId;

    public EmprestimoCriteria(){
    }

    public EmprestimoCriteria(EmprestimoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataEmprestimo = other.dataEmprestimo == null ? null : other.dataEmprestimo.copy();
        this.livroId = other.livroId == null ? null : other.livroId.copy();
        this.alunoId = other.alunoId == null ? null : other.alunoId.copy();
    }

    @Override
    public EmprestimoCriteria copy() {
        return new EmprestimoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateFilter dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LongFilter getLivroId() {
        return livroId;
    }

    public void setLivroId(LongFilter livroId) {
        this.livroId = livroId;
    }

    public LongFilter getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(LongFilter alunoId) {
        this.alunoId = alunoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmprestimoCriteria that = (EmprestimoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataEmprestimo, that.dataEmprestimo) &&
            Objects.equals(livroId, that.livroId) &&
            Objects.equals(alunoId, that.alunoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataEmprestimo,
        livroId,
        alunoId
        );
    }

    @Override
    public String toString() {
        return "EmprestimoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataEmprestimo != null ? "dataEmprestimo=" + dataEmprestimo + ", " : "") +
                (livroId != null ? "livroId=" + livroId + ", " : "") +
                (alunoId != null ? "alunoId=" + alunoId + ", " : "") +
            "}";
    }

}
