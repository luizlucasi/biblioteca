package com.sgaraba.library.web.rest;

import com.sgaraba.library.domain.Emprestimo;
import com.sgaraba.library.service.EmprestimoService;
import com.sgaraba.library.web.rest.errors.BadRequestAlertException;
import com.sgaraba.library.service.dto.EmprestimoCriteria;
import com.sgaraba.library.service.EmprestimoQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sgaraba.library.domain.Emprestimo}.
 */
@RestController
@RequestMapping("/api")
public class EmprestimoResource {

    private final Logger log = LoggerFactory.getLogger(EmprestimoResource.class);

    private static final String ENTITY_NAME = "emprestimo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmprestimoService emprestimoService;

    private final EmprestimoQueryService emprestimoQueryService;

    public EmprestimoResource(EmprestimoService emprestimoService, EmprestimoQueryService emprestimoQueryService) {
        this.emprestimoService = emprestimoService;
        this.emprestimoQueryService = emprestimoQueryService;
    }

    /**
     * {@code POST  /emprestimos} : Create a new emprestimo.
     *
     * @param emprestimo the emprestimo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emprestimo, or with status {@code 400 (Bad Request)} if the emprestimo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emprestimos")
    public ResponseEntity<Emprestimo> createEmprestimo(@RequestBody Emprestimo emprestimo) throws URISyntaxException {
        log.debug("REST request to save Emprestimo : {}", emprestimo);
        if (emprestimo.getId() != null) {
            throw new BadRequestAlertException("A new emprestimo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emprestimo result = emprestimoService.save(emprestimo);
        return ResponseEntity.created(new URI("/api/emprestimos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emprestimos} : Updates an existing emprestimo.
     *
     * @param emprestimo the emprestimo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emprestimo,
     * or with status {@code 400 (Bad Request)} if the emprestimo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emprestimo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emprestimos")
    public ResponseEntity<Emprestimo> updateEmprestimo(@RequestBody Emprestimo emprestimo) throws URISyntaxException {
        log.debug("REST request to update Emprestimo : {}", emprestimo);
        if (emprestimo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Emprestimo result = emprestimoService.save(emprestimo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emprestimo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emprestimos} : get all the emprestimos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emprestimos in body.
     */
    @GetMapping("/emprestimos")
    public ResponseEntity<List<Emprestimo>> getAllEmprestimos(EmprestimoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Emprestimos by criteria: {}", criteria);
        Page<Emprestimo> page = emprestimoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /emprestimos/count} : count all the emprestimos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/emprestimos/count")
    public ResponseEntity<Long> countEmprestimos(EmprestimoCriteria criteria) {
        log.debug("REST request to count Emprestimos by criteria: {}", criteria);
        return ResponseEntity.ok().body(emprestimoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emprestimos/:id} : get the "id" emprestimo.
     *
     * @param id the id of the emprestimo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emprestimo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emprestimos/{id}")
    public ResponseEntity<Emprestimo> getEmprestimo(@PathVariable Long id) {
        log.debug("REST request to get Emprestimo : {}", id);
        Optional<Emprestimo> emprestimo = emprestimoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emprestimo);
    }

    /**
     * {@code DELETE  /emprestimos/:id} : delete the "id" emprestimo.
     *
     * @param id the id of the emprestimo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emprestimos/{id}")
    public ResponseEntity<Void> deleteEmprestimo(@PathVariable Long id) {
        log.debug("REST request to delete Emprestimo : {}", id);
        emprestimoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
