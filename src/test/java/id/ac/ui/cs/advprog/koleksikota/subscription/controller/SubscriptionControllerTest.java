package id.ac.ui.cs.advprog.koleksikota.subscription.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
import id.ac.ui.cs.advprog.koleksikota.subscription.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @Test
    public void testGetAllSubscriptions() throws Exception {
        Subscription subscription = new Subscription();
        List<Subscription> allSubscriptions = Collections.singletonList(subscription);

        when(subscriptionService.findAllSubscriptions()).thenReturn(allSubscriptions);

        MvcResult mvcResult = mockMvc.perform(get("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        mvcResult.getAsyncResult();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testFindSubscriptionById() throws Exception {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(UUID.randomUUID());
        subscription.setCustomerId("123");
        subscription.setBoxId("box1");
        subscription.setSubscriptionCode("MTH-123456");

        when(subscriptionService.findSubscriptionById(subscription.getSubscriptionId().toString())).thenReturn(subscription);

        MvcResult mvcResult = mockMvc.perform(get("/{subscriptionId}", subscription.getSubscriptionId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        mvcResult.getAsyncResult();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCreateSubscription() throws Exception {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(UUID.randomUUID());
        subscription.setCustomerId("123");
        subscription.setBoxId("box1");
        when(subscriptionService.createSubscription(any(), any(), any())).thenReturn(subscription);

        MvcResult mvcResult = mockMvc.perform(post("/subscription-box/{subscriptionId}/subscribe", subscription.getSubscriptionId())
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subsType\": \"MTH-\", \"customerId\": \"123\"}"))
                .andReturn();
        mvcResult.getAsyncResult();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateApprovalStatus() throws Exception {
        Subscription subscription = new Subscription();
        subscription.setBoxId("box1");
        subscription.setSubscriptionType(SubscriptionType.MONTHLY);
        subscription.setCustomerId("123");

        when(subscriptionService.changeApprovalStatus(anyString(), anyString())).thenReturn(subscription);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("status", "APPROVED");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/subscriptions/1f36d862-6e0b-47a3-aa17-e02ec74a0246/change-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andReturn();

        mvcResult.getAsyncResult(); // Wait for async result

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCancelSubscription() throws Exception {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(UUID.randomUUID());
        subscription.setCustomerId("123");
        subscription.setBoxId("box1");
        when(subscriptionService.cancelSubscription(anyString())).thenReturn(subscription);

        MvcResult mvcResult = mockMvc.perform(patch("/subscriptions/{subscriptionId}/unsubscribe", subscription.getSubscriptionId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        mvcResult.getAsyncResult();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
