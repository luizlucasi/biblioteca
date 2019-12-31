package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.Autor;
import com.sgaraba.library.domain.Livro;
import com.sgaraba.library.repository.AutorRepository;
import com.sgaraba.library.service.AutorService;
import com.sgaraba.library.web.rest.errors.ExceptionTranslator;
import com.sgaraba.library.service.dto.AutorCriteria;
import com.sgaraba.library.service.AutorQueryService;

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
 * Integration tests for the {@link AutorResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)
public class AutorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private AutorQueryService autorQueryService;

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

    private MockMvc restAutorMockMvc;

    private Autor autor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutorResource autorResource = new AutorResource(autorService, autorQueryService);
        this.restAutorMockMvc = MockMvcBuilders.standaloneSetup(autorResource)
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
    public static Autor createEntity(EntityManager em) {
        Autor autor = new Autor()
            .nome(DEFAULT_NOME)
            .sobrenome(DEFAULT_SOBRENOME);
        return autor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autor createUpdatedEntity(EntityManager em) {
        Autor autor = new Autor()
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME);
        return autor;
    }

    @BeforeEach
    public void initTest() {
        autor = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutor() throws Exception {
        int databaseSizeBeforeCreate = autorRepository.findAll().size();

        // Create the Autor
        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isCreated());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeCreate + 1);
        Autor testAutor = autorList.get(autorList.size() - 1);
        assertThat(testAutor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAutor.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
    }

    @Test
    @Transactional
    public void createAutorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autorRepository.findAll().size();

        // Create the Autor with an existing ID
        autor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isBadRequest());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorRepository.findAll().size();
        // set the field null
        autor.setNome(null);

        // Create the Autor, which fails.

        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isBadRequest());

        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSobrenomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorRepository.findAll().size();
        // set the field null
        autor.setSobrenome(null);

        // Create the Autor, which fails.

        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isBadRequest());

        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutors() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList
        restAutorMockMvc.perform(get("/api/autors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)));
    }
    
    @Test
    @Transactional
    public void getAutor() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get the autor
        restAutorMockMvc.perform(get("/api/autors/{id}", autor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME));
    }


    @Test
    @Transactional
    public void getAutorsByIdFiltering() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        Long id = autor.getId();

        defaultAutorShouldBeFound("id.equals=" + id);
        defaultAutorShouldNotBeFound("id.notEquals=" + id);

        defaultAutorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAutorShouldNotBeFound("id.greaterThan=" + id);

        defaultAutorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAutorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAutorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where nome equals to DEFAULT_NOME
        defaultAutorShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the autorList where nome equals to UPDATED_NOME
        defaultAutorShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where nome not equals to DEFAULT_NOME
        defaultAutorShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the autorList where nome not equals to UPDATED_NOME
        defaultAutorShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAutorShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the autorList where nome equals to UPDATED_NOME
        defaultAutorShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where nome is not null
        defaultAutorShouldBeFound("nome.specified=true");

        // Get all the autorList where nome is null
        defaultAutorShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAutorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where nome contains DEFAULT_NOME
        defaultAutorShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the autorList where nome contains UPDATED_NOME
        defaultAutorShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAutorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where nome does not contain DEFAULT_NOME
        defaultAutorShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the autorList where nome does not contain UPDATED_NOME
        defaultAutorShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAutorsBySobrenomeIsEqualToSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where sobrenome equals to DEFAULT_SOBRENOME
        defaultAutorShouldBeFound("sobrenome.equals=" + DEFAULT_SOBRENOME);

        // Get all the autorList where sobrenome equals to UPDATED_SOBRENOME
        defaultAutorShouldNotBeFound("sobrenome.equals=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllAutorsBySobrenomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where sobrenome not equals to DEFAULT_SOBRENOME
        defaultAutorShouldNotBeFound("sobrenome.notEquals=" + DEFAULT_SOBRENOME);

        // Get all the autorList where sobrenome not equals to UPDATED_SOBRENOME
        defaultAutorShouldBeFound("sobrenome.notEquals=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllAutorsBySobrenomeIsInShouldWork() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where sobrenome in DEFAULT_SOBRENOME or UPDATED_SOBRENOME
        defaultAutorShouldBeFound("sobrenome.in=" + DEFAULT_SOBRENOME + "," + UPDATED_SOBRENOME);

        // Get all the autorList where sobrenome equals to UPDATED_SOBRENOME
        defaultAutorShouldNotBeFound("sobrenome.in=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllAutorsBySobrenomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where sobrenome is not null
        defaultAutorShouldBeFound("sobrenome.specified=true");

        // Get all the autorList where sobrenome is null
        defaultAutorShouldNotBeFound("sobrenome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAutorsBySobrenomeContainsSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where sobrenome contains DEFAULT_SOBRENOME
        defaultAutorShouldBeFound("sobrenome.contains=" + DEFAULT_SOBRENOME);

        // Get all the autorList where sobrenome contains UPDATED_SOBRENOME
        defaultAutorShouldNotBeFound("sobrenome.contains=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllAutorsBySobrenomeNotContainsSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList where sobrenome does not contain DEFAULT_SOBRENOME
        defaultAutorShouldNotBeFound("sobrenome.doesNotContain=" + DEFAULT_SOBRENOME);

        // Get all the autorList where sobrenome does not contain UPDATED_SOBRENOME
        defaultAutorShouldBeFound("sobrenome.doesNotContain=" + UPDATED_SOBRENOME);
    }


    @Test
    @Transactional
    public void getAllAutorsByLivroIsEqualToSomething() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);
        Livro livro = LivroResourceIT.createEntity(em);
        em.persist(livro);
        em.flush();
        autor.addLivro(livro);
        autorRepository.saveAndFlush(autor);
        Long livroId = livro.getId();

        // Get all the autorList where livro equals to livroId
        defaultAutorShouldBeFound("livroId.equals=" + livroId);

        // Get all the autorList where livro equals to livroId + 1
        defaultAutorShouldNotBeFound("livroId.equals=" + (livroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutorShouldBeFound(String filter) throws Exception {
        restAutorMockMvc.perform(get("/api/autors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)));

        // Check, that the count call also returns 1
        restAutorMockMvc.perform(get("/api/autors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutorShouldNotBeFound(String filter) throws Exception {
        restAutorMockMvc.perform(get("/api/autors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutorMockMvc.perform(get("/api/autors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAutor() throws Exception {
        // Get the autor
        restAutorMockMvc.perform(get("/api/autors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutor() throws Exception {
        // Initialize the database
        autorService.save(autor);

        int databaseSizeBeforeUpdate = autorRepository.findAll().size();

        // Update the autor
        Autor updatedAutor = autorRepository.findById(autor.getId()).get();
        // Disconnect from session so that the updates on updatedAutor are not directly saved in db
        em.detach(updatedAutor);
        updatedAutor
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME);

        restAutorMockMvc.perform(put("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAutor)))
            .andExpect(status().isOk());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeUpdate);
        Autor testAutor = autorList.get(autorList.size() - 1);
        assertThat(testAutor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAutor.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void updateNonExistingAutor() throws Exception {
        int databaseSizeBeforeUpdate = autorRepository.findAll().size();

        // Create the Autor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutorMockMvc.perform(put("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isBadRequest());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutor() throws Exception {
        // Initialize the database
        autorService.save(autor);

        int databaseSizeBeforeDelete = autorRepository.findAll().size();

        // Delete the autor
        restAutorMockMvc.perform(delete("/api/autors/{id}", autor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
