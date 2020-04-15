package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.NotaFiscal;
import com.sgaraba.library.repository.NotaFiscalRepository;

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
 * Integration tests for the {@link NotaFiscalResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class NotaFiscalResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETENCIA = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICO = "AAAAAAAAAA";
    private static final String UPDATED_SERVICO = "BBBBBBBBBB";

    private static final String DEFAULT_CONDICAO_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_CONDICAO_PAGAMENTO = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_NF = 1D;
    private static final Double UPDATED_TOTAL_NF = 2D;

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotaFiscalMockMvc;

    private NotaFiscal notaFiscal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaFiscal createEntity(EntityManager em) {
        NotaFiscal notaFiscal = new NotaFiscal()
            .numero(DEFAULT_NUMERO)
            .competencia(DEFAULT_COMPETENCIA)
            .tipo(DEFAULT_TIPO)
            .servico(DEFAULT_SERVICO)
            .condicaoPagamento(DEFAULT_CONDICAO_PAGAMENTO)
            .totalNF(DEFAULT_TOTAL_NF);
        return notaFiscal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaFiscal createUpdatedEntity(EntityManager em) {
        NotaFiscal notaFiscal = new NotaFiscal()
            .numero(UPDATED_NUMERO)
            .competencia(UPDATED_COMPETENCIA)
            .tipo(UPDATED_TIPO)
            .servico(UPDATED_SERVICO)
            .condicaoPagamento(UPDATED_CONDICAO_PAGAMENTO)
            .totalNF(UPDATED_TOTAL_NF);
        return notaFiscal;
    }

    @BeforeEach
    public void initTest() {
        notaFiscal = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotaFiscal() throws Exception {
        int databaseSizeBeforeCreate = notaFiscalRepository.findAll().size();

        // Create the NotaFiscal
        restNotaFiscalMockMvc.perform(post("/api/nota-fiscals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notaFiscal)))
            .andExpect(status().isCreated());

        // Validate the NotaFiscal in the database
        List<NotaFiscal> notaFiscalList = notaFiscalRepository.findAll();
        assertThat(notaFiscalList).hasSize(databaseSizeBeforeCreate + 1);
        NotaFiscal testNotaFiscal = notaFiscalList.get(notaFiscalList.size() - 1);
        assertThat(testNotaFiscal.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testNotaFiscal.getCompetencia()).isEqualTo(DEFAULT_COMPETENCIA);
        assertThat(testNotaFiscal.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testNotaFiscal.getServico()).isEqualTo(DEFAULT_SERVICO);
        assertThat(testNotaFiscal.getCondicaoPagamento()).isEqualTo(DEFAULT_CONDICAO_PAGAMENTO);
        assertThat(testNotaFiscal.getTotalNF()).isEqualTo(DEFAULT_TOTAL_NF);
    }

    @Test
    @Transactional
    public void createNotaFiscalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notaFiscalRepository.findAll().size();

        // Create the NotaFiscal with an existing ID
        notaFiscal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotaFiscalMockMvc.perform(post("/api/nota-fiscals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notaFiscal)))
            .andExpect(status().isBadRequest());

        // Validate the NotaFiscal in the database
        List<NotaFiscal> notaFiscalList = notaFiscalRepository.findAll();
        assertThat(notaFiscalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNotaFiscals() throws Exception {
        // Initialize the database
        notaFiscalRepository.saveAndFlush(notaFiscal);

        // Get all the notaFiscalList
        restNotaFiscalMockMvc.perform(get("/api/nota-fiscals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notaFiscal.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].competencia").value(hasItem(DEFAULT_COMPETENCIA)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].servico").value(hasItem(DEFAULT_SERVICO)))
            .andExpect(jsonPath("$.[*].condicaoPagamento").value(hasItem(DEFAULT_CONDICAO_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].totalNF").value(hasItem(DEFAULT_TOTAL_NF.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getNotaFiscal() throws Exception {
        // Initialize the database
        notaFiscalRepository.saveAndFlush(notaFiscal);

        // Get the notaFiscal
        restNotaFiscalMockMvc.perform(get("/api/nota-fiscals/{id}", notaFiscal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notaFiscal.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.competencia").value(DEFAULT_COMPETENCIA))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.servico").value(DEFAULT_SERVICO))
            .andExpect(jsonPath("$.condicaoPagamento").value(DEFAULT_CONDICAO_PAGAMENTO))
            .andExpect(jsonPath("$.totalNF").value(DEFAULT_TOTAL_NF.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNotaFiscal() throws Exception {
        // Get the notaFiscal
        restNotaFiscalMockMvc.perform(get("/api/nota-fiscals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotaFiscal() throws Exception {
        // Initialize the database
        notaFiscalRepository.saveAndFlush(notaFiscal);

        int databaseSizeBeforeUpdate = notaFiscalRepository.findAll().size();

        // Update the notaFiscal
        NotaFiscal updatedNotaFiscal = notaFiscalRepository.findById(notaFiscal.getId()).get();
        // Disconnect from session so that the updates on updatedNotaFiscal are not directly saved in db
        em.detach(updatedNotaFiscal);
        updatedNotaFiscal
            .numero(UPDATED_NUMERO)
            .competencia(UPDATED_COMPETENCIA)
            .tipo(UPDATED_TIPO)
            .servico(UPDATED_SERVICO)
            .condicaoPagamento(UPDATED_CONDICAO_PAGAMENTO)
            .totalNF(UPDATED_TOTAL_NF);

        restNotaFiscalMockMvc.perform(put("/api/nota-fiscals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotaFiscal)))
            .andExpect(status().isOk());

        // Validate the NotaFiscal in the database
        List<NotaFiscal> notaFiscalList = notaFiscalRepository.findAll();
        assertThat(notaFiscalList).hasSize(databaseSizeBeforeUpdate);
        NotaFiscal testNotaFiscal = notaFiscalList.get(notaFiscalList.size() - 1);
        assertThat(testNotaFiscal.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testNotaFiscal.getCompetencia()).isEqualTo(UPDATED_COMPETENCIA);
        assertThat(testNotaFiscal.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testNotaFiscal.getServico()).isEqualTo(UPDATED_SERVICO);
        assertThat(testNotaFiscal.getCondicaoPagamento()).isEqualTo(UPDATED_CONDICAO_PAGAMENTO);
        assertThat(testNotaFiscal.getTotalNF()).isEqualTo(UPDATED_TOTAL_NF);
    }

    @Test
    @Transactional
    public void updateNonExistingNotaFiscal() throws Exception {
        int databaseSizeBeforeUpdate = notaFiscalRepository.findAll().size();

        // Create the NotaFiscal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaFiscalMockMvc.perform(put("/api/nota-fiscals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notaFiscal)))
            .andExpect(status().isBadRequest());

        // Validate the NotaFiscal in the database
        List<NotaFiscal> notaFiscalList = notaFiscalRepository.findAll();
        assertThat(notaFiscalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotaFiscal() throws Exception {
        // Initialize the database
        notaFiscalRepository.saveAndFlush(notaFiscal);

        int databaseSizeBeforeDelete = notaFiscalRepository.findAll().size();

        // Delete the notaFiscal
        restNotaFiscalMockMvc.perform(delete("/api/nota-fiscals/{id}", notaFiscal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotaFiscal> notaFiscalList = notaFiscalRepository.findAll();
        assertThat(notaFiscalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
