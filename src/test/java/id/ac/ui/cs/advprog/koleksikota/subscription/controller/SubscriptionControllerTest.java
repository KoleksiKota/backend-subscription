package id.ac.ui.cs.advprog.koleksikota.subscription.controller;

import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
import id.ac.ui.cs.advprog.koleksikota.subscription.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
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
        SubscriptionIntegrated subscription = new SubscriptionIntegrated();
        List<SubscriptionIntegrated> allSubscriptions = Collections.singletonList(subscription);

        when(subscriptionService.findAllSubscriptions()).thenReturn(allSubscriptions);

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").isNotEmpty());

        verify(subscriptionService, times(1)).findAllSubscriptions();
    }

    @Test
    public void testCreateSubscription() throws Exception {
        SubscriptionIntegrated subscription = new SubscriptionIntegrated();
        when(subscriptionService.createSubscription(any(), any(), any())).thenReturn(subscription);

        mockMvc.perform(post("/subscription-box/1/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subsType\": \"MTH-\", \"customerId\": \"123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.boxId").value(subscription.getBoxId()));

        verify(subscriptionService, times(1)).createSubscription(any(), any(), any());
    }

    @Test
    public void testUpdateApprovalStatus() throws Exception {
        SubscriptionIntegrated subscription = new SubscriptionIntegrated();
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
        SubscriptionIntegrated subscription = new SubscriptionIntegrated();
        when(subscriptionService.cancelSubscription(anyString())).thenReturn(subscription);

        mockMvc.perform(patch("/subscriptions/1/unsubscribe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.subscriptionId").value(subscription.getSubscriptionId().toString()));

        verify(subscriptionService, times(1)).cancelSubscription(anyString());
    }
}
