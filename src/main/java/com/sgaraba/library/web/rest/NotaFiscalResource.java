package com.sgaraba.library.web.rest;

import com.sgaraba.library.domain.NotaFiscal;
import com.sgaraba.library.repository.NotaFiscalRepository;
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
 * REST controller for managing {@link com.sgaraba.library.domain.NotaFiscal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotaFiscalResource {

    private final Logger log = LoggerFactory.getLogger(NotaFiscalResource.class);

    private static final String ENTITY_NAME = "notaFiscal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotaFiscalRepository notaFiscalRepository;

    public NotaFiscalResource(NotaFiscalRepository notaFiscalRepository) {
        this.notaFiscalRepository = notaFiscalRepository;
    }

    /**
     * {@code POST  /nota-fiscals} : Create a new notaFiscal.
     *
     * @param notaFiscal the notaFiscal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notaFiscal, or with status {@code 400 (Bad Request)} if the notaFiscal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nota-fiscals")
    public ResponseEntity<NotaFiscal> createNotaFiscal(@RequestBody NotaFiscal notaFiscal) throws URISyntaxException {
        log.debug("REST request to save NotaFiscal : {}", notaFiscal);
        if (notaFiscal.getId() != null) {
            throw new BadRequestAlertException("A new notaFiscal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotaFiscal result = notaFiscalRepository.save(notaFiscal);
        return ResponseEntity.created(new URI("/api/nota-fiscals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nota-fiscals} : Updates an existing notaFiscal.
     *
     * @param notaFiscal the notaFiscal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notaFiscal,
     * or with status {@code 400 (Bad Request)} if the notaFiscal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notaFiscal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nota-fiscals")
    public ResponseEntity<NotaFiscal> updateNotaFiscal(@RequestBody NotaFiscal notaFiscal) throws URISyntaxException {
        log.debug("REST request to update NotaFiscal : {}", notaFiscal);
        if (notaFiscal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotaFiscal result = notaFiscalRepository.save(notaFiscal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notaFiscal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nota-fiscals} : get all the notaFiscals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notaFiscals in body.
     */
    @GetMapping("/nota-fiscals")
    public List<NotaFiscal> getAllNotaFiscals() {
        log.debug("REST request to get all NotaFiscals");
        return notaFiscalRepository.findAll();
    }

    /**
     * {@code GET  /nota-fiscals/:id} : get the "id" notaFiscal.
     *
     * @param id the id of the notaFiscal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notaFiscal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nota-fiscals/{id}")
    public ResponseEntity<NotaFiscal> getNotaFiscal(@PathVariable Long id) {
        log.debug("REST request to get NotaFiscal : {}", id);
        Optional<NotaFiscal> notaFiscal = notaFiscalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notaFiscal);
    }

    /**
     * {@code DELETE  /nota-fiscals/:id} : delete the "id" notaFiscal.
     *
     * @param id the id of the notaFiscal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nota-fiscals/{id}")
    public ResponseEntity<Void> deleteNotaFiscal(@PathVariable Long id) {
        log.debug("REST request to delete NotaFiscal : {}", id);
        notaFiscalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
