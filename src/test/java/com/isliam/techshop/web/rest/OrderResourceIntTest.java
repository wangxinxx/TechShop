package com.isliam.techshop.web.rest;

import com.isliam.techshop.TechShopApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the OrderControllerResource REST controller.
 *
 * @see OrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechShopApp.class)
public class OrderResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        OrderResource orderResource = new OrderResource(orderService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(orderResource)
            .build();
    }

    /**
     * Test makeAnOrder
     */
    @Test
    public void testMakeAnOrder() throws Exception {
        restMockMvc.perform(post("/api/order-controller/make-an-order"))
            .andExpect(status().isOk());
    }

    /**
     * Test approveOrder
     */
    @Test
    public void testApproveAnOrder() throws Exception {
        restMockMvc.perform(post("/api/order-controller/approve-an-order"))
            .andExpect(status().isOk());
    }

    /**
     * Test rejectAnOrder
     */
    @Test
    public void testRejectAnOrder() throws Exception {
        restMockMvc.perform(post("/api/order-controller/reject-an-order"))
            .andExpect(status().isOk());
    }

    /**
     * Test items
     */
    @Test
    public void testItems() throws Exception {
        restMockMvc.perform(get("/api/order-controller/items"))
            .andExpect(status().isOk());
    }
}
