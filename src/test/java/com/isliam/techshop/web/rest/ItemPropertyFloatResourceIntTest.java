package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;

import com.isliam.techshop.domain.ItemPropertyFloat;
import com.isliam.techshop.domain.Item;
import com.isliam.techshop.domain.Property;
import com.isliam.techshop.repository.ItemPropertyFloatRepository;
import com.isliam.techshop.service.ItemPropertyFloatService;
import com.isliam.techshop.service.dto.ItemPropertyFloatDTO;
import com.isliam.techshop.service.mapper.ItemPropertyFloatMapper;
import com.isliam.techshop.web.rest.errors.ExceptionTranslator;
import com.isliam.techshop.service.dto.ItemPropertyFloatCriteria;
import com.isliam.techshop.service.ItemPropertyFloatQueryService;

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
 * Test class for the ItemPropertyFloatResource REST controller.
 *
 * @see ItemPropertyFloatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class ItemPropertyFloatResourceIntTest {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    @Autowired
    private ItemPropertyFloatRepository itemPropertyFloatRepository;

    @Autowired
    private ItemPropertyFloatMapper itemPropertyFloatMapper;

    @Autowired
    private ItemPropertyFloatService itemPropertyFloatService;

    @Autowired
    private ItemPropertyFloatQueryService itemPropertyFloatQueryService;

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

    private MockMvc restItemPropertyFloatMockMvc;

    private ItemPropertyFloat itemPropertyFloat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPropertyFloatResource itemPropertyFloatResource = new ItemPropertyFloatResource(itemPropertyFloatService, itemPropertyFloatQueryService);
        this.restItemPropertyFloatMockMvc = MockMvcBuilders.standaloneSetup(itemPropertyFloatResource)
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
    public static ItemPropertyFloat createEntity(EntityManager em) {
        ItemPropertyFloat itemPropertyFloat = new ItemPropertyFloat()
            .value(DEFAULT_VALUE);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyFloat.setItem(item);
        // Add required entity
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyFloat.setProperty(property);
        return itemPropertyFloat;
    }

    @Before
    public void initTest() {
        itemPropertyFloat = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPropertyFloat() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyFloatRepository.findAll().size();

        // Create the ItemPropertyFloat
        ItemPropertyFloatDTO itemPropertyFloatDTO = itemPropertyFloatMapper.toDto(itemPropertyFloat);
        restItemPropertyFloatMockMvc.perform(post("/api/item-property-floats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyFloatDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPropertyFloat in the database
        List<ItemPropertyFloat> itemPropertyFloatList = itemPropertyFloatRepository.findAll();
        assertThat(itemPropertyFloatList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPropertyFloat testItemPropertyFloat = itemPropertyFloatList.get(itemPropertyFloatList.size() - 1);
        assertThat(testItemPropertyFloat.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createItemPropertyFloatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyFloatRepository.findAll().size();

        // Create the ItemPropertyFloat with an existing ID
        itemPropertyFloat.setId(1L);
        ItemPropertyFloatDTO itemPropertyFloatDTO = itemPropertyFloatMapper.toDto(itemPropertyFloat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPropertyFloatMockMvc.perform(post("/api/item-property-floats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyFloatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyFloat in the database
        List<ItemPropertyFloat> itemPropertyFloatList = itemPropertyFloatRepository.findAll();
        assertThat(itemPropertyFloatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllItemPropertyFloats() throws Exception {
        // Initialize the database
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);

        // Get all the itemPropertyFloatList
        restItemPropertyFloatMockMvc.perform(get("/api/item-property-floats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyFloat.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getItemPropertyFloat() throws Exception {
        // Initialize the database
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);

        // Get the itemPropertyFloat
        restItemPropertyFloatMockMvc.perform(get("/api/item-property-floats/{id}", itemPropertyFloat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPropertyFloat.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllItemPropertyFloatsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);

        // Get all the itemPropertyFloatList where value equals to DEFAULT_VALUE
        defaultItemPropertyFloatShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the itemPropertyFloatList where value equals to UPDATED_VALUE
        defaultItemPropertyFloatShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyFloatsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);

        // Get all the itemPropertyFloatList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultItemPropertyFloatShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the itemPropertyFloatList where value equals to UPDATED_VALUE
        defaultItemPropertyFloatShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyFloatsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);

        // Get all the itemPropertyFloatList where value is not null
        defaultItemPropertyFloatShouldBeFound("value.specified=true");

        // Get all the itemPropertyFloatList where value is null
        defaultItemPropertyFloatShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPropertyFloatsByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyFloat.setItem(item);
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);
        Long itemId = item.getId();

        // Get all the itemPropertyFloatList where item equals to itemId
        defaultItemPropertyFloatShouldBeFound("itemId.equals=" + itemId);

        // Get all the itemPropertyFloatList where item equals to itemId + 1
        defaultItemPropertyFloatShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPropertyFloatsByPropertyIsEqualToSomething() throws Exception {
        // Initialize the database
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyFloat.setProperty(property);
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);
        Long propertyId = property.getId();

        // Get all the itemPropertyFloatList where property equals to propertyId
        defaultItemPropertyFloatShouldBeFound("propertyId.equals=" + propertyId);

        // Get all the itemPropertyFloatList where property equals to propertyId + 1
        defaultItemPropertyFloatShouldNotBeFound("propertyId.equals=" + (propertyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemPropertyFloatShouldBeFound(String filter) throws Exception {
        restItemPropertyFloatMockMvc.perform(get("/api/item-property-floats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyFloat.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));

        // Check, that the count call also returns 1
        restItemPropertyFloatMockMvc.perform(get("/api/item-property-floats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemPropertyFloatShouldNotBeFound(String filter) throws Exception {
        restItemPropertyFloatMockMvc.perform(get("/api/item-property-floats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemPropertyFloatMockMvc.perform(get("/api/item-property-floats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemPropertyFloat() throws Exception {
        // Get the itemPropertyFloat
        restItemPropertyFloatMockMvc.perform(get("/api/item-property-floats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPropertyFloat() throws Exception {
        // Initialize the database
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);

        int databaseSizeBeforeUpdate = itemPropertyFloatRepository.findAll().size();

        // Update the itemPropertyFloat
        ItemPropertyFloat updatedItemPropertyFloat = itemPropertyFloatRepository.findById(itemPropertyFloat.getId()).get();
        // Disconnect from session so that the updates on updatedItemPropertyFloat are not directly saved in db
        em.detach(updatedItemPropertyFloat);
        updatedItemPropertyFloat
            .value(UPDATED_VALUE);
        ItemPropertyFloatDTO itemPropertyFloatDTO = itemPropertyFloatMapper.toDto(updatedItemPropertyFloat);

        restItemPropertyFloatMockMvc.perform(put("/api/item-property-floats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyFloatDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPropertyFloat in the database
        List<ItemPropertyFloat> itemPropertyFloatList = itemPropertyFloatRepository.findAll();
        assertThat(itemPropertyFloatList).hasSize(databaseSizeBeforeUpdate);
        ItemPropertyFloat testItemPropertyFloat = itemPropertyFloatList.get(itemPropertyFloatList.size() - 1);
        assertThat(testItemPropertyFloat.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPropertyFloat() throws Exception {
        int databaseSizeBeforeUpdate = itemPropertyFloatRepository.findAll().size();

        // Create the ItemPropertyFloat
        ItemPropertyFloatDTO itemPropertyFloatDTO = itemPropertyFloatMapper.toDto(itemPropertyFloat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPropertyFloatMockMvc.perform(put("/api/item-property-floats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyFloatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyFloat in the database
        List<ItemPropertyFloat> itemPropertyFloatList = itemPropertyFloatRepository.findAll();
        assertThat(itemPropertyFloatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemPropertyFloat() throws Exception {
        // Initialize the database
        itemPropertyFloatRepository.saveAndFlush(itemPropertyFloat);

        int databaseSizeBeforeDelete = itemPropertyFloatRepository.findAll().size();

        // Delete the itemPropertyFloat
        restItemPropertyFloatMockMvc.perform(delete("/api/item-property-floats/{id}", itemPropertyFloat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPropertyFloat> itemPropertyFloatList = itemPropertyFloatRepository.findAll();
        assertThat(itemPropertyFloatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyFloat.class);
        ItemPropertyFloat itemPropertyFloat1 = new ItemPropertyFloat();
        itemPropertyFloat1.setId(1L);
        ItemPropertyFloat itemPropertyFloat2 = new ItemPropertyFloat();
        itemPropertyFloat2.setId(itemPropertyFloat1.getId());
        assertThat(itemPropertyFloat1).isEqualTo(itemPropertyFloat2);
        itemPropertyFloat2.setId(2L);
        assertThat(itemPropertyFloat1).isNotEqualTo(itemPropertyFloat2);
        itemPropertyFloat1.setId(null);
        assertThat(itemPropertyFloat1).isNotEqualTo(itemPropertyFloat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyFloatDTO.class);
        ItemPropertyFloatDTO itemPropertyFloatDTO1 = new ItemPropertyFloatDTO();
        itemPropertyFloatDTO1.setId(1L);
        ItemPropertyFloatDTO itemPropertyFloatDTO2 = new ItemPropertyFloatDTO();
        assertThat(itemPropertyFloatDTO1).isNotEqualTo(itemPropertyFloatDTO2);
        itemPropertyFloatDTO2.setId(itemPropertyFloatDTO1.getId());
        assertThat(itemPropertyFloatDTO1).isEqualTo(itemPropertyFloatDTO2);
        itemPropertyFloatDTO2.setId(2L);
        assertThat(itemPropertyFloatDTO1).isNotEqualTo(itemPropertyFloatDTO2);
        itemPropertyFloatDTO1.setId(null);
        assertThat(itemPropertyFloatDTO1).isNotEqualTo(itemPropertyFloatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPropertyFloatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPropertyFloatMapper.fromId(null)).isNull();
    }
}
