package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;

import com.isliam.techshop.domain.ItemPropertyDouble;
import com.isliam.techshop.domain.Property;
import com.isliam.techshop.domain.Item;
import com.isliam.techshop.repository.ItemPropertyDoubleRepository;
import com.isliam.techshop.service.ItemPropertyDoubleService;
import com.isliam.techshop.service.dto.ItemPropertyDoubleDTO;
import com.isliam.techshop.service.mapper.ItemPropertyDoubleMapper;
import com.isliam.techshop.web.rest.errors.ExceptionTranslator;
import com.isliam.techshop.service.dto.ItemPropertyDoubleCriteria;
import com.isliam.techshop.service.ItemPropertyDoubleQueryService;

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
 * Test class for the ItemPropertyDoubleResource REST controller.
 *
 * @see ItemPropertyDoubleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class ItemPropertyDoubleResourceIntTest {

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    @Autowired
    private ItemPropertyDoubleRepository itemPropertyDoubleRepository;

    @Autowired
    private ItemPropertyDoubleMapper itemPropertyDoubleMapper;

    @Autowired
    private ItemPropertyDoubleService itemPropertyDoubleService;

    @Autowired
    private ItemPropertyDoubleQueryService itemPropertyDoubleQueryService;

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

    private MockMvc restItemPropertyDoubleMockMvc;

    private ItemPropertyDouble itemPropertyDouble;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPropertyDoubleResource itemPropertyDoubleResource = new ItemPropertyDoubleResource(itemPropertyDoubleService, itemPropertyDoubleQueryService);
        this.restItemPropertyDoubleMockMvc = MockMvcBuilders.standaloneSetup(itemPropertyDoubleResource)
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
    public static ItemPropertyDouble createEntity(EntityManager em) {
        ItemPropertyDouble itemPropertyDouble = new ItemPropertyDouble()
            .value(DEFAULT_VALUE);
        // Add required entity
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyDouble.setProperty(property);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyDouble.setItem(item);
        return itemPropertyDouble;
    }

    @Before
    public void initTest() {
        itemPropertyDouble = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPropertyDouble() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyDoubleRepository.findAll().size();

        // Create the ItemPropertyDouble
        ItemPropertyDoubleDTO itemPropertyDoubleDTO = itemPropertyDoubleMapper.toDto(itemPropertyDouble);
        restItemPropertyDoubleMockMvc.perform(post("/api/item-property-doubles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyDoubleDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPropertyDouble in the database
        List<ItemPropertyDouble> itemPropertyDoubleList = itemPropertyDoubleRepository.findAll();
        assertThat(itemPropertyDoubleList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPropertyDouble testItemPropertyDouble = itemPropertyDoubleList.get(itemPropertyDoubleList.size() - 1);
        assertThat(testItemPropertyDouble.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createItemPropertyDoubleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyDoubleRepository.findAll().size();

        // Create the ItemPropertyDouble with an existing ID
        itemPropertyDouble.setId(1L);
        ItemPropertyDoubleDTO itemPropertyDoubleDTO = itemPropertyDoubleMapper.toDto(itemPropertyDouble);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPropertyDoubleMockMvc.perform(post("/api/item-property-doubles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyDoubleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyDouble in the database
        List<ItemPropertyDouble> itemPropertyDoubleList = itemPropertyDoubleRepository.findAll();
        assertThat(itemPropertyDoubleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllItemPropertyDoubles() throws Exception {
        // Initialize the database
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);

        // Get all the itemPropertyDoubleList
        restItemPropertyDoubleMockMvc.perform(get("/api/item-property-doubles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyDouble.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getItemPropertyDouble() throws Exception {
        // Initialize the database
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);

        // Get the itemPropertyDouble
        restItemPropertyDoubleMockMvc.perform(get("/api/item-property-doubles/{id}", itemPropertyDouble.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPropertyDouble.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllItemPropertyDoublesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);

        // Get all the itemPropertyDoubleList where value equals to DEFAULT_VALUE
        defaultItemPropertyDoubleShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the itemPropertyDoubleList where value equals to UPDATED_VALUE
        defaultItemPropertyDoubleShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyDoublesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);

        // Get all the itemPropertyDoubleList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultItemPropertyDoubleShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the itemPropertyDoubleList where value equals to UPDATED_VALUE
        defaultItemPropertyDoubleShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyDoublesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);

        // Get all the itemPropertyDoubleList where value is not null
        defaultItemPropertyDoubleShouldBeFound("value.specified=true");

        // Get all the itemPropertyDoubleList where value is null
        defaultItemPropertyDoubleShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPropertyDoublesByPropertyIsEqualToSomething() throws Exception {
        // Initialize the database
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyDouble.setProperty(property);
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);
        Long propertyId = property.getId();

        // Get all the itemPropertyDoubleList where property equals to propertyId
        defaultItemPropertyDoubleShouldBeFound("propertyId.equals=" + propertyId);

        // Get all the itemPropertyDoubleList where property equals to propertyId + 1
        defaultItemPropertyDoubleShouldNotBeFound("propertyId.equals=" + (propertyId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPropertyDoublesByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyDouble.setItem(item);
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);
        Long itemId = item.getId();

        // Get all the itemPropertyDoubleList where item equals to itemId
        defaultItemPropertyDoubleShouldBeFound("itemId.equals=" + itemId);

        // Get all the itemPropertyDoubleList where item equals to itemId + 1
        defaultItemPropertyDoubleShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemPropertyDoubleShouldBeFound(String filter) throws Exception {
        restItemPropertyDoubleMockMvc.perform(get("/api/item-property-doubles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyDouble.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));

        // Check, that the count call also returns 1
        restItemPropertyDoubleMockMvc.perform(get("/api/item-property-doubles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemPropertyDoubleShouldNotBeFound(String filter) throws Exception {
        restItemPropertyDoubleMockMvc.perform(get("/api/item-property-doubles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemPropertyDoubleMockMvc.perform(get("/api/item-property-doubles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemPropertyDouble() throws Exception {
        // Get the itemPropertyDouble
        restItemPropertyDoubleMockMvc.perform(get("/api/item-property-doubles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPropertyDouble() throws Exception {
        // Initialize the database
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);

        int databaseSizeBeforeUpdate = itemPropertyDoubleRepository.findAll().size();

        // Update the itemPropertyDouble
        ItemPropertyDouble updatedItemPropertyDouble = itemPropertyDoubleRepository.findById(itemPropertyDouble.getId()).get();
        // Disconnect from session so that the updates on updatedItemPropertyDouble are not directly saved in db
        em.detach(updatedItemPropertyDouble);
        updatedItemPropertyDouble
            .value(UPDATED_VALUE);
        ItemPropertyDoubleDTO itemPropertyDoubleDTO = itemPropertyDoubleMapper.toDto(updatedItemPropertyDouble);

        restItemPropertyDoubleMockMvc.perform(put("/api/item-property-doubles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyDoubleDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPropertyDouble in the database
        List<ItemPropertyDouble> itemPropertyDoubleList = itemPropertyDoubleRepository.findAll();
        assertThat(itemPropertyDoubleList).hasSize(databaseSizeBeforeUpdate);
        ItemPropertyDouble testItemPropertyDouble = itemPropertyDoubleList.get(itemPropertyDoubleList.size() - 1);
        assertThat(testItemPropertyDouble.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPropertyDouble() throws Exception {
        int databaseSizeBeforeUpdate = itemPropertyDoubleRepository.findAll().size();

        // Create the ItemPropertyDouble
        ItemPropertyDoubleDTO itemPropertyDoubleDTO = itemPropertyDoubleMapper.toDto(itemPropertyDouble);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPropertyDoubleMockMvc.perform(put("/api/item-property-doubles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyDoubleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyDouble in the database
        List<ItemPropertyDouble> itemPropertyDoubleList = itemPropertyDoubleRepository.findAll();
        assertThat(itemPropertyDoubleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemPropertyDouble() throws Exception {
        // Initialize the database
        itemPropertyDoubleRepository.saveAndFlush(itemPropertyDouble);

        int databaseSizeBeforeDelete = itemPropertyDoubleRepository.findAll().size();

        // Delete the itemPropertyDouble
        restItemPropertyDoubleMockMvc.perform(delete("/api/item-property-doubles/{id}", itemPropertyDouble.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPropertyDouble> itemPropertyDoubleList = itemPropertyDoubleRepository.findAll();
        assertThat(itemPropertyDoubleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyDouble.class);
        ItemPropertyDouble itemPropertyDouble1 = new ItemPropertyDouble();
        itemPropertyDouble1.setId(1L);
        ItemPropertyDouble itemPropertyDouble2 = new ItemPropertyDouble();
        itemPropertyDouble2.setId(itemPropertyDouble1.getId());
        assertThat(itemPropertyDouble1).isEqualTo(itemPropertyDouble2);
        itemPropertyDouble2.setId(2L);
        assertThat(itemPropertyDouble1).isNotEqualTo(itemPropertyDouble2);
        itemPropertyDouble1.setId(null);
        assertThat(itemPropertyDouble1).isNotEqualTo(itemPropertyDouble2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyDoubleDTO.class);
        ItemPropertyDoubleDTO itemPropertyDoubleDTO1 = new ItemPropertyDoubleDTO();
        itemPropertyDoubleDTO1.setId(1L);
        ItemPropertyDoubleDTO itemPropertyDoubleDTO2 = new ItemPropertyDoubleDTO();
        assertThat(itemPropertyDoubleDTO1).isNotEqualTo(itemPropertyDoubleDTO2);
        itemPropertyDoubleDTO2.setId(itemPropertyDoubleDTO1.getId());
        assertThat(itemPropertyDoubleDTO1).isEqualTo(itemPropertyDoubleDTO2);
        itemPropertyDoubleDTO2.setId(2L);
        assertThat(itemPropertyDoubleDTO1).isNotEqualTo(itemPropertyDoubleDTO2);
        itemPropertyDoubleDTO1.setId(null);
        assertThat(itemPropertyDoubleDTO1).isNotEqualTo(itemPropertyDoubleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPropertyDoubleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPropertyDoubleMapper.fromId(null)).isNull();
    }
}
