package com.sgaraba.library.web.rest;

import com.sgaraba.library.LibraryApp;
import com.sgaraba.library.domain.Livro;
import com.sgaraba.library.domain.Autor;
import com.sgaraba.library.repository.LivroRepository;
import com.sgaraba.library.service.LivroService;
import com.sgaraba.library.web.rest.errors.ExceptionTranslator;
import com.sgaraba.library.service.dto.LivroCriteria;
import com.sgaraba.library.service.LivroQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.sgaraba.library.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LivroResource} REST controller.
 */
@SpringBootTest(classes = LibraryApp.class)
public class LivroResourceIT {

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EDITORA = "AAAAAAAAAA";
    private static final String UPDATED_EDITORA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CAPA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CAPA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CAPA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CAPA_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_TOMBO = 1;
    private static final Integer UPDATED_TOMBO = 2;
    private static final Integer SMALLER_TOMBO = 1 - 1;

    @Autowired
    private LivroRepository livroRepository;

    @Mock
    private LivroRepository livroRepositoryMock;

    @Mock
    private LivroService livroServiceMock;

    @Autowired
    private LivroService livroService;

    @Autowired
    private LivroQueryService livroQueryService;

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

    private MockMvc restLivroMockMvc;

    private Livro livro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LivroResource livroResource = new LivroResource(livroService, livroQueryService);
        this.restLivroMockMvc = MockMvcBuilders.standaloneSetup(livroResource)
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
    public static Livro createEntity(EntityManager em) {
        Livro livro = new Livro()
            .isbn(DEFAULT_ISBN)
            .nome(DEFAULT_NOME)
            .editora(DEFAULT_EDITORA)
            .capa(DEFAULT_CAPA)
            .capaContentType(DEFAULT_CAPA_CONTENT_TYPE)
            .tombo(DEFAULT_TOMBO);
        return livro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livro createUpdatedEntity(EntityManager em) {
        Livro livro = new Livro()
            .isbn(UPDATED_ISBN)
            .nome(UPDATED_NOME)
            .editora(UPDATED_EDITORA)
            .capa(UPDATED_CAPA)
            .capaContentType(UPDATED_CAPA_CONTENT_TYPE)
            .tombo(UPDATED_TOMBO);
        return livro;
    }

    @BeforeEach
    public void initTest() {
        livro = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivro() throws Exception {
        int databaseSizeBeforeCreate = livroRepository.findAll().size();

        // Create the Livro
        restLivroMockMvc.perform(post("/api/livros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isCreated());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeCreate + 1);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testLivro.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLivro.getEditora()).isEqualTo(DEFAULT_EDITORA);
        assertThat(testLivro.getCapa()).isEqualTo(DEFAULT_CAPA);
        assertThat(testLivro.getCapaContentType()).isEqualTo(DEFAULT_CAPA_CONTENT_TYPE);
        assertThat(testLivro.getTombo()).isEqualTo(DEFAULT_TOMBO);
    }

    @Test
    @Transactional
    public void createLivroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livroRepository.findAll().size();

        // Create the Livro with an existing ID
        livro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivroMockMvc.perform(post("/api/livros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsbnIsRequired() throws Exception {
        int databaseSizeBeforeTest = livroRepository.findAll().size();
        // set the field null
        livro.setIsbn(null);

        // Create the Livro, which fails.

        restLivroMockMvc.perform(post("/api/livros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = livroRepository.findAll().size();
        // set the field null
        livro.setNome(null);

        // Create the Livro, which fails.

        restLivroMockMvc.perform(post("/api/livros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEditoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = livroRepository.findAll().size();
        // set the field null
        livro.setEditora(null);

        // Create the Livro, which fails.

        restLivroMockMvc.perform(post("/api/livros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLivros() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList
        restLivroMockMvc.perform(get("/api/livros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livro.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].editora").value(hasItem(DEFAULT_EDITORA)))
            .andExpect(jsonPath("$.[*].capaContentType").value(hasItem(DEFAULT_CAPA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].capa").value(hasItem(Base64Utils.encodeToString(DEFAULT_CAPA))))
            .andExpect(jsonPath("$.[*].tombo").value(hasItem(DEFAULT_TOMBO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllLivrosWithEagerRelationshipsIsEnabled() throws Exception {
        LivroResource livroResource = new LivroResource(livroServiceMock, livroQueryService);
        when(livroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restLivroMockMvc = MockMvcBuilders.standaloneSetup(livroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLivroMockMvc.perform(get("/api/livros?eagerload=true"))
        .andExpect(status().isOk());

        verify(livroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllLivrosWithEagerRelationshipsIsNotEnabled() throws Exception {
        LivroResource livroResource = new LivroResource(livroServiceMock, livroQueryService);
            when(livroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restLivroMockMvc = MockMvcBuilders.standaloneSetup(livroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLivroMockMvc.perform(get("/api/livros?eagerload=true"))
        .andExpect(status().isOk());

            verify(livroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getLivro() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get the livro
        restLivroMockMvc.perform(get("/api/livros/{id}", livro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(livro.getId().intValue()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.editora").value(DEFAULT_EDITORA))
            .andExpect(jsonPath("$.capaContentType").value(DEFAULT_CAPA_CONTENT_TYPE))
            .andExpect(jsonPath("$.capa").value(Base64Utils.encodeToString(DEFAULT_CAPA)))
            .andExpect(jsonPath("$.tombo").value(DEFAULT_TOMBO));
    }


    @Test
    @Transactional
    public void getLivrosByIdFiltering() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        Long id = livro.getId();

        defaultLivroShouldBeFound("id.equals=" + id);
        defaultLivroShouldNotBeFound("id.notEquals=" + id);

        defaultLivroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLivroShouldNotBeFound("id.greaterThan=" + id);

        defaultLivroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLivroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLivrosByIsbnIsEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where isbn equals to DEFAULT_ISBN
        defaultLivroShouldBeFound("isbn.equals=" + DEFAULT_ISBN);

        // Get all the livroList where isbn equals to UPDATED_ISBN
        defaultLivroShouldNotBeFound("isbn.equals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllLivrosByIsbnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where isbn not equals to DEFAULT_ISBN
        defaultLivroShouldNotBeFound("isbn.notEquals=" + DEFAULT_ISBN);

        // Get all the livroList where isbn not equals to UPDATED_ISBN
        defaultLivroShouldBeFound("isbn.notEquals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllLivrosByIsbnIsInShouldWork() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where isbn in DEFAULT_ISBN or UPDATED_ISBN
        defaultLivroShouldBeFound("isbn.in=" + DEFAULT_ISBN + "," + UPDATED_ISBN);

        // Get all the livroList where isbn equals to UPDATED_ISBN
        defaultLivroShouldNotBeFound("isbn.in=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllLivrosByIsbnIsNullOrNotNull() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where isbn is not null
        defaultLivroShouldBeFound("isbn.specified=true");

        // Get all the livroList where isbn is null
        defaultLivroShouldNotBeFound("isbn.specified=false");
    }
                @Test
    @Transactional
    public void getAllLivrosByIsbnContainsSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where isbn contains DEFAULT_ISBN
        defaultLivroShouldBeFound("isbn.contains=" + DEFAULT_ISBN);

        // Get all the livroList where isbn contains UPDATED_ISBN
        defaultLivroShouldNotBeFound("isbn.contains=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllLivrosByIsbnNotContainsSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where isbn does not contain DEFAULT_ISBN
        defaultLivroShouldNotBeFound("isbn.doesNotContain=" + DEFAULT_ISBN);

        // Get all the livroList where isbn does not contain UPDATED_ISBN
        defaultLivroShouldBeFound("isbn.doesNotContain=" + UPDATED_ISBN);
    }


    @Test
    @Transactional
    public void getAllLivrosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where nome equals to DEFAULT_NOME
        defaultLivroShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the livroList where nome equals to UPDATED_NOME
        defaultLivroShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLivrosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where nome not equals to DEFAULT_NOME
        defaultLivroShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the livroList where nome not equals to UPDATED_NOME
        defaultLivroShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLivrosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultLivroShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the livroList where nome equals to UPDATED_NOME
        defaultLivroShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLivrosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where nome is not null
        defaultLivroShouldBeFound("nome.specified=true");

        // Get all the livroList where nome is null
        defaultLivroShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllLivrosByNomeContainsSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where nome contains DEFAULT_NOME
        defaultLivroShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the livroList where nome contains UPDATED_NOME
        defaultLivroShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLivrosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where nome does not contain DEFAULT_NOME
        defaultLivroShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the livroList where nome does not contain UPDATED_NOME
        defaultLivroShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllLivrosByEditoraIsEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where editora equals to DEFAULT_EDITORA
        defaultLivroShouldBeFound("editora.equals=" + DEFAULT_EDITORA);

        // Get all the livroList where editora equals to UPDATED_EDITORA
        defaultLivroShouldNotBeFound("editora.equals=" + UPDATED_EDITORA);
    }

    @Test
    @Transactional
    public void getAllLivrosByEditoraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where editora not equals to DEFAULT_EDITORA
        defaultLivroShouldNotBeFound("editora.notEquals=" + DEFAULT_EDITORA);

        // Get all the livroList where editora not equals to UPDATED_EDITORA
        defaultLivroShouldBeFound("editora.notEquals=" + UPDATED_EDITORA);
    }

    @Test
    @Transactional
    public void getAllLivrosByEditoraIsInShouldWork() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where editora in DEFAULT_EDITORA or UPDATED_EDITORA
        defaultLivroShouldBeFound("editora.in=" + DEFAULT_EDITORA + "," + UPDATED_EDITORA);

        // Get all the livroList where editora equals to UPDATED_EDITORA
        defaultLivroShouldNotBeFound("editora.in=" + UPDATED_EDITORA);
    }

    @Test
    @Transactional
    public void getAllLivrosByEditoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where editora is not null
        defaultLivroShouldBeFound("editora.specified=true");

        // Get all the livroList where editora is null
        defaultLivroShouldNotBeFound("editora.specified=false");
    }
                @Test
    @Transactional
    public void getAllLivrosByEditoraContainsSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where editora contains DEFAULT_EDITORA
        defaultLivroShouldBeFound("editora.contains=" + DEFAULT_EDITORA);

        // Get all the livroList where editora contains UPDATED_EDITORA
        defaultLivroShouldNotBeFound("editora.contains=" + UPDATED_EDITORA);
    }

    @Test
    @Transactional
    public void getAllLivrosByEditoraNotContainsSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where editora does not contain DEFAULT_EDITORA
        defaultLivroShouldNotBeFound("editora.doesNotContain=" + DEFAULT_EDITORA);

        // Get all the livroList where editora does not contain UPDATED_EDITORA
        defaultLivroShouldBeFound("editora.doesNotContain=" + UPDATED_EDITORA);
    }


    @Test
    @Transactional
    public void getAllLivrosByTomboIsEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo equals to DEFAULT_TOMBO
        defaultLivroShouldBeFound("tombo.equals=" + DEFAULT_TOMBO);

        // Get all the livroList where tombo equals to UPDATED_TOMBO
        defaultLivroShouldNotBeFound("tombo.equals=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    public void getAllLivrosByTomboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo not equals to DEFAULT_TOMBO
        defaultLivroShouldNotBeFound("tombo.notEquals=" + DEFAULT_TOMBO);

        // Get all the livroList where tombo not equals to UPDATED_TOMBO
        defaultLivroShouldBeFound("tombo.notEquals=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    public void getAllLivrosByTomboIsInShouldWork() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo in DEFAULT_TOMBO or UPDATED_TOMBO
        defaultLivroShouldBeFound("tombo.in=" + DEFAULT_TOMBO + "," + UPDATED_TOMBO);

        // Get all the livroList where tombo equals to UPDATED_TOMBO
        defaultLivroShouldNotBeFound("tombo.in=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    public void getAllLivrosByTomboIsNullOrNotNull() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo is not null
        defaultLivroShouldBeFound("tombo.specified=true");

        // Get all the livroList where tombo is null
        defaultLivroShouldNotBeFound("tombo.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivrosByTomboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo is greater than or equal to DEFAULT_TOMBO
        defaultLivroShouldBeFound("tombo.greaterThanOrEqual=" + DEFAULT_TOMBO);

        // Get all the livroList where tombo is greater than or equal to UPDATED_TOMBO
        defaultLivroShouldNotBeFound("tombo.greaterThanOrEqual=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    public void getAllLivrosByTomboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo is less than or equal to DEFAULT_TOMBO
        defaultLivroShouldBeFound("tombo.lessThanOrEqual=" + DEFAULT_TOMBO);

        // Get all the livroList where tombo is less than or equal to SMALLER_TOMBO
        defaultLivroShouldNotBeFound("tombo.lessThanOrEqual=" + SMALLER_TOMBO);
    }

    @Test
    @Transactional
    public void getAllLivrosByTomboIsLessThanSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo is less than DEFAULT_TOMBO
        defaultLivroShouldNotBeFound("tombo.lessThan=" + DEFAULT_TOMBO);

        // Get all the livroList where tombo is less than UPDATED_TOMBO
        defaultLivroShouldBeFound("tombo.lessThan=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    public void getAllLivrosByTomboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList where tombo is greater than DEFAULT_TOMBO
        defaultLivroShouldNotBeFound("tombo.greaterThan=" + DEFAULT_TOMBO);

        // Get all the livroList where tombo is greater than SMALLER_TOMBO
        defaultLivroShouldBeFound("tombo.greaterThan=" + SMALLER_TOMBO);
    }


    @Test
    @Transactional
    public void getAllLivrosByAutorIsEqualToSomething() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);
        Autor autor = AutorResourceIT.createEntity(em);
        em.persist(autor);
        em.flush();
        livro.addAutor(autor);
        livroRepository.saveAndFlush(livro);
        Long autorId = autor.getId();

        // Get all the livroList where autor equals to autorId
        defaultLivroShouldBeFound("autorId.equals=" + autorId);

        // Get all the livroList where autor equals to autorId + 1
        defaultLivroShouldNotBeFound("autorId.equals=" + (autorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLivroShouldBeFound(String filter) throws Exception {
        restLivroMockMvc.perform(get("/api/livros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livro.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].editora").value(hasItem(DEFAULT_EDITORA)))
            .andExpect(jsonPath("$.[*].capaContentType").value(hasItem(DEFAULT_CAPA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].capa").value(hasItem(Base64Utils.encodeToString(DEFAULT_CAPA))))
            .andExpect(jsonPath("$.[*].tombo").value(hasItem(DEFAULT_TOMBO)));

        // Check, that the count call also returns 1
        restLivroMockMvc.perform(get("/api/livros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLivroShouldNotBeFound(String filter) throws Exception {
        restLivroMockMvc.perform(get("/api/livros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLivroMockMvc.perform(get("/api/livros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLivro() throws Exception {
        // Get the livro
        restLivroMockMvc.perform(get("/api/livros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivro() throws Exception {
        // Initialize the database
        livroService.save(livro);

        int databaseSizeBeforeUpdate = livroRepository.findAll().size();

        // Update the livro
        Livro updatedLivro = livroRepository.findById(livro.getId()).get();
        // Disconnect from session so that the updates on updatedLivro are not directly saved in db
        em.detach(updatedLivro);
        updatedLivro
            .isbn(UPDATED_ISBN)
            .nome(UPDATED_NOME)
            .editora(UPDATED_EDITORA)
            .capa(UPDATED_CAPA)
            .capaContentType(UPDATED_CAPA_CONTENT_TYPE)
            .tombo(UPDATED_TOMBO);

        restLivroMockMvc.perform(put("/api/livros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLivro)))
            .andExpect(status().isOk());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testLivro.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLivro.getEditora()).isEqualTo(UPDATED_EDITORA);
        assertThat(testLivro.getCapa()).isEqualTo(UPDATED_CAPA);
        assertThat(testLivro.getCapaContentType()).isEqualTo(UPDATED_CAPA_CONTENT_TYPE);
        assertThat(testLivro.getTombo()).isEqualTo(UPDATED_TOMBO);
    }

    @Test
    @Transactional
    public void updateNonExistingLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();

        // Create the Livro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivroMockMvc.perform(put("/api/livros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivro() throws Exception {
        // Initialize the database
        livroService.save(livro);

        int databaseSizeBeforeDelete = livroRepository.findAll().size();

        // Delete the livro
        restLivroMockMvc.perform(delete("/api/livros/{id}", livro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
