package com.sgaraba.library.service;

import com.sgaraba.library.domain.Emprestimo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Emprestimo}.
 */
public interface EmprestimoService {

    /**
     * Save a emprestimo.
     *
     * @param emprestimo the entity to save.
     * @return the persisted entity.
     */
    Emprestimo save(Emprestimo emprestimo);

    /**
     * Get all the emprestimos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Emprestimo> findAll(Pageable pageable);


    /**
     * Get the "id" emprestimo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Emprestimo> findOne(Long id);

    /**
     * Delete the "id" emprestimo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
