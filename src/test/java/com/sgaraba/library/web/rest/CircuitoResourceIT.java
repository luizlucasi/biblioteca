package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.Circuito;
import com.sgaraba.library.repository.CircuitoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CircuitoResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CircuitoResourceIT {

    private static final String DEFAULT_ID_CONTROLE_DESIGNACAO = "AAAAAAAAAA";
    private static final String UPDATED_ID_CONTROLE_DESIGNACAO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ESTACAO = "AAAAAAAAAA";
    private static final String UPDATED_ID_ESTACAO = "BBBBBBBBBB";

    @Autowired
    private CircuitoRepository circuitoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCircuitoMockMvc;

    private Circuito circuito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Circuito createEntity(EntityManager em) {
        Circuito circuito = new Circuito()
            .idControleDesignacao(DEFAULT_ID_CONTROLE_DESIGNACAO)
            .idEstacao(DEFAULT_ID_ESTACAO);
        return circuito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Circuito createUpdatedEntity(EntityManager em) {
        Circuito circuito = new Circuito()
            .idControleDesignacao(UPDATED_ID_CONTROLE_DESIGNACAO)
            .idEstacao(UPDATED_ID_ESTACAO);
        return circuito;
    }

    @BeforeEach
    public void initTest() {
        circuito = createEntity(em);
    }

    @Test
    @Transactional
    public void createCircuito() throws Exception {
        int databaseSizeBeforeCreate = circuitoRepository.findAll().size();

        // Create the Circuito
        restCircuitoMockMvc.perform(post("/api/circuitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(circuito)))
            .andExpect(status().isCreated());

        // Validate the Circuito in the database
        List<Circuito> circuitoList = circuitoRepository.findAll();
        assertThat(circuitoList).hasSize(databaseSizeBeforeCreate + 1);
        Circuito testCircuito = circuitoList.get(circuitoList.size() - 1);
        assertThat(testCircuito.getIdControleDesignacao()).isEqualTo(DEFAULT_ID_CONTROLE_DESIGNACAO);
        assertThat(testCircuito.getIdEstacao()).isEqualTo(DEFAULT_ID_ESTACAO);
    }

    @Test
    @Transactional
    public void createCircuitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = circuitoRepository.findAll().size();

        // Create the Circuito with an existing ID
        circuito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCircuitoMockMvc.perform(post("/api/circuitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(circuito)))
            .andExpect(status().isBadRequest());

        // Validate the Circuito in the database
        List<Circuito> circuitoList = circuitoRepository.findAll();
        assertThat(circuitoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCircuitos() throws Exception {
        // Initialize the database
        circuitoRepository.saveAndFlush(circuito);

        // Get all the circuitoList
        restCircuitoMockMvc.perform(get("/api/circuitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(circuito.getId().intValue())))
            .andExpect(jsonPath("$.[*].idControleDesignacao").value(hasItem(DEFAULT_ID_CONTROLE_DESIGNACAO)))
            .andExpect(jsonPath("$.[*].idEstacao").value(hasItem(DEFAULT_ID_ESTACAO)));
    }
    
    @Test
    @Transactional
    public void getCircuito() throws Exception {
        // Initialize the database
        circuitoRepository.saveAndFlush(circuito);

        // Get the circuito
        restCircuitoMockMvc.perform(get("/api/circuitos/{id}", circuito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(circuito.getId().intValue()))
            .andExpect(jsonPath("$.idControleDesignacao").value(DEFAULT_ID_CONTROLE_DESIGNACAO))
            .andExpect(jsonPath("$.idEstacao").value(DEFAULT_ID_ESTACAO));
    }

    @Test
    @Transactional
    public void getNonExistingCircuito() throws Exception {
        // Get the circuito
        restCircuitoMockMvc.perform(get("/api/circuitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCircuito() throws Exception {
        // Initialize the database
        circuitoRepository.saveAndFlush(circuito);

        int databaseSizeBeforeUpdate = circuitoRepository.findAll().size();

        // Update the circuito
        Circuito updatedCircuito = circuitoRepository.findById(circuito.getId()).get();
        // Disconnect from session so that the updates on updatedCircuito are not directly saved in db
        em.detach(updatedCircuito);
        updatedCircuito
            .idControleDesignacao(UPDATED_ID_CONTROLE_DESIGNACAO)
            .idEstacao(UPDATED_ID_ESTACAO);

        restCircuitoMockMvc.perform(put("/api/circuitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCircuito)))
            .andExpect(status().isOk());

        // Validate the Circuito in the database
        List<Circuito> circuitoList = circuitoRepository.findAll();
        assertThat(circuitoList).hasSize(databaseSizeBeforeUpdate);
        Circuito testCircuito = circuitoList.get(circuitoList.size() - 1);
        assertThat(testCircuito.getIdControleDesignacao()).isEqualTo(UPDATED_ID_CONTROLE_DESIGNACAO);
        assertThat(testCircuito.getIdEstacao()).isEqualTo(UPDATED_ID_ESTACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCircuito() throws Exception {
        int databaseSizeBeforeUpdate = circuitoRepository.findAll().size();

        // Create the Circuito

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCircuitoMockMvc.perform(put("/api/circuitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(circuito)))
            .andExpect(status().isBadRequest());

        // Validate the Circuito in the database
        List<Circuito> circuitoList = circuitoRepository.findAll();
        assertThat(circuitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCircuito() throws Exception {
        // Initialize the database
        circuitoRepository.saveAndFlush(circuito);

        int databaseSizeBeforeDelete = circuitoRepository.findAll().size();

        // Delete the circuito
        restCircuitoMockMvc.perform(delete("/api/circuitos/{id}", circuito.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Circuito> circuitoList = circuitoRepository.findAll();
        assertThat(circuitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
