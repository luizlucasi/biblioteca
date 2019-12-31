package com.sgaraba.library.service;

import com.sgaraba.library.domain.Autor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Autor}.
 */
public interface AutorService {

    /**
     * Save a autor.
     *
     * @param autor the entity to save.
     * @return the persisted entity.
     */
    Autor save(Autor autor);

    /**
     * Get all the autors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Autor> findAll(Pageable pageable);


    /**
     * Get the "id" autor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Autor> findOne(Long id);

    /**
     * Delete the "id" autor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
