package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.ApprovalStatus;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionStatus;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
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
        Subscription subscription = new Subscription();
        SubscriptionType subsType = SubscriptionType.MONTHLY;
        subscription.setSubscriptionType(subsType);
        String customerId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        subscription.setCustomerId(customerId);
        String boxId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        subscription.setBoxId(boxId);

        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        Subscription savedSubscription = subscriptionService.createSubscription(subsType, customerId, boxId);

        assertEquals(customerId, savedSubscription.getCustomerId());
        assertEquals(boxId, savedSubscription.getBoxId());
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    void testCancelSubscription() {
        SubscriptionType subsType = SubscriptionType.MONTHLY;
        String customerId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String boxId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        Subscription subscription = new Subscription(subsType, customerId, boxId);

        when(subscriptionRepository.findById(subscription.getSubscriptionId())).thenReturn(Optional.of(subscription));

        subscriptionService.cancelSubscription(String.valueOf(subscription.getSubscriptionId()));

        assertEquals(SubscriptionStatus.CANCELLED, subscription.getSubscriptionStatus());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void testFindSubscriptionById() {
        UUID subscriptionId = UUID.randomUUID();
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(subscriptionId);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        Subscription foundSubscription = subscriptionService.findSubscriptionById(String.valueOf(subscriptionId));

        assertNotNull(foundSubscription);
        assertEquals(subscriptionId, foundSubscription.getSubscriptionId());
    }

    @Test
    void testFindAllSubscriptions() {
        Subscription subscription1 = new Subscription();
        Subscription subscription2 = new Subscription();
        List<Subscription> subscriptions = Arrays.asList(subscription1, subscription2);

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        List<Subscription> foundSubscriptions = subscriptionService.findAllSubscriptions();

        assertEquals(subscriptions.size(), foundSubscriptions.size());
        assertTrue(foundSubscriptions.containsAll(subscriptions));
    }

    @Test
    public void testChangeApprovalStatusToApproved() {
        SubscriptionType subsType = SubscriptionType.MONTHLY;
        String customerId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String boxId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        Subscription subscription = new Subscription(subsType, customerId, boxId);

        UUID subsId = subscription.getSubscriptionId();
        when(subscriptionRepository.findById(subsId)).thenReturn(Optional.of(subscription));

        Subscription updatedSubscription = subscriptionService.changeApprovalStatus(subsId.toString(), ApprovalStatus.APPROVED.getValue());

        verify(subscriptionRepository, times(1)).findById(subsId);
        verify(subscriptionRepository, times(1)).save(subscription);
        assertEquals(ApprovalStatus.APPROVED, updatedSubscription.getApprovalStatus());
    }

    @Test
    public void testChangeApprovalStatusToRejected() {
        SubscriptionType subsType = SubscriptionType.MONTHLY;
        String customerId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String boxId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        Subscription subscription = new Subscription(subsType, customerId, boxId);
        UUID subsId = subscription.getSubscriptionId();

        when(subscriptionRepository.findById(subsId)).thenReturn(Optional.of(subscription));

        Subscription updatedSubscription = subscriptionService.changeApprovalStatus(subsId.toString(), ApprovalStatus.REJECTED.getValue());

        verify(subscriptionRepository, times(1)).findById(subsId);
        verify(subscriptionRepository, times(1)).save(subscription);
        assertEquals(ApprovalStatus.REJECTED, updatedSubscription.getApprovalStatus());
    }

    @Test
    public void testChangeApprovalStatusWithInvalidStatus() {
        SubscriptionType subsType = SubscriptionType.MONTHLY;
        String customerId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String boxId = "a0f9de46-90b1-437d-a0bf-d0821dde9096";

        Subscription subscription = new Subscription(subsType, customerId, boxId);
        UUID subsId = subscription.getSubscriptionId();
        when(subscriptionRepository.findById(subsId)).thenReturn(Optional.of(subscription));

        try {
            subscriptionService.changeApprovalStatus(subsId.toString(), "DECLINED");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid approval status", e.getMessage());
        }
    }
}

