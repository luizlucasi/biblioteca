package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.Contrato;
import com.sgaraba.library.repository.ContratoRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContratoResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ContratoResourceIT {

    private static final String DEFAULT_CODIGO_CLIENTE_SAP = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_CLIENTE_SAP = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CONTRATO_SAP = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CONTRATO_SAP = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CONTRATO_TPZ = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CONTRATO_TPZ = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_ASSINATURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ASSINATURA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_REAJUSTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_REAJUSTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_TERMINO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_TERMINO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_INSCRICAO_ESTADUAL = "AAAAAAAAAA";
    private static final String UPDATED_INSCRICAO_ESTADUAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_VIGENCIA = 1;
    private static final Integer UPDATED_VIGENCIA = 2;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContratoMockMvc;

    private Contrato contrato;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrato createEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .codigoClienteSap(DEFAULT_CODIGO_CLIENTE_SAP)
            .numeroContratoSap(DEFAULT_NUMERO_CONTRATO_SAP)
            .numeroContratoTpz(DEFAULT_NUMERO_CONTRATO_TPZ)
            .nome(DEFAULT_NOME)
            .status(DEFAULT_STATUS)
            .dataAssinatura(DEFAULT_DATA_ASSINATURA)
            .dataReajuste(DEFAULT_DATA_REAJUSTE)
            .dataTermino(DEFAULT_DATA_TERMINO)
            .estado(DEFAULT_ESTADO)
            .cnpj(DEFAULT_CNPJ)
            .inscricaoEstadual(DEFAULT_INSCRICAO_ESTADUAL)
            .vigencia(DEFAULT_VIGENCIA);
        return contrato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrato createUpdatedEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .codigoClienteSap(UPDATED_CODIGO_CLIENTE_SAP)
            .numeroContratoSap(UPDATED_NUMERO_CONTRATO_SAP)
            .numeroContratoTpz(UPDATED_NUMERO_CONTRATO_TPZ)
            .nome(UPDATED_NOME)
            .status(UPDATED_STATUS)
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .dataReajuste(UPDATED_DATA_REAJUSTE)
            .dataTermino(UPDATED_DATA_TERMINO)
            .estado(UPDATED_ESTADO)
            .cnpj(UPDATED_CNPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .vigencia(UPDATED_VIGENCIA);
        return contrato;
    }

    @BeforeEach
    public void initTest() {
        contrato = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrato() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isCreated());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate + 1);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getCodigoClienteSap()).isEqualTo(DEFAULT_CODIGO_CLIENTE_SAP);
        assertThat(testContrato.getNumeroContratoSap()).isEqualTo(DEFAULT_NUMERO_CONTRATO_SAP);
        assertThat(testContrato.getNumeroContratoTpz()).isEqualTo(DEFAULT_NUMERO_CONTRATO_TPZ);
        assertThat(testContrato.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContrato.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testContrato.getDataAssinatura()).isEqualTo(DEFAULT_DATA_ASSINATURA);
        assertThat(testContrato.getDataReajuste()).isEqualTo(DEFAULT_DATA_REAJUSTE);
        assertThat(testContrato.getDataTermino()).isEqualTo(DEFAULT_DATA_TERMINO);
        assertThat(testContrato.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testContrato.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testContrato.getInscricaoEstadual()).isEqualTo(DEFAULT_INSCRICAO_ESTADUAL);
        assertThat(testContrato.getVigencia()).isEqualTo(DEFAULT_VIGENCIA);
    }

    @Test
    @Transactional
    public void createContratoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato with an existing ID
        contrato.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContratoes() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get all the contratoList
        restContratoMockMvc.perform(get("/api/contratoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrato.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoClienteSap").value(hasItem(DEFAULT_CODIGO_CLIENTE_SAP)))
            .andExpect(jsonPath("$.[*].numeroContratoSap").value(hasItem(DEFAULT_NUMERO_CONTRATO_SAP)))
            .andExpect(jsonPath("$.[*].numeroContratoTpz").value(hasItem(DEFAULT_NUMERO_CONTRATO_TPZ)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dataAssinatura").value(hasItem(DEFAULT_DATA_ASSINATURA.toString())))
            .andExpect(jsonPath("$.[*].dataReajuste").value(hasItem(DEFAULT_DATA_REAJUSTE.toString())))
            .andExpect(jsonPath("$.[*].dataTermino").value(hasItem(DEFAULT_DATA_TERMINO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].inscricaoEstadual").value(hasItem(DEFAULT_INSCRICAO_ESTADUAL)))
            .andExpect(jsonPath("$.[*].vigencia").value(hasItem(DEFAULT_VIGENCIA)));
    }
    
    @Test
    @Transactional
    public void getContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", contrato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contrato.getId().intValue()))
            .andExpect(jsonPath("$.codigoClienteSap").value(DEFAULT_CODIGO_CLIENTE_SAP))
            .andExpect(jsonPath("$.numeroContratoSap").value(DEFAULT_NUMERO_CONTRATO_SAP))
            .andExpect(jsonPath("$.numeroContratoTpz").value(DEFAULT_NUMERO_CONTRATO_TPZ))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dataAssinatura").value(DEFAULT_DATA_ASSINATURA.toString()))
            .andExpect(jsonPath("$.dataReajuste").value(DEFAULT_DATA_REAJUSTE.toString()))
            .andExpect(jsonPath("$.dataTermino").value(DEFAULT_DATA_TERMINO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.inscricaoEstadual").value(DEFAULT_INSCRICAO_ESTADUAL))
            .andExpect(jsonPath("$.vigencia").value(DEFAULT_VIGENCIA));
    }

    @Test
    @Transactional
    public void getNonExistingContrato() throws Exception {
        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Update the contrato
        Contrato updatedContrato = contratoRepository.findById(contrato.getId()).get();
        // Disconnect from session so that the updates on updatedContrato are not directly saved in db
        em.detach(updatedContrato);
        updatedContrato
            .codigoClienteSap(UPDATED_CODIGO_CLIENTE_SAP)
            .numeroContratoSap(UPDATED_NUMERO_CONTRATO_SAP)
            .numeroContratoTpz(UPDATED_NUMERO_CONTRATO_TPZ)
            .nome(UPDATED_NOME)
            .status(UPDATED_STATUS)
            .dataAssinatura(UPDATED_DATA_ASSINATURA)
            .dataReajuste(UPDATED_DATA_REAJUSTE)
            .dataTermino(UPDATED_DATA_TERMINO)
            .estado(UPDATED_ESTADO)
            .cnpj(UPDATED_CNPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .vigencia(UPDATED_VIGENCIA);

        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContrato)))
            .andExpect(status().isOk());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getCodigoClienteSap()).isEqualTo(UPDATED_CODIGO_CLIENTE_SAP);
        assertThat(testContrato.getNumeroContratoSap()).isEqualTo(UPDATED_NUMERO_CONTRATO_SAP);
        assertThat(testContrato.getNumeroContratoTpz()).isEqualTo(UPDATED_NUMERO_CONTRATO_TPZ);
        assertThat(testContrato.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContrato.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testContrato.getDataAssinatura()).isEqualTo(UPDATED_DATA_ASSINATURA);
        assertThat(testContrato.getDataReajuste()).isEqualTo(UPDATED_DATA_REAJUSTE);
        assertThat(testContrato.getDataTermino()).isEqualTo(UPDATED_DATA_TERMINO);
        assertThat(testContrato.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testContrato.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testContrato.getInscricaoEstadual()).isEqualTo(UPDATED_INSCRICAO_ESTADUAL);
        assertThat(testContrato.getVigencia()).isEqualTo(UPDATED_VIGENCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Create the Contrato

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeDelete = contratoRepository.findAll().size();

        // Delete the contrato
        restContratoMockMvc.perform(delete("/api/contratoes/{id}", contrato.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
