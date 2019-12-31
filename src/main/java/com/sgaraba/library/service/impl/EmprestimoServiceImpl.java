package com.sgaraba.library.service.impl;

import com.sgaraba.library.service.EmprestimoService;
import com.sgaraba.library.domain.Emprestimo;
import com.sgaraba.library.repository.EmprestimoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Emprestimo}.
 */
@Service
@Transactional
public class EmprestimoServiceImpl implements EmprestimoService {

    private final Logger log = LoggerFactory.getLogger(EmprestimoServiceImpl.class);

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoServiceImpl(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    /**
     * Save a emprestimo.
     *
     * @param emprestimo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Emprestimo save(Emprestimo emprestimo) {
        log.debug("Request to save Emprestimo : {}", emprestimo);
        return emprestimoRepository.save(emprestimo);
    }

    /**
     * Get all the emprestimos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Emprestimo> findAll(Pageable pageable) {
        log.debug("Request to get all Emprestimos");
        return emprestimoRepository.findAll(pageable);
    }


    /**
     * Get one emprestimo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Emprestimo> findOne(Long id) {
        log.debug("Request to get Emprestimo : {}", id);
        return emprestimoRepository.findById(id);
    }

    /**
     * Delete the emprestimo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emprestimo : {}", id);
        emprestimoRepository.deleteById(id);
    }
}
