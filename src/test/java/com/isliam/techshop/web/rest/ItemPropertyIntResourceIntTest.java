package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;

import com.isliam.techshop.domain.ItemPropertyInt;
import com.isliam.techshop.domain.Item;
import com.isliam.techshop.domain.Property;
import com.isliam.techshop.repository.ItemPropertyIntRepository;
import com.isliam.techshop.service.ItemPropertyIntService;
import com.isliam.techshop.service.dto.ItemPropertyIntDTO;
import com.isliam.techshop.service.mapper.ItemPropertyIntMapper;
import com.isliam.techshop.web.rest.errors.ExceptionTranslator;
import com.isliam.techshop.service.dto.ItemPropertyIntCriteria;
import com.isliam.techshop.service.ItemPropertyIntQueryService;

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
import java.util.List;


import static com.isliam.techshop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemPropertyIntResource REST controller.
 *
 * @see ItemPropertyIntResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class ItemPropertyIntResourceIntTest {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private ItemPropertyIntRepository itemPropertyIntRepository;

    @Autowired
    private ItemPropertyIntMapper itemPropertyIntMapper;

    @Autowired
    private ItemPropertyIntService itemPropertyIntService;

    @Autowired
    private ItemPropertyIntQueryService itemPropertyIntQueryService;

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

    private MockMvc restItemPropertyIntMockMvc;

    private ItemPropertyInt itemPropertyInt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPropertyIntResource itemPropertyIntResource = new ItemPropertyIntResource(itemPropertyIntService, itemPropertyIntQueryService);
        this.restItemPropertyIntMockMvc = MockMvcBuilders.standaloneSetup(itemPropertyIntResource)
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
    public static ItemPropertyInt createEntity(EntityManager em) {
        ItemPropertyInt itemPropertyInt = new ItemPropertyInt()
            .value(DEFAULT_VALUE);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyInt.setItem(item);
        // Add required entity
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyInt.setProperty(property);
        return itemPropertyInt;
    }

    @Before
    public void initTest() {
        itemPropertyInt = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPropertyInt() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyIntRepository.findAll().size();

        // Create the ItemPropertyInt
        ItemPropertyIntDTO itemPropertyIntDTO = itemPropertyIntMapper.toDto(itemPropertyInt);
        restItemPropertyIntMockMvc.perform(post("/api/item-property-ints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyIntDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPropertyInt in the database
        List<ItemPropertyInt> itemPropertyIntList = itemPropertyIntRepository.findAll();
        assertThat(itemPropertyIntList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPropertyInt testItemPropertyInt = itemPropertyIntList.get(itemPropertyIntList.size() - 1);
        assertThat(testItemPropertyInt.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createItemPropertyIntWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyIntRepository.findAll().size();

        // Create the ItemPropertyInt with an existing ID
        itemPropertyInt.setId(1L);
        ItemPropertyIntDTO itemPropertyIntDTO = itemPropertyIntMapper.toDto(itemPropertyInt);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPropertyIntMockMvc.perform(post("/api/item-property-ints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyIntDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyInt in the database
        List<ItemPropertyInt> itemPropertyIntList = itemPropertyIntRepository.findAll();
        assertThat(itemPropertyIntList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllItemPropertyInts() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        // Get all the itemPropertyIntList
        restItemPropertyIntMockMvc.perform(get("/api/item-property-ints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyInt.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getItemPropertyInt() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        // Get the itemPropertyInt
        restItemPropertyIntMockMvc.perform(get("/api/item-property-ints/{id}", itemPropertyInt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPropertyInt.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getAllItemPropertyIntsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        // Get all the itemPropertyIntList where value equals to DEFAULT_VALUE
        defaultItemPropertyIntShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the itemPropertyIntList where value equals to UPDATED_VALUE
        defaultItemPropertyIntShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyIntsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        // Get all the itemPropertyIntList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultItemPropertyIntShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the itemPropertyIntList where value equals to UPDATED_VALUE
        defaultItemPropertyIntShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyIntsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        // Get all the itemPropertyIntList where value is not null
        defaultItemPropertyIntShouldBeFound("value.specified=true");

        // Get all the itemPropertyIntList where value is null
        defaultItemPropertyIntShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPropertyIntsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        // Get all the itemPropertyIntList where value greater than or equals to DEFAULT_VALUE
        defaultItemPropertyIntShouldBeFound("value.greaterOrEqualThan=" + DEFAULT_VALUE);

        // Get all the itemPropertyIntList where value greater than or equals to UPDATED_VALUE
        defaultItemPropertyIntShouldNotBeFound("value.greaterOrEqualThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyIntsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        // Get all the itemPropertyIntList where value less than or equals to DEFAULT_VALUE
        defaultItemPropertyIntShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the itemPropertyIntList where value less than or equals to UPDATED_VALUE
        defaultItemPropertyIntShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllItemPropertyIntsByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyInt.setItem(item);
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);
        Long itemId = item.getId();

        // Get all the itemPropertyIntList where item equals to itemId
        defaultItemPropertyIntShouldBeFound("itemId.equals=" + itemId);

        // Get all the itemPropertyIntList where item equals to itemId + 1
        defaultItemPropertyIntShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPropertyIntsByPropertyIsEqualToSomething() throws Exception {
        // Initialize the database
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyInt.setProperty(property);
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);
        Long propertyId = property.getId();

        // Get all the itemPropertyIntList where property equals to propertyId
        defaultItemPropertyIntShouldBeFound("propertyId.equals=" + propertyId);

        // Get all the itemPropertyIntList where property equals to propertyId + 1
        defaultItemPropertyIntShouldNotBeFound("propertyId.equals=" + (propertyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemPropertyIntShouldBeFound(String filter) throws Exception {
        restItemPropertyIntMockMvc.perform(get("/api/item-property-ints?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyInt.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restItemPropertyIntMockMvc.perform(get("/api/item-property-ints/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemPropertyIntShouldNotBeFound(String filter) throws Exception {
        restItemPropertyIntMockMvc.perform(get("/api/item-property-ints?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemPropertyIntMockMvc.perform(get("/api/item-property-ints/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemPropertyInt() throws Exception {
        // Get the itemPropertyInt
        restItemPropertyIntMockMvc.perform(get("/api/item-property-ints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPropertyInt() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        int databaseSizeBeforeUpdate = itemPropertyIntRepository.findAll().size();

        // Update the itemPropertyInt
        ItemPropertyInt updatedItemPropertyInt = itemPropertyIntRepository.findById(itemPropertyInt.getId()).get();
        // Disconnect from session so that the updates on updatedItemPropertyInt are not directly saved in db
        em.detach(updatedItemPropertyInt);
        updatedItemPropertyInt
            .value(UPDATED_VALUE);
        ItemPropertyIntDTO itemPropertyIntDTO = itemPropertyIntMapper.toDto(updatedItemPropertyInt);

        restItemPropertyIntMockMvc.perform(put("/api/item-property-ints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyIntDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPropertyInt in the database
        List<ItemPropertyInt> itemPropertyIntList = itemPropertyIntRepository.findAll();
        assertThat(itemPropertyIntList).hasSize(databaseSizeBeforeUpdate);
        ItemPropertyInt testItemPropertyInt = itemPropertyIntList.get(itemPropertyIntList.size() - 1);
        assertThat(testItemPropertyInt.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPropertyInt() throws Exception {
        int databaseSizeBeforeUpdate = itemPropertyIntRepository.findAll().size();

        // Create the ItemPropertyInt
        ItemPropertyIntDTO itemPropertyIntDTO = itemPropertyIntMapper.toDto(itemPropertyInt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPropertyIntMockMvc.perform(put("/api/item-property-ints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyIntDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyInt in the database
        List<ItemPropertyInt> itemPropertyIntList = itemPropertyIntRepository.findAll();
        assertThat(itemPropertyIntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemPropertyInt() throws Exception {
        // Initialize the database
        itemPropertyIntRepository.saveAndFlush(itemPropertyInt);

        int databaseSizeBeforeDelete = itemPropertyIntRepository.findAll().size();

        // Delete the itemPropertyInt
        restItemPropertyIntMockMvc.perform(delete("/api/item-property-ints/{id}", itemPropertyInt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPropertyInt> itemPropertyIntList = itemPropertyIntRepository.findAll();
        assertThat(itemPropertyIntList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyInt.class);
        ItemPropertyInt itemPropertyInt1 = new ItemPropertyInt();
        itemPropertyInt1.setId(1L);
        ItemPropertyInt itemPropertyInt2 = new ItemPropertyInt();
        itemPropertyInt2.setId(itemPropertyInt1.getId());
        assertThat(itemPropertyInt1).isEqualTo(itemPropertyInt2);
        itemPropertyInt2.setId(2L);
        assertThat(itemPropertyInt1).isNotEqualTo(itemPropertyInt2);
        itemPropertyInt1.setId(null);
        assertThat(itemPropertyInt1).isNotEqualTo(itemPropertyInt2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyIntDTO.class);
        ItemPropertyIntDTO itemPropertyIntDTO1 = new ItemPropertyIntDTO();
        itemPropertyIntDTO1.setId(1L);
        ItemPropertyIntDTO itemPropertyIntDTO2 = new ItemPropertyIntDTO();
        assertThat(itemPropertyIntDTO1).isNotEqualTo(itemPropertyIntDTO2);
        itemPropertyIntDTO2.setId(itemPropertyIntDTO1.getId());
        assertThat(itemPropertyIntDTO1).isEqualTo(itemPropertyIntDTO2);
        itemPropertyIntDTO2.setId(2L);
        assertThat(itemPropertyIntDTO1).isNotEqualTo(itemPropertyIntDTO2);
        itemPropertyIntDTO1.setId(null);
        assertThat(itemPropertyIntDTO1).isNotEqualTo(itemPropertyIntDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPropertyIntMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPropertyIntMapper.fromId(null)).isNull();
    }
}
