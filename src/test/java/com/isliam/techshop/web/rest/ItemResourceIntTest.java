package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;

import com.isliam.techshop.domain.Item;
import com.isliam.techshop.domain.Product;
import com.isliam.techshop.domain.Manufacturer;
import com.isliam.techshop.domain.ItemPropertyBool;
import com.isliam.techshop.domain.ItemPropertyDouble;
import com.isliam.techshop.domain.ItemPropertyFloat;
import com.isliam.techshop.domain.ItemPropertyInt;
import com.isliam.techshop.domain.ItemPropertyString;
import com.isliam.techshop.repository.ItemRepository;
import com.isliam.techshop.service.ItemService;
import com.isliam.techshop.service.dto.ItemDTO;
import com.isliam.techshop.service.mapper.ItemMapper;
import com.isliam.techshop.web.rest.errors.ExceptionTranslator;
import com.isliam.techshop.service.dto.ItemCriteria;
import com.isliam.techshop.service.ItemQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.isliam.techshop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemResource REST controller.
 *
 * @see ItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class ItemResourceIntTest {

    private static final String DEFAULT_GTIN = "AAAAAAAAAAAAA";
    private static final String UPDATED_GTIN = "BBBBBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemQueryService itemQueryService;

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

    private MockMvc restItemMockMvc;

    private Item item;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemResource itemResource = new ItemResource(itemService, itemQueryService);
        this.restItemMockMvc = MockMvcBuilders.standaloneSetup(itemResource)
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
    public static Item createEntity(EntityManager em) {
        Item item = new Item()
            .gtin(DEFAULT_GTIN)
            .barcode(DEFAULT_BARCODE)
            .cost(DEFAULT_COST)
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        item.setProduct(product);
        return item;
    }

    @Before
    public void initTest() {
        item = createEntity(em);
    }

    @Test
    @Transactional
    public void createItem() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item
        ItemDTO itemDTO = itemMapper.toDto(item);
        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isCreated());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate + 1);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getGtin()).isEqualTo(DEFAULT_GTIN);
        assertThat(testItem.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testItem.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item with an existing ID
        item.setId(1L);
        ItemDTO itemDTO = itemMapper.toDto(item);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setCost(null);

        // Create the Item, which fails.
        ItemDTO itemDTO = itemMapper.toDto(item);

        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setName(null);

        // Create the Item, which fails.
        ItemDTO itemDTO = itemMapper.toDto(item);

        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setActive(null);

        // Create the Item, which fails.
        ItemDTO itemDTO = itemMapper.toDto(item);

        restItemMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList
        restItemMockMvc.perform(get("/api/items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId().intValue())))
            .andExpect(jsonPath("$.[*].gtin").value(hasItem(DEFAULT_GTIN.toString())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(item.getId().intValue()))
            .andExpect(jsonPath("$.gtin").value(DEFAULT_GTIN.toString()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllItemsByGtinIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where gtin equals to DEFAULT_GTIN
        defaultItemShouldBeFound("gtin.equals=" + DEFAULT_GTIN);

        // Get all the itemList where gtin equals to UPDATED_GTIN
        defaultItemShouldNotBeFound("gtin.equals=" + UPDATED_GTIN);
    }

    @Test
    @Transactional
    public void getAllItemsByGtinIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where gtin in DEFAULT_GTIN or UPDATED_GTIN
        defaultItemShouldBeFound("gtin.in=" + DEFAULT_GTIN + "," + UPDATED_GTIN);

        // Get all the itemList where gtin equals to UPDATED_GTIN
        defaultItemShouldNotBeFound("gtin.in=" + UPDATED_GTIN);
    }

    @Test
    @Transactional
    public void getAllItemsByGtinIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where gtin is not null
        defaultItemShouldBeFound("gtin.specified=true");

        // Get all the itemList where gtin is null
        defaultItemShouldNotBeFound("gtin.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where cost equals to DEFAULT_COST
        defaultItemShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the itemList where cost equals to UPDATED_COST
        defaultItemShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllItemsByCostIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where cost in DEFAULT_COST or UPDATED_COST
        defaultItemShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the itemList where cost equals to UPDATED_COST
        defaultItemShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllItemsByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where cost is not null
        defaultItemShouldBeFound("cost.specified=true");

        // Get all the itemList where cost is null
        defaultItemShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name equals to DEFAULT_NAME
        defaultItemShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the itemList where name equals to UPDATED_NAME
        defaultItemShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name in DEFAULT_NAME or UPDATED_NAME
        defaultItemShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the itemList where name equals to UPDATED_NAME
        defaultItemShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where name is not null
        defaultItemShouldBeFound("name.specified=true");

        // Get all the itemList where name is null
        defaultItemShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where active equals to DEFAULT_ACTIVE
        defaultItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the itemList where active equals to UPDATED_ACTIVE
        defaultItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the itemList where active equals to UPDATED_ACTIVE
        defaultItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList where active is not null
        defaultItemShouldBeFound("active.specified=true");

        // Get all the itemList where active is null
        defaultItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        item.setProduct(product);
        itemRepository.saveAndFlush(item);
        Long productId = product.getId();

        // Get all the itemList where product equals to productId
        defaultItemShouldBeFound("productId.equals=" + productId);

        // Get all the itemList where product equals to productId + 1
        defaultItemShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByManufacturerIsEqualToSomething() throws Exception {
        // Initialize the database
        Manufacturer manufacturer = ManufacturerResourceIntTest.createEntity(em);
        em.persist(manufacturer);
        em.flush();
        item.setManufacturer(manufacturer);
        itemRepository.saveAndFlush(item);
        Long manufacturerId = manufacturer.getId();

        // Get all the itemList where manufacturer equals to manufacturerId
        defaultItemShouldBeFound("manufacturerId.equals=" + manufacturerId);

        // Get all the itemList where manufacturer equals to manufacturerId + 1
        defaultItemShouldNotBeFound("manufacturerId.equals=" + (manufacturerId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByItemPropertyBoolIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemPropertyBool itemPropertyBool = ItemPropertyBoolResourceIntTest.createEntity(em);
        em.persist(itemPropertyBool);
        em.flush();
        item.addItemPropertyBool(itemPropertyBool);
        itemRepository.saveAndFlush(item);
        Long itemPropertyBoolId = itemPropertyBool.getId();

        // Get all the itemList where itemPropertyBool equals to itemPropertyBoolId
        defaultItemShouldBeFound("itemPropertyBoolId.equals=" + itemPropertyBoolId);

        // Get all the itemList where itemPropertyBool equals to itemPropertyBoolId + 1
        defaultItemShouldNotBeFound("itemPropertyBoolId.equals=" + (itemPropertyBoolId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByItemPropertyDoubleIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemPropertyDouble itemPropertyDouble = ItemPropertyDoubleResourceIntTest.createEntity(em);
        em.persist(itemPropertyDouble);
        em.flush();
        item.addItemPropertyDouble(itemPropertyDouble);
        itemRepository.saveAndFlush(item);
        Long itemPropertyDoubleId = itemPropertyDouble.getId();

        // Get all the itemList where itemPropertyDouble equals to itemPropertyDoubleId
        defaultItemShouldBeFound("itemPropertyDoubleId.equals=" + itemPropertyDoubleId);

        // Get all the itemList where itemPropertyDouble equals to itemPropertyDoubleId + 1
        defaultItemShouldNotBeFound("itemPropertyDoubleId.equals=" + (itemPropertyDoubleId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByItemPropertyFloatIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemPropertyFloat itemPropertyFloat = ItemPropertyFloatResourceIntTest.createEntity(em);
        em.persist(itemPropertyFloat);
        em.flush();
        item.addItemPropertyFloat(itemPropertyFloat);
        itemRepository.saveAndFlush(item);
        Long itemPropertyFloatId = itemPropertyFloat.getId();

        // Get all the itemList where itemPropertyFloat equals to itemPropertyFloatId
        defaultItemShouldBeFound("itemPropertyFloatId.equals=" + itemPropertyFloatId);

        // Get all the itemList where itemPropertyFloat equals to itemPropertyFloatId + 1
        defaultItemShouldNotBeFound("itemPropertyFloatId.equals=" + (itemPropertyFloatId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByItemPropertyIntIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemPropertyInt itemPropertyInt = ItemPropertyIntResourceIntTest.createEntity(em);
        em.persist(itemPropertyInt);
        em.flush();
        item.addItemPropertyInt(itemPropertyInt);
        itemRepository.saveAndFlush(item);
        Long itemPropertyIntId = itemPropertyInt.getId();

        // Get all the itemList where itemPropertyInt equals to itemPropertyIntId
        defaultItemShouldBeFound("itemPropertyIntId.equals=" + itemPropertyIntId);

        // Get all the itemList where itemPropertyInt equals to itemPropertyIntId + 1
        defaultItemShouldNotBeFound("itemPropertyIntId.equals=" + (itemPropertyIntId + 1));
    }


    @Test
    @Transactional
    public void getAllItemsByItemPropertyStringIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemPropertyString itemPropertyString = ItemPropertyStringResourceIntTest.createEntity(em);
        em.persist(itemPropertyString);
        em.flush();
        item.addItemPropertyString(itemPropertyString);
        itemRepository.saveAndFlush(item);
        Long itemPropertyStringId = itemPropertyString.getId();

        // Get all the itemList where itemPropertyString equals to itemPropertyStringId
        defaultItemShouldBeFound("itemPropertyStringId.equals=" + itemPropertyStringId);

        // Get all the itemList where itemPropertyString equals to itemPropertyStringId + 1
        defaultItemShouldNotBeFound("itemPropertyStringId.equals=" + (itemPropertyStringId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemShouldBeFound(String filter) throws Exception {
        restItemMockMvc.perform(get("/api/items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId().intValue())))
            .andExpect(jsonPath("$.[*].gtin").value(hasItem(DEFAULT_GTIN)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restItemMockMvc.perform(get("/api/items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemShouldNotBeFound(String filter) throws Exception {
        restItemMockMvc.perform(get("/api/items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemMockMvc.perform(get("/api/items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item
        Item updatedItem = itemRepository.findById(item.getId()).get();
        // Disconnect from session so that the updates on updatedItem are not directly saved in db
        em.detach(updatedItem);
        updatedItem
            .gtin(UPDATED_GTIN)
            .barcode(UPDATED_BARCODE)
            .cost(UPDATED_COST)
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE);
        ItemDTO itemDTO = itemMapper.toDto(updatedItem);

        restItemMockMvc.perform(put("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getGtin()).isEqualTo(UPDATED_GTIN);
        assertThat(testItem.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testItem.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Create the Item
        ItemDTO itemDTO = itemMapper.toDto(item);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemMockMvc.perform(put("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        int databaseSizeBeforeDelete = itemRepository.findAll().size();

        // Delete the item
        restItemMockMvc.perform(delete("/api/items/{id}", item.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Item.class);
        Item item1 = new Item();
        item1.setId(1L);
        Item item2 = new Item();
        item2.setId(item1.getId());
        assertThat(item1).isEqualTo(item2);
        item2.setId(2L);
        assertThat(item1).isNotEqualTo(item2);
        item1.setId(null);
        assertThat(item1).isNotEqualTo(item2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemDTO.class);
        ItemDTO itemDTO1 = new ItemDTO();
        itemDTO1.setId(1L);
        ItemDTO itemDTO2 = new ItemDTO();
        assertThat(itemDTO1).isNotEqualTo(itemDTO2);
        itemDTO2.setId(itemDTO1.getId());
        assertThat(itemDTO1).isEqualTo(itemDTO2);
        itemDTO2.setId(2L);
        assertThat(itemDTO1).isNotEqualTo(itemDTO2);
        itemDTO1.setId(null);
        assertThat(itemDTO1).isNotEqualTo(itemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemMapper.fromId(null)).isNull();
    }
}
