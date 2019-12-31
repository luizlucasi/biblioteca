package com.sgaraba.library.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.sgaraba.library.domain.Emprestimo;
import com.sgaraba.library.domain.*; // for static metamodels
import com.sgaraba.library.repository.EmprestimoRepository;
import com.sgaraba.library.service.dto.EmprestimoCriteria;

/**
 * Service for executing complex queries for {@link Emprestimo} entities in the database.
 * The main input is a {@link EmprestimoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Emprestimo} or a {@link Page} of {@link Emprestimo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmprestimoQueryService extends QueryService<Emprestimo> {

    private final Logger log = LoggerFactory.getLogger(EmprestimoQueryService.class);

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoQueryService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    /**
     * Return a {@link List} of {@link Emprestimo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Emprestimo> findByCriteria(EmprestimoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Emprestimo> specification = createSpecification(criteria);
        return emprestimoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Emprestimo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Emprestimo> findByCriteria(EmprestimoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Emprestimo> specification = createSpecification(criteria);
        return emprestimoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmprestimoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Emprestimo> specification = createSpecification(criteria);
        return emprestimoRepository.count(specification);
    }

    /**
     * Function to convert {@link EmprestimoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Emprestimo> createSpecification(EmprestimoCriteria criteria) {
        Specification<Emprestimo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Emprestimo_.id));
            }
            if (criteria.getDataEmprestimo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataEmprestimo(), Emprestimo_.dataEmprestimo));
            }
            if (criteria.getLivroId() != null) {
                specification = specification.and(buildSpecification(criteria.getLivroId(),
                    root -> root.join(Emprestimo_.livro, JoinType.LEFT).get(Livro_.id)));
            }
            if (criteria.getAlunoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlunoId(),
                    root -> root.join(Emprestimo_.aluno, JoinType.LEFT).get(Aluno_.id)));
            }
        }
        return specification;
    }
}
