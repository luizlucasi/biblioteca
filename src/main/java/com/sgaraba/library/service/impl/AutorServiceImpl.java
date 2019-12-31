package com.sgaraba.library.service.impl;

import com.sgaraba.library.service.AutorService;
import com.sgaraba.library.domain.Autor;
import com.sgaraba.library.repository.AutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Autor}.
 */
@Service
@Transactional
public class AutorServiceImpl implements AutorService {

    private final Logger log = LoggerFactory.getLogger(AutorServiceImpl.class);

    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    /**
     * Save a autor.
     *
     * @param autor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Autor save(Autor autor) {
        log.debug("Request to save Autor : {}", autor);
        return autorRepository.save(autor);
    }

    /**
     * Get all the autors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Autor> findAll(Pageable pageable) {
        log.debug("Request to get all Autors");
        return autorRepository.findAll(pageable);
    }


    /**
     * Get one autor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Autor> findOne(Long id) {
        log.debug("Request to get Autor : {}", id);
        return autorRepository.findById(id);
    }

    /**
     * Delete the autor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Autor : {}", id);
        autorRepository.deleteById(id);
    }
}
