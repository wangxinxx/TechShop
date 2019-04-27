package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;

import com.isliam.techshop.domain.ItemPropertyString;
import com.isliam.techshop.domain.Item;
import com.isliam.techshop.domain.Property;
import com.isliam.techshop.repository.ItemPropertyStringRepository;
import com.isliam.techshop.service.ItemPropertyStringService;
import com.isliam.techshop.service.dto.ItemPropertyStringDTO;
import com.isliam.techshop.service.mapper.ItemPropertyStringMapper;
import com.isliam.techshop.web.rest.errors.ExceptionTranslator;
import com.isliam.techshop.service.dto.ItemPropertyStringCriteria;
import com.isliam.techshop.service.ItemPropertyStringQueryService;

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
 * Test class for the ItemPropertyStringResource REST controller.
 *
 * @see ItemPropertyStringResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class ItemPropertyStringResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ItemPropertyStringRepository itemPropertyStringRepository;

    @Autowired
    private ItemPropertyStringMapper itemPropertyStringMapper;

    @Autowired
    private ItemPropertyStringService itemPropertyStringService;

    @Autowired
    private ItemPropertyStringQueryService itemPropertyStringQueryService;

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

    private MockMvc restItemPropertyStringMockMvc;

    private ItemPropertyString itemPropertyString;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPropertyStringResource itemPropertyStringResource = new ItemPropertyStringResource(itemPropertyStringService, itemPropertyStringQueryService);
        this.restItemPropertyStringMockMvc = MockMvcBuilders.standaloneSetup(itemPropertyStringResource)
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
    public static ItemPropertyString createEntity(EntityManager em) {
        ItemPropertyString itemPropertyString = new ItemPropertyString()
            .value(DEFAULT_VALUE);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyString.setItem(item);
        // Add required entity
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyString.setProperty(property);
        return itemPropertyString;
    }

    @Before
    public void initTest() {
        itemPropertyString = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPropertyString() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyStringRepository.findAll().size();

        // Create the ItemPropertyString
        ItemPropertyStringDTO itemPropertyStringDTO = itemPropertyStringMapper.toDto(itemPropertyString);
        restItemPropertyStringMockMvc.perform(post("/api/item-property-strings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyStringDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPropertyString in the database
        List<ItemPropertyString> itemPropertyStringList = itemPropertyStringRepository.findAll();
        assertThat(itemPropertyStringList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPropertyString testItemPropertyString = itemPropertyStringList.get(itemPropertyStringList.size() - 1);
        assertThat(testItemPropertyString.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createItemPropertyStringWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPropertyStringRepository.findAll().size();

        // Create the ItemPropertyString with an existing ID
        itemPropertyString.setId(1L);
        ItemPropertyStringDTO itemPropertyStringDTO = itemPropertyStringMapper.toDto(itemPropertyString);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPropertyStringMockMvc.perform(post("/api/item-property-strings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyStringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyString in the database
        List<ItemPropertyString> itemPropertyStringList = itemPropertyStringRepository.findAll();
        assertThat(itemPropertyStringList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllItemPropertyStrings() throws Exception {
        // Initialize the database
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);

        // Get all the itemPropertyStringList
        restItemPropertyStringMockMvc.perform(get("/api/item-property-strings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyString.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getItemPropertyString() throws Exception {
        // Initialize the database
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);

        // Get the itemPropertyString
        restItemPropertyStringMockMvc.perform(get("/api/item-property-strings/{id}", itemPropertyString.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPropertyString.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllItemPropertyStringsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);

        // Get all the itemPropertyStringList where value equals to DEFAULT_VALUE
        defaultItemPropertyStringShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the itemPropertyStringList where value equals to UPDATED_VALUE
        defaultItemPropertyStringShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyStringsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);

        // Get all the itemPropertyStringList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultItemPropertyStringShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the itemPropertyStringList where value equals to UPDATED_VALUE
        defaultItemPropertyStringShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllItemPropertyStringsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);

        // Get all the itemPropertyStringList where value is not null
        defaultItemPropertyStringShouldBeFound("value.specified=true");

        // Get all the itemPropertyStringList where value is null
        defaultItemPropertyStringShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPropertyStringsByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemPropertyString.setItem(item);
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);
        Long itemId = item.getId();

        // Get all the itemPropertyStringList where item equals to itemId
        defaultItemPropertyStringShouldBeFound("itemId.equals=" + itemId);

        // Get all the itemPropertyStringList where item equals to itemId + 1
        defaultItemPropertyStringShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPropertyStringsByPropertyIsEqualToSomething() throws Exception {
        // Initialize the database
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        itemPropertyString.setProperty(property);
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);
        Long propertyId = property.getId();

        // Get all the itemPropertyStringList where property equals to propertyId
        defaultItemPropertyStringShouldBeFound("propertyId.equals=" + propertyId);

        // Get all the itemPropertyStringList where property equals to propertyId + 1
        defaultItemPropertyStringShouldNotBeFound("propertyId.equals=" + (propertyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemPropertyStringShouldBeFound(String filter) throws Exception {
        restItemPropertyStringMockMvc.perform(get("/api/item-property-strings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPropertyString.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restItemPropertyStringMockMvc.perform(get("/api/item-property-strings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemPropertyStringShouldNotBeFound(String filter) throws Exception {
        restItemPropertyStringMockMvc.perform(get("/api/item-property-strings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemPropertyStringMockMvc.perform(get("/api/item-property-strings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemPropertyString() throws Exception {
        // Get the itemPropertyString
        restItemPropertyStringMockMvc.perform(get("/api/item-property-strings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPropertyString() throws Exception {
        // Initialize the database
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);

        int databaseSizeBeforeUpdate = itemPropertyStringRepository.findAll().size();

        // Update the itemPropertyString
        ItemPropertyString updatedItemPropertyString = itemPropertyStringRepository.findById(itemPropertyString.getId()).get();
        // Disconnect from session so that the updates on updatedItemPropertyString are not directly saved in db
        em.detach(updatedItemPropertyString);
        updatedItemPropertyString
            .value(UPDATED_VALUE);
        ItemPropertyStringDTO itemPropertyStringDTO = itemPropertyStringMapper.toDto(updatedItemPropertyString);

        restItemPropertyStringMockMvc.perform(put("/api/item-property-strings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyStringDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPropertyString in the database
        List<ItemPropertyString> itemPropertyStringList = itemPropertyStringRepository.findAll();
        assertThat(itemPropertyStringList).hasSize(databaseSizeBeforeUpdate);
        ItemPropertyString testItemPropertyString = itemPropertyStringList.get(itemPropertyStringList.size() - 1);
        assertThat(testItemPropertyString.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPropertyString() throws Exception {
        int databaseSizeBeforeUpdate = itemPropertyStringRepository.findAll().size();

        // Create the ItemPropertyString
        ItemPropertyStringDTO itemPropertyStringDTO = itemPropertyStringMapper.toDto(itemPropertyString);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPropertyStringMockMvc.perform(put("/api/item-property-strings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPropertyStringDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPropertyString in the database
        List<ItemPropertyString> itemPropertyStringList = itemPropertyStringRepository.findAll();
        assertThat(itemPropertyStringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemPropertyString() throws Exception {
        // Initialize the database
        itemPropertyStringRepository.saveAndFlush(itemPropertyString);

        int databaseSizeBeforeDelete = itemPropertyStringRepository.findAll().size();

        // Delete the itemPropertyString
        restItemPropertyStringMockMvc.perform(delete("/api/item-property-strings/{id}", itemPropertyString.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPropertyString> itemPropertyStringList = itemPropertyStringRepository.findAll();
        assertThat(itemPropertyStringList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyString.class);
        ItemPropertyString itemPropertyString1 = new ItemPropertyString();
        itemPropertyString1.setId(1L);
        ItemPropertyString itemPropertyString2 = new ItemPropertyString();
        itemPropertyString2.setId(itemPropertyString1.getId());
        assertThat(itemPropertyString1).isEqualTo(itemPropertyString2);
        itemPropertyString2.setId(2L);
        assertThat(itemPropertyString1).isNotEqualTo(itemPropertyString2);
        itemPropertyString1.setId(null);
        assertThat(itemPropertyString1).isNotEqualTo(itemPropertyString2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPropertyStringDTO.class);
        ItemPropertyStringDTO itemPropertyStringDTO1 = new ItemPropertyStringDTO();
        itemPropertyStringDTO1.setId(1L);
        ItemPropertyStringDTO itemPropertyStringDTO2 = new ItemPropertyStringDTO();
        assertThat(itemPropertyStringDTO1).isNotEqualTo(itemPropertyStringDTO2);
        itemPropertyStringDTO2.setId(itemPropertyStringDTO1.getId());
        assertThat(itemPropertyStringDTO1).isEqualTo(itemPropertyStringDTO2);
        itemPropertyStringDTO2.setId(2L);
        assertThat(itemPropertyStringDTO1).isNotEqualTo(itemPropertyStringDTO2);
        itemPropertyStringDTO1.setId(null);
        assertThat(itemPropertyStringDTO1).isNotEqualTo(itemPropertyStringDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPropertyStringMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPropertyStringMapper.fromId(null)).isNull();
    }
}
