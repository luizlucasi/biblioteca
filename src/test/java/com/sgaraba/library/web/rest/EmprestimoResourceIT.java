package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.Emprestimo;
import com.sgaraba.library.domain.Livro;
import com.sgaraba.library.domain.Aluno;
import com.sgaraba.library.repository.EmprestimoRepository;
import com.sgaraba.library.service.EmprestimoService;
import com.sgaraba.library.web.rest.errors.ExceptionTranslator;
import com.sgaraba.library.service.dto.EmprestimoCriteria;
import com.sgaraba.library.service.EmprestimoQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.sgaraba.library.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmprestimoResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)
public class EmprestimoResourceIT {

    private static final LocalDate DEFAULT_DATA_EMPRESTIMO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EMPRESTIMO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_EMPRESTIMO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private EmprestimoQueryService emprestimoQueryService;

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

    private MockMvc restEmprestimoMockMvc;

    private Emprestimo emprestimo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmprestimoResource emprestimoResource = new EmprestimoResource(emprestimoService, emprestimoQueryService);
        this.restEmprestimoMockMvc = MockMvcBuilders.standaloneSetup(emprestimoResource)
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
    public static Emprestimo createEntity(EntityManager em) {
        Emprestimo emprestimo = new Emprestimo()
            .dataEmprestimo(DEFAULT_DATA_EMPRESTIMO);
        return emprestimo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emprestimo createUpdatedEntity(EntityManager em) {
        Emprestimo emprestimo = new Emprestimo()
            .dataEmprestimo(UPDATED_DATA_EMPRESTIMO);
        return emprestimo;
    }

    @BeforeEach
    public void initTest() {
        emprestimo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmprestimo() throws Exception {
        int databaseSizeBeforeCreate = emprestimoRepository.findAll().size();

        // Create the Emprestimo
        restEmprestimoMockMvc.perform(post("/api/emprestimos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emprestimo)))
            .andExpect(status().isCreated());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeCreate + 1);
        Emprestimo testEmprestimo = emprestimoList.get(emprestimoList.size() - 1);
        assertThat(testEmprestimo.getDataEmprestimo()).isEqualTo(DEFAULT_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void createEmprestimoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emprestimoRepository.findAll().size();

        // Create the Emprestimo with an existing ID
        emprestimo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmprestimoMockMvc.perform(post("/api/emprestimos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emprestimo)))
            .andExpect(status().isBadRequest());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmprestimos() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList
        restEmprestimoMockMvc.perform(get("/api/emprestimos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emprestimo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataEmprestimo").value(hasItem(DEFAULT_DATA_EMPRESTIMO.toString())));
    }
    
    @Test
    @Transactional
    public void getEmprestimo() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get the emprestimo
        restEmprestimoMockMvc.perform(get("/api/emprestimos/{id}", emprestimo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emprestimo.getId().intValue()))
            .andExpect(jsonPath("$.dataEmprestimo").value(DEFAULT_DATA_EMPRESTIMO.toString()));
    }


    @Test
    @Transactional
    public void getEmprestimosByIdFiltering() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        Long id = emprestimo.getId();

        defaultEmprestimoShouldBeFound("id.equals=" + id);
        defaultEmprestimoShouldNotBeFound("id.notEquals=" + id);

        defaultEmprestimoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmprestimoShouldNotBeFound("id.greaterThan=" + id);

        defaultEmprestimoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmprestimoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsEqualToSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo equals to DEFAULT_DATA_EMPRESTIMO
        defaultEmprestimoShouldBeFound("dataEmprestimo.equals=" + DEFAULT_DATA_EMPRESTIMO);

        // Get all the emprestimoList where dataEmprestimo equals to UPDATED_DATA_EMPRESTIMO
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.equals=" + UPDATED_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo not equals to DEFAULT_DATA_EMPRESTIMO
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.notEquals=" + DEFAULT_DATA_EMPRESTIMO);

        // Get all the emprestimoList where dataEmprestimo not equals to UPDATED_DATA_EMPRESTIMO
        defaultEmprestimoShouldBeFound("dataEmprestimo.notEquals=" + UPDATED_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsInShouldWork() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo in DEFAULT_DATA_EMPRESTIMO or UPDATED_DATA_EMPRESTIMO
        defaultEmprestimoShouldBeFound("dataEmprestimo.in=" + DEFAULT_DATA_EMPRESTIMO + "," + UPDATED_DATA_EMPRESTIMO);

        // Get all the emprestimoList where dataEmprestimo equals to UPDATED_DATA_EMPRESTIMO
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.in=" + UPDATED_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsNullOrNotNull() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo is not null
        defaultEmprestimoShouldBeFound("dataEmprestimo.specified=true");

        // Get all the emprestimoList where dataEmprestimo is null
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo is greater than or equal to DEFAULT_DATA_EMPRESTIMO
        defaultEmprestimoShouldBeFound("dataEmprestimo.greaterThanOrEqual=" + DEFAULT_DATA_EMPRESTIMO);

        // Get all the emprestimoList where dataEmprestimo is greater than or equal to UPDATED_DATA_EMPRESTIMO
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.greaterThanOrEqual=" + UPDATED_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo is less than or equal to DEFAULT_DATA_EMPRESTIMO
        defaultEmprestimoShouldBeFound("dataEmprestimo.lessThanOrEqual=" + DEFAULT_DATA_EMPRESTIMO);

        // Get all the emprestimoList where dataEmprestimo is less than or equal to SMALLER_DATA_EMPRESTIMO
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.lessThanOrEqual=" + SMALLER_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsLessThanSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo is less than DEFAULT_DATA_EMPRESTIMO
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.lessThan=" + DEFAULT_DATA_EMPRESTIMO);

        // Get all the emprestimoList where dataEmprestimo is less than UPDATED_DATA_EMPRESTIMO
        defaultEmprestimoShouldBeFound("dataEmprestimo.lessThan=" + UPDATED_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void getAllEmprestimosByDataEmprestimoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);

        // Get all the emprestimoList where dataEmprestimo is greater than DEFAULT_DATA_EMPRESTIMO
        defaultEmprestimoShouldNotBeFound("dataEmprestimo.greaterThan=" + DEFAULT_DATA_EMPRESTIMO);

        // Get all the emprestimoList where dataEmprestimo is greater than SMALLER_DATA_EMPRESTIMO
        defaultEmprestimoShouldBeFound("dataEmprestimo.greaterThan=" + SMALLER_DATA_EMPRESTIMO);
    }


    @Test
    @Transactional
    public void getAllEmprestimosByLivroIsEqualToSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);
        Livro livro = LivroResourceIT.createEntity(em);
        em.persist(livro);
        em.flush();
        emprestimo.setLivro(livro);
        emprestimoRepository.saveAndFlush(emprestimo);
        Long livroId = livro.getId();

        // Get all the emprestimoList where livro equals to livroId
        defaultEmprestimoShouldBeFound("livroId.equals=" + livroId);

        // Get all the emprestimoList where livro equals to livroId + 1
        defaultEmprestimoShouldNotBeFound("livroId.equals=" + (livroId + 1));
    }


    @Test
    @Transactional
    public void getAllEmprestimosByAlunoIsEqualToSomething() throws Exception {
        // Initialize the database
        emprestimoRepository.saveAndFlush(emprestimo);
        Aluno aluno = AlunoResourceIT.createEntity(em);
        em.persist(aluno);
        em.flush();
        emprestimo.setAluno(aluno);
        emprestimoRepository.saveAndFlush(emprestimo);
        Long alunoId = aluno.getId();

        // Get all the emprestimoList where aluno equals to alunoId
        defaultEmprestimoShouldBeFound("alunoId.equals=" + alunoId);

        // Get all the emprestimoList where aluno equals to alunoId + 1
        defaultEmprestimoShouldNotBeFound("alunoId.equals=" + (alunoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmprestimoShouldBeFound(String filter) throws Exception {
        restEmprestimoMockMvc.perform(get("/api/emprestimos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emprestimo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataEmprestimo").value(hasItem(DEFAULT_DATA_EMPRESTIMO.toString())));

        // Check, that the count call also returns 1
        restEmprestimoMockMvc.perform(get("/api/emprestimos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmprestimoShouldNotBeFound(String filter) throws Exception {
        restEmprestimoMockMvc.perform(get("/api/emprestimos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmprestimoMockMvc.perform(get("/api/emprestimos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmprestimo() throws Exception {
        // Get the emprestimo
        restEmprestimoMockMvc.perform(get("/api/emprestimos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmprestimo() throws Exception {
        // Initialize the database
        emprestimoService.save(emprestimo);

        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();

        // Update the emprestimo
        Emprestimo updatedEmprestimo = emprestimoRepository.findById(emprestimo.getId()).get();
        // Disconnect from session so that the updates on updatedEmprestimo are not directly saved in db
        em.detach(updatedEmprestimo);
        updatedEmprestimo
            .dataEmprestimo(UPDATED_DATA_EMPRESTIMO);

        restEmprestimoMockMvc.perform(put("/api/emprestimos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmprestimo)))
            .andExpect(status().isOk());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
        Emprestimo testEmprestimo = emprestimoList.get(emprestimoList.size() - 1);
        assertThat(testEmprestimo.getDataEmprestimo()).isEqualTo(UPDATED_DATA_EMPRESTIMO);
    }

    @Test
    @Transactional
    public void updateNonExistingEmprestimo() throws Exception {
        int databaseSizeBeforeUpdate = emprestimoRepository.findAll().size();

        // Create the Emprestimo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmprestimoMockMvc.perform(put("/api/emprestimos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emprestimo)))
            .andExpect(status().isBadRequest());

        // Validate the Emprestimo in the database
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmprestimo() throws Exception {
        // Initialize the database
        emprestimoService.save(emprestimo);

        int databaseSizeBeforeDelete = emprestimoRepository.findAll().size();

        // Delete the emprestimo
        restEmprestimoMockMvc.perform(delete("/api/emprestimos/{id}", emprestimo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emprestimo> emprestimoList = emprestimoRepository.findAll();
        assertThat(emprestimoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
