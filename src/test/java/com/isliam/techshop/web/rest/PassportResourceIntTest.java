package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;

import com.isliam.techshop.domain.Passport;
import com.isliam.techshop.domain.Profile;
import com.isliam.techshop.repository.PassportRepository;
import com.isliam.techshop.service.PassportService;
import com.isliam.techshop.service.dto.PassportDTO;
import com.isliam.techshop.service.mapper.PassportMapper;
import com.isliam.techshop.web.rest.errors.ExceptionTranslator;
import com.isliam.techshop.service.dto.PassportCriteria;
import com.isliam.techshop.service.PassportQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.isliam.techshop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PassportResource REST controller.
 *
 * @see PassportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class PassportResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATRONYMIC = "AAAAAAAAAA";
    private static final String UPDATED_PATRONYMIC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private PassportMapper passportMapper;

    @Autowired
    private PassportService passportService;

    @Autowired
    private PassportQueryService passportQueryService;

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

    private MockMvc restPassportMockMvc;

    private Passport passport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PassportResource passportResource = new PassportResource(passportService, passportQueryService);
        this.restPassportMockMvc = MockMvcBuilders.standaloneSetup(passportResource)
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
    public static Passport createEntity(EntityManager em) {
        Passport passport = new Passport()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .patronymic(DEFAULT_PATRONYMIC)
            .dob(DEFAULT_DOB)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .taxId(DEFAULT_TAX_ID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        passport.setProfile(profile);
        return passport;
    }

    @Before
    public void initTest() {
        passport = createEntity(em);
    }

    @Test
    @Transactional
    public void createPassport() throws Exception {
        int databaseSizeBeforeCreate = passportRepository.findAll().size();

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);
        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isCreated());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate + 1);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPassport.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPassport.getPatronymic()).isEqualTo(DEFAULT_PATRONYMIC);
        assertThat(testPassport.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testPassport.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testPassport.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testPassport.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPassportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = passportRepository.findAll().size();

        // Create the Passport with an existing ID
        passport.setId(1L);
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = passportRepository.findAll().size();
        // set the field null
        passport.setFirstName(null);

        // Create the Passport, which fails.
        PassportDTO passportDTO = passportMapper.toDto(passport);

        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isBadRequest());

        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = passportRepository.findAll().size();
        // set the field null
        passport.setLastName(null);

        // Create the Passport, which fails.
        PassportDTO passportDTO = passportMapper.toDto(passport);

        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isBadRequest());

        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSerialNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = passportRepository.findAll().size();
        // set the field null
        passport.setSerialNumber(null);

        // Create the Passport, which fails.
        PassportDTO passportDTO = passportMapper.toDto(passport);

        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isBadRequest());

        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = passportRepository.findAll().size();
        // set the field null
        passport.setTaxId(null);

        // Create the Passport, which fails.
        PassportDTO passportDTO = passportMapper.toDto(passport);

        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isBadRequest());

        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = passportRepository.findAll().size();
        // set the field null
        passport.setActive(null);

        // Create the Passport, which fails.
        PassportDTO passportDTO = passportMapper.toDto(passport);

        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isBadRequest());

        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPassports() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList
        restPassportMockMvc.perform(get("/api/passports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passport.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].patronymic").value(hasItem(DEFAULT_PATRONYMIC.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get the passport
        restPassportMockMvc.perform(get("/api/passports/{id}", passport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(passport.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.patronymic").value(DEFAULT_PATRONYMIC.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPassportsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where firstName equals to DEFAULT_FIRST_NAME
        defaultPassportShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the passportList where firstName equals to UPDATED_FIRST_NAME
        defaultPassportShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPassportsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultPassportShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the passportList where firstName equals to UPDATED_FIRST_NAME
        defaultPassportShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPassportsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where firstName is not null
        defaultPassportShouldBeFound("firstName.specified=true");

        // Get all the passportList where firstName is null
        defaultPassportShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPassportsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where lastName equals to DEFAULT_LAST_NAME
        defaultPassportShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the passportList where lastName equals to UPDATED_LAST_NAME
        defaultPassportShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPassportsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultPassportShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the passportList where lastName equals to UPDATED_LAST_NAME
        defaultPassportShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPassportsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where lastName is not null
        defaultPassportShouldBeFound("lastName.specified=true");

        // Get all the passportList where lastName is null
        defaultPassportShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPassportsByPatronymicIsEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where patronymic equals to DEFAULT_PATRONYMIC
        defaultPassportShouldBeFound("patronymic.equals=" + DEFAULT_PATRONYMIC);

        // Get all the passportList where patronymic equals to UPDATED_PATRONYMIC
        defaultPassportShouldNotBeFound("patronymic.equals=" + UPDATED_PATRONYMIC);
    }

    @Test
    @Transactional
    public void getAllPassportsByPatronymicIsInShouldWork() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where patronymic in DEFAULT_PATRONYMIC or UPDATED_PATRONYMIC
        defaultPassportShouldBeFound("patronymic.in=" + DEFAULT_PATRONYMIC + "," + UPDATED_PATRONYMIC);

        // Get all the passportList where patronymic equals to UPDATED_PATRONYMIC
        defaultPassportShouldNotBeFound("patronymic.in=" + UPDATED_PATRONYMIC);
    }

    @Test
    @Transactional
    public void getAllPassportsByPatronymicIsNullOrNotNull() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where patronymic is not null
        defaultPassportShouldBeFound("patronymic.specified=true");

        // Get all the passportList where patronymic is null
        defaultPassportShouldNotBeFound("patronymic.specified=false");
    }

    @Test
    @Transactional
    public void getAllPassportsByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where dob equals to DEFAULT_DOB
        defaultPassportShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the passportList where dob equals to UPDATED_DOB
        defaultPassportShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllPassportsByDobIsInShouldWork() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultPassportShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the passportList where dob equals to UPDATED_DOB
        defaultPassportShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllPassportsByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where dob is not null
        defaultPassportShouldBeFound("dob.specified=true");

        // Get all the passportList where dob is null
        defaultPassportShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    public void getAllPassportsByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where dob greater than or equals to DEFAULT_DOB
        defaultPassportShouldBeFound("dob.greaterOrEqualThan=" + DEFAULT_DOB);

        // Get all the passportList where dob greater than or equals to UPDATED_DOB
        defaultPassportShouldNotBeFound("dob.greaterOrEqualThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllPassportsByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where dob less than or equals to DEFAULT_DOB
        defaultPassportShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the passportList where dob less than or equals to UPDATED_DOB
        defaultPassportShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }


    @Test
    @Transactional
    public void getAllPassportsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultPassportShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the passportList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultPassportShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPassportsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultPassportShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the passportList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultPassportShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPassportsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where serialNumber is not null
        defaultPassportShouldBeFound("serialNumber.specified=true");

        // Get all the passportList where serialNumber is null
        defaultPassportShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPassportsByTaxIdIsEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where taxId equals to DEFAULT_TAX_ID
        defaultPassportShouldBeFound("taxId.equals=" + DEFAULT_TAX_ID);

        // Get all the passportList where taxId equals to UPDATED_TAX_ID
        defaultPassportShouldNotBeFound("taxId.equals=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllPassportsByTaxIdIsInShouldWork() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where taxId in DEFAULT_TAX_ID or UPDATED_TAX_ID
        defaultPassportShouldBeFound("taxId.in=" + DEFAULT_TAX_ID + "," + UPDATED_TAX_ID);

        // Get all the passportList where taxId equals to UPDATED_TAX_ID
        defaultPassportShouldNotBeFound("taxId.in=" + UPDATED_TAX_ID);
    }

    @Test
    @Transactional
    public void getAllPassportsByTaxIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where taxId is not null
        defaultPassportShouldBeFound("taxId.specified=true");

        // Get all the passportList where taxId is null
        defaultPassportShouldNotBeFound("taxId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPassportsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where active equals to DEFAULT_ACTIVE
        defaultPassportShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the passportList where active equals to UPDATED_ACTIVE
        defaultPassportShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPassportsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultPassportShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the passportList where active equals to UPDATED_ACTIVE
        defaultPassportShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPassportsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList where active is not null
        defaultPassportShouldBeFound("active.specified=true");

        // Get all the passportList where active is null
        defaultPassportShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllPassportsByProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        passport.setProfile(profile);
        passportRepository.saveAndFlush(passport);
        Long profileId = profile.getId();

        // Get all the passportList where profile equals to profileId
        defaultPassportShouldBeFound("profileId.equals=" + profileId);

        // Get all the passportList where profile equals to profileId + 1
        defaultPassportShouldNotBeFound("profileId.equals=" + (profileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPassportShouldBeFound(String filter) throws Exception {
        restPassportMockMvc.perform(get("/api/passports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passport.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].patronymic").value(hasItem(DEFAULT_PATRONYMIC)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restPassportMockMvc.perform(get("/api/passports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPassportShouldNotBeFound(String filter) throws Exception {
        restPassportMockMvc.perform(get("/api/passports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPassportMockMvc.perform(get("/api/passports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPassport() throws Exception {
        // Get the passport
        restPassportMockMvc.perform(get("/api/passports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport
        Passport updatedPassport = passportRepository.findById(passport.getId()).get();
        // Disconnect from session so that the updates on updatedPassport are not directly saved in db
        em.detach(updatedPassport);
        updatedPassport
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .patronymic(UPDATED_PATRONYMIC)
            .dob(UPDATED_DOB)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .taxId(UPDATED_TAX_ID)
            .active(UPDATED_ACTIVE);
        PassportDTO passportDTO = passportMapper.toDto(updatedPassport);

        restPassportMockMvc.perform(put("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPassport.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPassport.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testPassport.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testPassport.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testPassport.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testPassport.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassportMockMvc.perform(put("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeDelete = passportRepository.findAll().size();

        // Delete the passport
        restPassportMockMvc.perform(delete("/api/passports/{id}", passport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passport.class);
        Passport passport1 = new Passport();
        passport1.setId(1L);
        Passport passport2 = new Passport();
        passport2.setId(passport1.getId());
        assertThat(passport1).isEqualTo(passport2);
        passport2.setId(2L);
        assertThat(passport1).isNotEqualTo(passport2);
        passport1.setId(null);
        assertThat(passport1).isNotEqualTo(passport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PassportDTO.class);
        PassportDTO passportDTO1 = new PassportDTO();
        passportDTO1.setId(1L);
        PassportDTO passportDTO2 = new PassportDTO();
        assertThat(passportDTO1).isNotEqualTo(passportDTO2);
        passportDTO2.setId(passportDTO1.getId());
        assertThat(passportDTO1).isEqualTo(passportDTO2);
        passportDTO2.setId(2L);
        assertThat(passportDTO1).isNotEqualTo(passportDTO2);
        passportDTO1.setId(null);
        assertThat(passportDTO1).isNotEqualTo(passportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(passportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(passportMapper.fromId(null)).isNull();
    }
}
