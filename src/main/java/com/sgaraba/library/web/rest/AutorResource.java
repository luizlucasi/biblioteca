package com.sgaraba.library.web.rest;

import com.sgaraba.library.domain.Autor;
import com.sgaraba.library.service.AutorService;
import com.sgaraba.library.web.rest.errors.BadRequestAlertException;
import com.sgaraba.library.service.dto.AutorCriteria;
import com.sgaraba.library.service.AutorQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sgaraba.library.domain.Autor}.
 */
@RestController
@RequestMapping("/api")
public class AutorResource {

    private final Logger log = LoggerFactory.getLogger(AutorResource.class);

    private static final String ENTITY_NAME = "autor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutorService autorService;

    private final AutorQueryService autorQueryService;

    public AutorResource(AutorService autorService, AutorQueryService autorQueryService) {
        this.autorService = autorService;
        this.autorQueryService = autorQueryService;
    }

    /**
     * {@code POST  /autors} : Create a new autor.
     *
     * @param autor the autor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autor, or with status {@code 400 (Bad Request)} if the autor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/autors")
    public ResponseEntity<Autor> createAutor(@Valid @RequestBody Autor autor) throws URISyntaxException {
        log.debug("REST request to save Autor : {}", autor);
        if (autor.getId() != null) {
            throw new BadRequestAlertException("A new autor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Autor result = autorService.save(autor);
        return ResponseEntity.created(new URI("/api/autors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /autors} : Updates an existing autor.
     *
     * @param autor the autor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autor,
     * or with status {@code 400 (Bad Request)} if the autor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/autors")
    public ResponseEntity<Autor> updateAutor(@Valid @RequestBody Autor autor) throws URISyntaxException {
        log.debug("REST request to update Autor : {}", autor);
        if (autor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Autor result = autorService.save(autor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /autors} : get all the autors.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autors in body.
     */
    @GetMapping("/autors")
    public ResponseEntity<List<Autor>> getAllAutors(AutorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Autors by criteria: {}", criteria);
        Page<Autor> page = autorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /autors/count} : count all the autors.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/autors/count")
    public ResponseEntity<Long> countAutors(AutorCriteria criteria) {
        log.debug("REST request to count Autors by criteria: {}", criteria);
        return ResponseEntity.ok().body(autorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autors/:id} : get the "id" autor.
     *
     * @param id the id of the autor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/autors/{id}")
    public ResponseEntity<Autor> getAutor(@PathVariable Long id) {
        log.debug("REST request to get Autor : {}", id);
        Optional<Autor> autor = autorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autor);
    }

    /**
     * {@code DELETE  /autors/:id} : delete the "id" autor.
     *
     * @param id the id of the autor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/autors/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        log.debug("REST request to delete Autor : {}", id);
        autorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
