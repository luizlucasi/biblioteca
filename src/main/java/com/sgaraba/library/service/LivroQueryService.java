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

import com.sgaraba.library.domain.Livro;
import com.sgaraba.library.domain.*; // for static metamodels
import com.sgaraba.library.repository.LivroRepository;
import com.sgaraba.library.service.dto.LivroCriteria;

/**
 * Service for executing complex queries for {@link Livro} entities in the database.
 * The main input is a {@link LivroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Livro} or a {@link Page} of {@link Livro} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LivroQueryService extends QueryService<Livro> {

    private final Logger log = LoggerFactory.getLogger(LivroQueryService.class);

    private final LivroRepository livroRepository;

    public LivroQueryService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    /**
     * Return a {@link List} of {@link Livro} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Livro> findByCriteria(LivroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Livro> specification = createSpecification(criteria);
        return livroRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Livro} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Livro> findByCriteria(LivroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Livro> specification = createSpecification(criteria);
        return livroRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LivroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Livro> specification = createSpecification(criteria);
        return livroRepository.count(specification);
    }

    /**
     * Function to convert {@link LivroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Livro> createSpecification(LivroCriteria criteria) {
        Specification<Livro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Livro_.id));
            }
            if (criteria.getIsbn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsbn(), Livro_.isbn));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Livro_.nome));
            }
            if (criteria.getEditora() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditora(), Livro_.editora));
            }
            if (criteria.getTombo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTombo(), Livro_.tombo));
            }
            if (criteria.getAutorId() != null) {
                specification = specification.and(buildSpecification(criteria.getAutorId(),
                    root -> root.join(Livro_.autors, JoinType.LEFT).get(Autor_.id)));
            }
        }
        return specification;
    }
}
