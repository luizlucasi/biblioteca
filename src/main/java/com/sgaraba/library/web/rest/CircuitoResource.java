package com.sgaraba.library.web.rest;

import com.sgaraba.library.domain.Circuito;
import com.sgaraba.library.repository.CircuitoRepository;
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
 * REST controller for managing {@link com.sgaraba.library.domain.Circuito}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CircuitoResource {

    private final Logger log = LoggerFactory.getLogger(CircuitoResource.class);

    private static final String ENTITY_NAME = "circuito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CircuitoRepository circuitoRepository;

    public CircuitoResource(CircuitoRepository circuitoRepository) {
        this.circuitoRepository = circuitoRepository;
    }

    /**
     * {@code POST  /circuitos} : Create a new circuito.
     *
     * @param circuito the circuito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new circuito, or with status {@code 400 (Bad Request)} if the circuito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/circuitos")
    public ResponseEntity<Circuito> createCircuito(@RequestBody Circuito circuito) throws URISyntaxException {
        log.debug("REST request to save Circuito : {}", circuito);
        if (circuito.getId() != null) {
            throw new BadRequestAlertException("A new circuito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Circuito result = circuitoRepository.save(circuito);
        return ResponseEntity.created(new URI("/api/circuitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /circuitos} : Updates an existing circuito.
     *
     * @param circuito the circuito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated circuito,
     * or with status {@code 400 (Bad Request)} if the circuito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the circuito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/circuitos")
    public ResponseEntity<Circuito> updateCircuito(@RequestBody Circuito circuito) throws URISyntaxException {
        log.debug("REST request to update Circuito : {}", circuito);
        if (circuito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Circuito result = circuitoRepository.save(circuito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, circuito.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /circuitos} : get all the circuitos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of circuitos in body.
     */
    @GetMapping("/circuitos")
    public List<Circuito> getAllCircuitos() {
        log.debug("REST request to get all Circuitos");
        return circuitoRepository.findAll();
    }

    /**
     * {@code GET  /circuitos/:id} : get the "id" circuito.
     *
     * @param id the id of the circuito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the circuito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/circuitos/{id}")
    public ResponseEntity<Circuito> getCircuito(@PathVariable Long id) {
        log.debug("REST request to get Circuito : {}", id);
        Optional<Circuito> circuito = circuitoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(circuito);
    }

    /**
     * {@code DELETE  /circuitos/:id} : delete the "id" circuito.
     *
     * @param id the id of the circuito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/circuitos/{id}")
    public ResponseEntity<Void> deleteCircuito(@PathVariable Long id) {
        log.debug("REST request to delete Circuito : {}", id);
        circuitoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
