package com.sgaraba.library.service.impl;

import com.sgaraba.library.service.LivroService;
import com.sgaraba.library.domain.Livro;
import com.sgaraba.library.repository.LivroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Livro}.
 */
@Service
@Transactional
public class LivroServiceImpl implements LivroService {

    private final Logger log = LoggerFactory.getLogger(LivroServiceImpl.class);

    private final LivroRepository livroRepository;

    public LivroServiceImpl(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    /**
     * Save a livro.
     *
     * @param livro the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Livro save(Livro livro) {
        log.debug("Request to save Livro : {}", livro);
        return livroRepository.save(livro);
    }

    /**
     * Get all the livros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Livro> findAll(Pageable pageable) {
        log.debug("Request to get all Livros");
        return livroRepository.findAll(pageable);
    }

    /**
     * Get all the livros with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Livro> findAllWithEagerRelationships(Pageable pageable) {
        return livroRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one livro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Livro> findOne(Long id) {
        log.debug("Request to get Livro : {}", id);
        return livroRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the livro by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Livro : {}", id);
        livroRepository.deleteById(id);
    }
}
