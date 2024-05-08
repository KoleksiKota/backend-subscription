package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionStatus;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
import id.ac.ui.cs.advprog.koleksikota.subscription.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubscription() {
        SubscriptionIntegrated subscription = new SubscriptionIntegrated();
        SubscriptionType subsType = SubscriptionType.MONTHLY;
        subscription.setSubscriptionType(subsType);
        String customerId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        subscription.setCustomerId(customerId);
        String boxId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        subscription.setBoxId(boxId);

        when(subscriptionRepository.save(any(SubscriptionIntegrated.class))).thenReturn(subscription);

        SubscriptionIntegrated savedSubscription = subscriptionService.createSubscription(subsType, customerId, boxId);

        assertEquals(customerId, savedSubscription.getCustomerId());
        assertEquals(boxId, savedSubscription.getBoxId());
        verify(subscriptionRepository, times(1)).save(any(SubscriptionIntegrated.class));
    }

    @Test
    void testCancelSubscription() {
        SubscriptionType subsType = SubscriptionType.MONTHLY;
        String customerId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String boxId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        SubscriptionIntegrated subscription = new SubscriptionIntegrated(subsType, customerId, boxId);

        when(subscriptionRepository.findById(subscription.getSubscriptionId())).thenReturn(Optional.of(subscription));

        subscriptionService.cancelSubscription(String.valueOf(subscription.getSubscriptionId()));

        assertEquals(SubscriptionStatus.CANCELLED, subscription.getSubscriptionStatus());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void testFindSubscriptionById() {
        UUID subscriptionId = UUID.randomUUID();
        SubscriptionIntegrated subscription = new SubscriptionIntegrated();
        subscription.setSubscriptionId(subscriptionId);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        SubscriptionIntegrated foundSubscription = subscriptionService.findSubscriptionById(String.valueOf(subscriptionId));

        assertNotNull(foundSubscription);
        assertEquals(subscriptionId, foundSubscription.getSubscriptionId());
    }

    @Test
    void testFindAllSubscriptions() {
        SubscriptionIntegrated subscription1 = new SubscriptionIntegrated();
        SubscriptionIntegrated subscription2 = new SubscriptionIntegrated();
        List<SubscriptionIntegrated> subscriptions = Arrays.asList(subscription1, subscription2);

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        List<SubscriptionIntegrated> foundSubscriptions = subscriptionService.findAllSubscriptions();

        assertEquals(subscriptions.size(), foundSubscriptions.size());
        assertTrue(foundSubscriptions.containsAll(subscriptions));
    }
}
