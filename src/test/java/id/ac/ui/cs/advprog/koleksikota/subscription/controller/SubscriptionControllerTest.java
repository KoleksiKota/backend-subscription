package id.ac.ui.cs.advprog.koleksikota.subscription.controller;

import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
import id.ac.ui.cs.advprog.koleksikota.subscription.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        when(subscriptionService.changeApprovalStatus(anyString(), anyString())).thenReturn(subscription);

        mockMvc.perform(patch("/subscriptions/1/change-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"APPROVED\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.subscriptionId").value(subscription.getSubscriptionId().toString()));

        verify(subscriptionService, times(1)).changeApprovalStatus(anyString(), anyString());
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
