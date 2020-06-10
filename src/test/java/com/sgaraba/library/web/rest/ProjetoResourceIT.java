package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.Projeto;
import com.sgaraba.library.repository.ProjetoRepository;

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
 * Integration tests for the {@link ProjetoResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjetoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjetoMockMvc;

    private Projeto projeto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projeto createEntity(EntityManager em) {
        Projeto projeto = new Projeto()
            .nome(DEFAULT_NOME);
        return projeto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projeto createUpdatedEntity(EntityManager em) {
        Projeto projeto = new Projeto()
            .nome(UPDATED_NOME);
        return projeto;
    }

    @BeforeEach
    public void initTest() {
        projeto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjeto() throws Exception {
        int databaseSizeBeforeCreate = projetoRepository.findAll().size();
        // Create the Projeto
        restProjetoMockMvc.perform(post("/api/projetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projeto)))
            .andExpect(status().isCreated());

        // Validate the Projeto in the database
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeCreate + 1);
        Projeto testProjeto = projetoList.get(projetoList.size() - 1);
        assertThat(testProjeto.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createProjetoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projetoRepository.findAll().size();

        // Create the Projeto with an existing ID
        projeto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetoMockMvc.perform(post("/api/projetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projeto)))
            .andExpect(status().isBadRequest());

        // Validate the Projeto in the database
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjetos() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

        // Get all the projetoList
        restProjetoMockMvc.perform(get("/api/projetos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projeto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

        // Get the projeto
        restProjetoMockMvc.perform(get("/api/projetos/{id}", projeto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projeto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingProjeto() throws Exception {
        // Get the projeto
        restProjetoMockMvc.perform(get("/api/projetos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

        int databaseSizeBeforeUpdate = projetoRepository.findAll().size();

        // Update the projeto
        Projeto updatedProjeto = projetoRepository.findById(projeto.getId()).get();
        // Disconnect from session so that the updates on updatedProjeto are not directly saved in db
        em.detach(updatedProjeto);
        updatedProjeto
            .nome(UPDATED_NOME);

        restProjetoMockMvc.perform(put("/api/projetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjeto)))
            .andExpect(status().isOk());

        // Validate the Projeto in the database
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeUpdate);
        Projeto testProjeto = projetoList.get(projetoList.size() - 1);
        assertThat(testProjeto.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingProjeto() throws Exception {
        int databaseSizeBeforeUpdate = projetoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetoMockMvc.perform(put("/api/projetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projeto)))
            .andExpect(status().isBadRequest());

        // Validate the Projeto in the database
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjeto() throws Exception {
        // Initialize the database
        projetoRepository.saveAndFlush(projeto);

        int databaseSizeBeforeDelete = projetoRepository.findAll().size();

        // Delete the projeto
        restProjetoMockMvc.perform(delete("/api/projetos/{id}", projeto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projeto> projetoList = projetoRepository.findAll();
        assertThat(projetoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
