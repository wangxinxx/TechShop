package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;

import com.isliam.techshop.domain.ItemPropertyBool;
import com.isliam.techshop.domain.Item;
import com.isliam.techshop.domain.Property;
import com.isliam.techshop.repository.ItemPropertyBoolRepository;
import com.isliam.techshop.service.ItemPropertyBoolService;
import com.isliam.techshop.service.dto.ItemPropertyBoolDTO;
import com.isliam.techshop.service.mapper.ItemPropertyBoolMapper;
import com.isliam.techshop.web.rest.errors.ExceptionTranslator;
import com.isliam.techshop.service.dto.ItemPropertyBoolCriteria;
import com.isliam.techshop.service.ItemPropertyBoolQueryService;

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
 * Test class for the ItemPropertyBoolResource REST controller.
 *
 * @see ItemPropertyBoolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class ItemPropertyBoolResourceIntTest {

    private static final Boolean DEFAULT_VALUE = false;
    private static final Boolean UPDATED_VALUE = true;

    @Autowired
    private ItemPropertyBoolRepository itemPropertyBoolRepository;

    @Autowired
    private ItemPropertyBoolMapper itemPropertyBoolMapper;

    @Autowired
    private ItemPropertyBoolService itemPropertyBoolService;

    @Autowired
    private ItemPropertyBoolQueryService itemPropertyBoolQueryService;

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

    private MockMvc restItemPropertyBoolMockMvc;

    private ItemPropertyBool itemPropertyBool;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPropertyBoolResource itemPropertyBoolResource = new ItemPropertyBoolResource(itemPropertyBoolService, itemPropertyBoolQueryService);
        this.restItemPropertyBoolMockMvc = MockMvcBuilders.standaloneSetup(itemPropertyBoolResource)
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
    public static ItemPropertyBool createEntity(EntityManager em) {
        ItemPropertyBool itemPropertyBool = new ItemPropertyBool()
            .value(DEFAULT_VALUE);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyBool.setItem(item);
        // Add required entity
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyBool.setProperty(property);
        return itemPropertyBool;
    }

    @Before
    public void initTest() {
        itemPropertyBool = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPropertyBool() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyBoolRepository.findAll().size();

        // Create the ItemPropertyBool
        ItemPropertyBoolDTO itemPropertyBoolDTO = itemPropertyBoolMapper.toDto(itemPropertyBool);
        restItemPropertyBoolMockMvc.perform(post("/api/item-property-bools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyBoolDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPropertyBool in the database
        List<ItemPropertyBool> itemPropertyBoolList = itemPropertyBoolRepository.findAll();
        assertThat(itemPropertyBoolList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPropertyBool testItemPropertyBool = itemPropertyBoolList.get(itemPropertyBoolList.size() - 1);
        assertThat(testItemPropertyBool.isValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createItemPropertyBoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyBoolRepository.findAll().size();

        // Create the ItemPropertyBool with an existing ID
        itemPropertyBool.setId(1L);
        ItemPropertyBoolDTO itemPropertyBoolDTO = itemPropertyBoolMapper.toDto(itemPropertyBool);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPropertyBoolMockMvc.perform(post("/api/item-property-bools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyBoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyBool in the database
        List<ItemPropertyBool> itemPropertyBoolList = itemPropertyBoolRepository.findAll();
        assertThat(itemPropertyBoolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllItemPropertyBools() throws Exception {
        // Initialize the database
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);

        // Get all the itemPropertyBoolList
        restItemPropertyBoolMockMvc.perform(get("/api/item-property-bools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyBool.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getItemPropertyBool() throws Exception {
        // Initialize the database
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);

        // Get the itemPropertyBool
        restItemPropertyBoolMockMvc.perform(get("/api/item-property-bools/{id}", itemPropertyBool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPropertyBool.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllItemPropertyBoolsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);

        // Get all the itemPropertyBoolList where value equals to DEFAULT_VALUE
        defaultItemPropertyBoolShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the itemPropertyBoolList where value equals to UPDATED_VALUE
        defaultItemPropertyBoolShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyBoolsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);

        // Get all the itemPropertyBoolList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultItemPropertyBoolShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the itemPropertyBoolList where value equals to UPDATED_VALUE
        defaultItemPropertyBoolShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyBoolsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);

        // Get all the itemPropertyBoolList where value is not null
        defaultItemPropertyBoolShouldBeFound("value.specified=true");

        // Get all the itemPropertyBoolList where value is null
        defaultItemPropertyBoolShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPropertyBoolsByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyBool.setItem(item);
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);
        Long itemId = item.getId();

        // Get all the itemPropertyBoolList where item equals to itemId
        defaultItemPropertyBoolShouldBeFound("itemId.equals=" + itemId);

        // Get all the itemPropertyBoolList where item equals to itemId + 1
        defaultItemPropertyBoolShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPropertyBoolsByPropertyIsEqualToSomething() throws Exception {
        // Initialize the database
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyBool.setProperty(property);
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);
        Long propertyId = property.getId();

        // Get all the itemPropertyBoolList where property equals to propertyId
        defaultItemPropertyBoolShouldBeFound("propertyId.equals=" + propertyId);

        // Get all the itemPropertyBoolList where property equals to propertyId + 1
        defaultItemPropertyBoolShouldNotBeFound("propertyId.equals=" + (propertyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemPropertyBoolShouldBeFound(String filter) throws Exception {
        restItemPropertyBoolMockMvc.perform(get("/api/item-property-bools?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyBool.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.booleanValue())));

        // Check, that the count call also returns 1
        restItemPropertyBoolMockMvc.perform(get("/api/item-property-bools/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemPropertyBoolShouldNotBeFound(String filter) throws Exception {
        restItemPropertyBoolMockMvc.perform(get("/api/item-property-bools?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemPropertyBoolMockMvc.perform(get("/api/item-property-bools/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemPropertyBool() throws Exception {
        // Get the itemPropertyBool
        restItemPropertyBoolMockMvc.perform(get("/api/item-property-bools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPropertyBool() throws Exception {
        // Initialize the database
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);

        int databaseSizeBeforeUpdate = itemPropertyBoolRepository.findAll().size();

        // Update the itemPropertyBool
        ItemPropertyBool updatedItemPropertyBool = itemPropertyBoolRepository.findById(itemPropertyBool.getId()).get();
        // Disconnect from session so that the updates on updatedItemPropertyBool are not directly saved in db
        em.detach(updatedItemPropertyBool);
        updatedItemPropertyBool
            .value(UPDATED_VALUE);
        ItemPropertyBoolDTO itemPropertyBoolDTO = itemPropertyBoolMapper.toDto(updatedItemPropertyBool);

        restItemPropertyBoolMockMvc.perform(put("/api/item-property-bools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyBoolDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPropertyBool in the database
        List<ItemPropertyBool> itemPropertyBoolList = itemPropertyBoolRepository.findAll();
        assertThat(itemPropertyBoolList).hasSize(databaseSizeBeforeUpdate);
        ItemPropertyBool testItemPropertyBool = itemPropertyBoolList.get(itemPropertyBoolList.size() - 1);
        assertThat(testItemPropertyBool.isValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPropertyBool() throws Exception {
        int databaseSizeBeforeUpdate = itemPropertyBoolRepository.findAll().size();

        // Create the ItemPropertyBool
        ItemPropertyBoolDTO itemPropertyBoolDTO = itemPropertyBoolMapper.toDto(itemPropertyBool);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPropertyBoolMockMvc.perform(put("/api/item-property-bools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyBoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyBool in the database
        List<ItemPropertyBool> itemPropertyBoolList = itemPropertyBoolRepository.findAll();
        assertThat(itemPropertyBoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemPropertyBool() throws Exception {
        // Initialize the database
        itemPropertyBoolRepository.saveAndFlush(itemPropertyBool);

        int databaseSizeBeforeDelete = itemPropertyBoolRepository.findAll().size();

        // Delete the itemPropertyBool
        restItemPropertyBoolMockMvc.perform(delete("/api/item-property-bools/{id}", itemPropertyBool.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPropertyBool> itemPropertyBoolList = itemPropertyBoolRepository.findAll();
        assertThat(itemPropertyBoolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyBool.class);
        ItemPropertyBool itemPropertyBool1 = new ItemPropertyBool();
        itemPropertyBool1.setId(1L);
        ItemPropertyBool itemPropertyBool2 = new ItemPropertyBool();
        itemPropertyBool2.setId(itemPropertyBool1.getId());
        assertThat(itemPropertyBool1).isEqualTo(itemPropertyBool2);
        itemPropertyBool2.setId(2L);
        assertThat(itemPropertyBool1).isNotEqualTo(itemPropertyBool2);
        itemPropertyBool1.setId(null);
        assertThat(itemPropertyBool1).isNotEqualTo(itemPropertyBool2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyBoolDTO.class);
        ItemPropertyBoolDTO itemPropertyBoolDTO1 = new ItemPropertyBoolDTO();
        itemPropertyBoolDTO1.setId(1L);
        ItemPropertyBoolDTO itemPropertyBoolDTO2 = new ItemPropertyBoolDTO();
        assertThat(itemPropertyBoolDTO1).isNotEqualTo(itemPropertyBoolDTO2);
        itemPropertyBoolDTO2.setId(itemPropertyBoolDTO1.getId());
        assertThat(itemPropertyBoolDTO1).isEqualTo(itemPropertyBoolDTO2);
        itemPropertyBoolDTO2.setId(2L);
        assertThat(itemPropertyBoolDTO1).isNotEqualTo(itemPropertyBoolDTO2);
        itemPropertyBoolDTO1.setId(null);
        assertThat(itemPropertyBoolDTO1).isNotEqualTo(itemPropertyBoolDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPropertyBoolMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPropertyBoolMapper.fromId(null)).isNull();
    }
}
