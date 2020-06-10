package com.sgaraba.library.web.rest;

import com.sgaraba.library.domain.Projeto;
import com.sgaraba.library.repository.ProjetoRepository;
import com.sgaraba.library.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sgaraba.library.domain.Projeto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjetoResource {

    private final Logger log = LoggerFactory.getLogger(ProjetoResource.class);

    private static final String ENTITY_NAME = "projeto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjetoRepository projetoRepository;

    public ProjetoResource(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    /**
     * {@code POST  /projetos} : Create a new projeto.
     *
     * @param projeto the projeto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projeto, or with status {@code 400 (Bad Request)} if the projeto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projetos")
    public ResponseEntity<Projeto> createProjeto(@RequestBody Projeto projeto) throws URISyntaxException {
        log.debug("REST request to save Projeto : {}", projeto);
        if (projeto.getId() != null) {
            throw new BadRequestAlertException("A new projeto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Projeto result = projetoRepository.save(projeto);
        return ResponseEntity.created(new URI("/api/projetos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projetos} : Updates an existing projeto.
     *
     * @param projeto the projeto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projeto,
     * or with status {@code 400 (Bad Request)} if the projeto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projeto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projetos")
    public ResponseEntity<Projeto> updateProjeto(@RequestBody Projeto projeto) throws URISyntaxException {
        log.debug("REST request to update Projeto : {}", projeto);
        if (projeto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Projeto result = projetoRepository.save(projeto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projeto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /projetos} : get all the projetos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projetos in body.
     */
    @GetMapping("/projetos")
    public List<Projeto> getAllProjetos() {
        log.debug("REST request to get all Projetos");
        return projetoRepository.findAll();
    }

    /**
     * {@code GET  /projetos/:id} : get the "id" projeto.
     *
     * @param id the id of the projeto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projeto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projetos/{id}")
    public ResponseEntity<Projeto> getProjeto(@PathVariable Long id) {
        log.debug("REST request to get Projeto : {}", id);
        Optional<Projeto> projeto = projetoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projeto);
    }

    /**
     * {@code DELETE  /projetos/:id} : delete the "id" projeto.
     *
     * @param id the id of the projeto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Long id) {
        log.debug("REST request to delete Projeto : {}", id);
        projetoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
