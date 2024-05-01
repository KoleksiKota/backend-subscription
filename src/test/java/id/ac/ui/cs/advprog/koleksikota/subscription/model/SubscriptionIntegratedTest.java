package id.ac.ui.cs.advprog.koleksikota.subscription.model;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionIntegratedTest{
    private SubscriptionIntegrated subscription;
    private final SubscriptionType subsType = SubscriptionType.SEMI_ANNUAL;
    private final String customerId = "6f4f91d2-af1a-4d0a-a60b-c6e0884cebca";
    private final String boxId = "788fab34-43af-44e8-9b1d-00076969a367";

    @BeforeEach
    public void setUp() {
        subscription = new SubscriptionIntegrated(subsType, customerId, boxId);
    }

    @Test
    public void testNewSubscription() {
        assertNotNull(subscription);
        assertNotNull(subscription.getSubscriptionId());
        assertNotNull(subscription.getCreatedDate());
        assertNotNull(subscription.getSubscriptionCode());
        assertEquals(ApprovalStatus.PENDING, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.PENDING, subscription.getSubscriptionStatus());
        assertEquals(subsType, subscription.getSubscriptionType());
        assertEquals(customerId, subscription.getCustomerId());
        assertEquals(boxId, subscription.getBoxId());
    }

    @Test
    public void testApproveStatus() {
        subscription.setApprovalStatus(ApprovalStatus.APPROVED);
        assertEquals(ApprovalStatus.APPROVED, subscription.getApprovalStatus());
    }

    @Test 
    public void testRejectStatus() {
        subscription.setApprovalStatus(ApprovalStatus.REJECTED);
        assertEquals(ApprovalStatus.REJECTED, subscription.getApprovalStatus());
    }

    @Test
    public void testSubscriptionBoxIsSubscribed() {
        subscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        assertEquals(SubscriptionStatus.SUBSCRIBED, subscription.getSubscriptionStatus());
    }

    @Test
    public void testSubscriptionCodeFormat() {
        String subscriptionCode = subscription.getSubscriptionCode();
        assertNotNull(subscriptionCode);
        String[] parts = subscriptionCode.split("-");
        assertEquals(2, parts.length);
        assertEquals(subsType.getValue(), parts[0]+"-");
    }
}