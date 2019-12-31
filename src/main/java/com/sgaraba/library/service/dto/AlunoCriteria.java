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
 * Criteria class for the {@link com.sgaraba.library.domain.Aluno} entity. This class is used
 * in {@link com.sgaraba.library.web.rest.AlunoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alunos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlunoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private IntegerFilter turma;

    private IntegerFilter matricula;

    private StringFilter celular;

    public AlunoCriteria(){
    }

    public AlunoCriteria(AlunoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.turma = other.turma == null ? null : other.turma.copy();
        this.matricula = other.matricula == null ? null : other.matricula.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
    }

    @Override
    public AlunoCriteria copy() {
        return new AlunoCriteria(this);
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

    public IntegerFilter getTurma() {
        return turma;
    }

    public void setTurma(IntegerFilter turma) {
        this.turma = turma;
    }

    public IntegerFilter getMatricula() {
        return matricula;
    }

    public void setMatricula(IntegerFilter matricula) {
        this.matricula = matricula;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlunoCriteria that = (AlunoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(turma, that.turma) &&
            Objects.equals(matricula, that.matricula) &&
            Objects.equals(celular, that.celular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        turma,
        matricula,
        celular
        );
    }

    @Override
    public String toString() {
        return "AlunoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (turma != null ? "turma=" + turma + ", " : "") +
                (matricula != null ? "matricula=" + matricula + ", " : "") +
                (celular != null ? "celular=" + celular + ", " : "") +
            "}";
    }

}
