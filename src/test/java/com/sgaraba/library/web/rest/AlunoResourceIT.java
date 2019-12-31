package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.Aluno;
import com.sgaraba.library.repository.AlunoRepository;
import com.sgaraba.library.service.AlunoService;
import com.sgaraba.library.web.rest.errors.ExceptionTranslator;
import com.sgaraba.library.service.dto.AlunoCriteria;
import com.sgaraba.library.service.AlunoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sgaraba.library.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlunoResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)
public class AlunoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TURMA = 1;
    private static final Integer UPDATED_TURMA = 2;
    private static final Integer SMALLER_TURMA = 1 - 1;

    private static final Integer DEFAULT_MATRICULA = 1;
    private static final Integer UPDATED_MATRICULA = 2;
    private static final Integer SMALLER_MATRICULA = 1 - 1;

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoQueryService alunoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlunoResource alunoResource = new AlunoResource(alunoService, alunoQueryService);
        this.restAlunoMockMvc = MockMvcBuilders.standaloneSetup(alunoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .nome(DEFAULT_NOME)
            .turma(DEFAULT_TURMA)
            .matricula(DEFAULT_MATRICULA)
            .celular(DEFAULT_CELULAR);
        return aluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createUpdatedEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .nome(UPDATED_NOME)
            .turma(UPDATED_TURMA)
            .matricula(UPDATED_MATRICULA)
            .celular(UPDATED_CELULAR);
        return aluno;
    }

    @BeforeEach
    public void initTest() {
        aluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAluno() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aluno)))
            .andExpect(status().isCreated());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate + 1);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAluno.getTurma()).isEqualTo(DEFAULT_TURMA);
        assertThat(testAluno.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testAluno.getCelular()).isEqualTo(DEFAULT_CELULAR);
    }

    @Test
    @Transactional
    public void createAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno with an existing ID
        aluno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aluno)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNome(null);

        // Create the Aluno, which fails.

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aluno)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlunos() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].turma").value(hasItem(DEFAULT_TURMA)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)));
    }
    
    @Test
    @Transactional
    public void getAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.turma").value(DEFAULT_TURMA))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR));
    }


    @Test
    @Transactional
    public void getAlunosByIdFiltering() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        Long id = aluno.getId();

        defaultAlunoShouldBeFound("id.equals=" + id);
        defaultAlunoShouldNotBeFound("id.notEquals=" + id);

        defaultAlunoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlunoShouldNotBeFound("id.greaterThan=" + id);

        defaultAlunoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlunoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlunosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome equals to DEFAULT_NOME
        defaultAlunoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alunoList where nome equals to UPDATED_NOME
        defaultAlunoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome not equals to DEFAULT_NOME
        defaultAlunoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alunoList where nome not equals to UPDATED_NOME
        defaultAlunoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlunoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alunoList where nome equals to UPDATED_NOME
        defaultAlunoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome is not null
        defaultAlunoShouldBeFound("nome.specified=true");

        // Get all the alunoList where nome is null
        defaultAlunoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlunosByNomeContainsSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome contains DEFAULT_NOME
        defaultAlunoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alunoList where nome contains UPDATED_NOME
        defaultAlunoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome does not contain DEFAULT_NOME
        defaultAlunoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alunoList where nome does not contain UPDATED_NOME
        defaultAlunoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlunosByTurmaIsEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma equals to DEFAULT_TURMA
        defaultAlunoShouldBeFound("turma.equals=" + DEFAULT_TURMA);

        // Get all the alunoList where turma equals to UPDATED_TURMA
        defaultAlunoShouldNotBeFound("turma.equals=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    public void getAllAlunosByTurmaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma not equals to DEFAULT_TURMA
        defaultAlunoShouldNotBeFound("turma.notEquals=" + DEFAULT_TURMA);

        // Get all the alunoList where turma not equals to UPDATED_TURMA
        defaultAlunoShouldBeFound("turma.notEquals=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    public void getAllAlunosByTurmaIsInShouldWork() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma in DEFAULT_TURMA or UPDATED_TURMA
        defaultAlunoShouldBeFound("turma.in=" + DEFAULT_TURMA + "," + UPDATED_TURMA);

        // Get all the alunoList where turma equals to UPDATED_TURMA
        defaultAlunoShouldNotBeFound("turma.in=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    public void getAllAlunosByTurmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma is not null
        defaultAlunoShouldBeFound("turma.specified=true");

        // Get all the alunoList where turma is null
        defaultAlunoShouldNotBeFound("turma.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlunosByTurmaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma is greater than or equal to DEFAULT_TURMA
        defaultAlunoShouldBeFound("turma.greaterThanOrEqual=" + DEFAULT_TURMA);

        // Get all the alunoList where turma is greater than or equal to UPDATED_TURMA
        defaultAlunoShouldNotBeFound("turma.greaterThanOrEqual=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    public void getAllAlunosByTurmaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma is less than or equal to DEFAULT_TURMA
        defaultAlunoShouldBeFound("turma.lessThanOrEqual=" + DEFAULT_TURMA);

        // Get all the alunoList where turma is less than or equal to SMALLER_TURMA
        defaultAlunoShouldNotBeFound("turma.lessThanOrEqual=" + SMALLER_TURMA);
    }

    @Test
    @Transactional
    public void getAllAlunosByTurmaIsLessThanSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma is less than DEFAULT_TURMA
        defaultAlunoShouldNotBeFound("turma.lessThan=" + DEFAULT_TURMA);

        // Get all the alunoList where turma is less than UPDATED_TURMA
        defaultAlunoShouldBeFound("turma.lessThan=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    public void getAllAlunosByTurmaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where turma is greater than DEFAULT_TURMA
        defaultAlunoShouldNotBeFound("turma.greaterThan=" + DEFAULT_TURMA);

        // Get all the alunoList where turma is greater than SMALLER_TURMA
        defaultAlunoShouldBeFound("turma.greaterThan=" + SMALLER_TURMA);
    }


    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula equals to DEFAULT_MATRICULA
        defaultAlunoShouldBeFound("matricula.equals=" + DEFAULT_MATRICULA);

        // Get all the alunoList where matricula equals to UPDATED_MATRICULA
        defaultAlunoShouldNotBeFound("matricula.equals=" + UPDATED_MATRICULA);
    }

    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula not equals to DEFAULT_MATRICULA
        defaultAlunoShouldNotBeFound("matricula.notEquals=" + DEFAULT_MATRICULA);

        // Get all the alunoList where matricula not equals to UPDATED_MATRICULA
        defaultAlunoShouldBeFound("matricula.notEquals=" + UPDATED_MATRICULA);
    }

    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsInShouldWork() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula in DEFAULT_MATRICULA or UPDATED_MATRICULA
        defaultAlunoShouldBeFound("matricula.in=" + DEFAULT_MATRICULA + "," + UPDATED_MATRICULA);

        // Get all the alunoList where matricula equals to UPDATED_MATRICULA
        defaultAlunoShouldNotBeFound("matricula.in=" + UPDATED_MATRICULA);
    }

    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsNullOrNotNull() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula is not null
        defaultAlunoShouldBeFound("matricula.specified=true");

        // Get all the alunoList where matricula is null
        defaultAlunoShouldNotBeFound("matricula.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula is greater than or equal to DEFAULT_MATRICULA
        defaultAlunoShouldBeFound("matricula.greaterThanOrEqual=" + DEFAULT_MATRICULA);

        // Get all the alunoList where matricula is greater than or equal to UPDATED_MATRICULA
        defaultAlunoShouldNotBeFound("matricula.greaterThanOrEqual=" + UPDATED_MATRICULA);
    }

    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula is less than or equal to DEFAULT_MATRICULA
        defaultAlunoShouldBeFound("matricula.lessThanOrEqual=" + DEFAULT_MATRICULA);

        // Get all the alunoList where matricula is less than or equal to SMALLER_MATRICULA
        defaultAlunoShouldNotBeFound("matricula.lessThanOrEqual=" + SMALLER_MATRICULA);
    }

    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsLessThanSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula is less than DEFAULT_MATRICULA
        defaultAlunoShouldNotBeFound("matricula.lessThan=" + DEFAULT_MATRICULA);

        // Get all the alunoList where matricula is less than UPDATED_MATRICULA
        defaultAlunoShouldBeFound("matricula.lessThan=" + UPDATED_MATRICULA);
    }

    @Test
    @Transactional
    public void getAllAlunosByMatriculaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where matricula is greater than DEFAULT_MATRICULA
        defaultAlunoShouldNotBeFound("matricula.greaterThan=" + DEFAULT_MATRICULA);

        // Get all the alunoList where matricula is greater than SMALLER_MATRICULA
        defaultAlunoShouldBeFound("matricula.greaterThan=" + SMALLER_MATRICULA);
    }


    @Test
    @Transactional
    public void getAllAlunosByCelularIsEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where celular equals to DEFAULT_CELULAR
        defaultAlunoShouldBeFound("celular.equals=" + DEFAULT_CELULAR);

        // Get all the alunoList where celular equals to UPDATED_CELULAR
        defaultAlunoShouldNotBeFound("celular.equals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllAlunosByCelularIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where celular not equals to DEFAULT_CELULAR
        defaultAlunoShouldNotBeFound("celular.notEquals=" + DEFAULT_CELULAR);

        // Get all the alunoList where celular not equals to UPDATED_CELULAR
        defaultAlunoShouldBeFound("celular.notEquals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllAlunosByCelularIsInShouldWork() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where celular in DEFAULT_CELULAR or UPDATED_CELULAR
        defaultAlunoShouldBeFound("celular.in=" + DEFAULT_CELULAR + "," + UPDATED_CELULAR);

        // Get all the alunoList where celular equals to UPDATED_CELULAR
        defaultAlunoShouldNotBeFound("celular.in=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllAlunosByCelularIsNullOrNotNull() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where celular is not null
        defaultAlunoShouldBeFound("celular.specified=true");

        // Get all the alunoList where celular is null
        defaultAlunoShouldNotBeFound("celular.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlunosByCelularContainsSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where celular contains DEFAULT_CELULAR
        defaultAlunoShouldBeFound("celular.contains=" + DEFAULT_CELULAR);

        // Get all the alunoList where celular contains UPDATED_CELULAR
        defaultAlunoShouldNotBeFound("celular.contains=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllAlunosByCelularNotContainsSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where celular does not contain DEFAULT_CELULAR
        defaultAlunoShouldNotBeFound("celular.doesNotContain=" + DEFAULT_CELULAR);

        // Get all the alunoList where celular does not contain UPDATED_CELULAR
        defaultAlunoShouldBeFound("celular.doesNotContain=" + UPDATED_CELULAR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlunoShouldBeFound(String filter) throws Exception {
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].turma").value(hasItem(DEFAULT_TURMA)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)));

        // Check, that the count call also returns 1
        restAlunoMockMvc.perform(get("/api/alunos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlunoShouldNotBeFound(String filter) throws Exception {
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlunoMockMvc.perform(get("/api/alunos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAluno() throws Exception {
        // Initialize the database
        alunoService.save(aluno);

        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findById(aluno.getId()).get();
        // Disconnect from session so that the updates on updatedAluno are not directly saved in db
        em.detach(updatedAluno);
        updatedAluno
            .nome(UPDATED_NOME)
            .turma(UPDATED_TURMA)
            .matricula(UPDATED_MATRICULA)
            .celular(UPDATED_CELULAR);

        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAluno)))
            .andExpect(status().isOk());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAluno.getTurma()).isEqualTo(UPDATED_TURMA);
        assertThat(testAluno.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testAluno.getCelular()).isEqualTo(UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void updateNonExistingAluno() throws Exception {
        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Create the Aluno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aluno)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAluno() throws Exception {
        // Initialize the database
        alunoService.save(aluno);

        int databaseSizeBeforeDelete = alunoRepository.findAll().size();

        // Delete the aluno
        restAlunoMockMvc.perform(delete("/api/alunos/{id}", aluno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
