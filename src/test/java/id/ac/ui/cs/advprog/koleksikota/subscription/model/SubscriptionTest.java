package id.ac.ui.cs.advprog.koleksikota.subscription.model;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.state.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionTest {
    private Subscription subscription;
    private final SubscriptionType subsType = SubscriptionType.SEMI_ANNUAL;
    private final String customerId = "6f4f91d2-af1a-4d0a-a60b-c6e0884cebca";
    private final String boxId = "788fab34-43af-44e8-9b1d-00076969a367";

    @BeforeEach
    public void setUp() {
        subscription = new Subscription(subsType, customerId, boxId);
    }

    @Test
    public void testNewSubscription() {
        assertNotNull(subscription);
        assertNotNull(subscription.getSubscriptionId());
        assertNotNull(subscription.getCreatedDate());
        assertNotNull(subscription.getSubscriptionCode());
        assertEquals(ApprovalStatus.PENDING, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.PENDING, subscription.getSubscriptionStatus());
        assertEquals(ApprovalStatus.PENDING.toString(), subscription.getSavedState());
        assertEquals(subsType, subscription.getSubscriptionType());
        assertEquals(customerId, subscription.getCustomerId());
        assertEquals(boxId, subscription.getBoxId());
        assertNull(subscription.getStartDate());
        assertNull(subscription.getEndDate());
    }

    @Test
    public void testApproveStatus() {
        subscription.approve();
        assertInstanceOf(ApprovedState.class, subscription.getState());
        assertEquals(ApprovalStatus.APPROVED, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.SUBSCRIBED, subscription.getSubscriptionStatus());
        assertEquals(ApprovalStatus.APPROVED.toString(), subscription.getSavedState());
        assertNotNull(subscription.getStartDate());
        assertNull(subscription.getEndDate());
    }

    @Test //tidak ada perubahan pada rejected state
    public void testApproveStatusAfterRejected() {
        subscription.reject();
        subscription.approve();
        assertInstanceOf(RejectedState.class, subscription.getState());
        assertEquals(ApprovalStatus.REJECTED, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.CANCELLED, subscription.getSubscriptionStatus());
        assertEquals(ApprovalStatus.REJECTED.toString(), subscription.getSavedState());
        assertNull(subscription.getStartDate());
        assertNull(subscription.getEndDate());
    }

    @Test 
    public void testRejectStatus() {
        subscription.reject();
        assertInstanceOf(RejectedState.class, subscription.getState());
        assertEquals(ApprovalStatus.REJECTED, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.CANCELLED, subscription.getSubscriptionStatus());
        assertEquals(ApprovalStatus.REJECTED.toString(), subscription.getSavedState());
        assertNull(subscription.getStartDate());
        assertNull(subscription.getEndDate());
    }

    @Test //tidak ada perubahan pada Approved state
    public void testRejectStatusAfterApproved() {
        subscription.approve();
        subscription.reject();
        assertInstanceOf(ApprovedState.class, subscription.getState());
        assertEquals(SubscriptionStatus.SUBSCRIBED, subscription.getSubscriptionStatus());
        assertEquals(ApprovalStatus.APPROVED, subscription.getApprovalStatus());
        assertEquals(ApprovalStatus.APPROVED.toString(), subscription.getSavedState());
        assertNotNull(subscription.getStartDate());
        assertNull(subscription.getEndDate());
    }

    @Test
    public void testCancelStatusImmediately() {
        subscription.cancel();
        assertInstanceOf(CancelledState.class, subscription.getState());
        assertEquals(ApprovalStatus.PENDING, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.CANCELLED, subscription.getSubscriptionStatus());
        assertEquals(SubscriptionStatus.CANCELLED.toString(), subscription.getSavedState());
        assertNull(subscription.getStartDate());
        assertNull(subscription.getEndDate());
    }

    @Test
    public void testCancelAfterSubscribed(){
        subscription.approve();
        subscription.cancel();
        assertInstanceOf(CancelledState.class, subscription.getState());
        assertEquals(ApprovalStatus.APPROVED, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.CANCELLED, subscription.getSubscriptionStatus());
        assertEquals(SubscriptionStatus.CANCELLED.toString(), subscription.getSavedState());
        assertNotNull(subscription.getStartDate());
        assertNotNull(subscription.getEndDate());
    }

    @Test //tidak ada perubahan pada rejected state
    public void testCancelStatusAfterRejected() {
        subscription.reject();
        subscription.cancel();
        assertInstanceOf(RejectedState.class, subscription.getState());
        assertEquals(ApprovalStatus.REJECTED, subscription.getApprovalStatus());
        assertEquals(SubscriptionStatus.CANCELLED, subscription.getSubscriptionStatus());
        assertEquals(ApprovalStatus.REJECTED.toString(), subscription.getSavedState());
        assertNull(subscription.getStartDate());
        assertNull(subscription.getEndDate());
    }

    @Test
    public void testSubscriptionCodeFormat() {
        String subscriptionCode = subscription.getSubscriptionCode();
        assertNotNull(subscriptionCode);
        String[] parts = subscriptionCode.split("-");
        assertEquals(2, parts.length);
        assertEquals(subsType.getValue(), parts[0]+"-");
    }

    @Test
    void testMonthlyCode() {
        Subscription subscription3 = new Subscription(SubscriptionType.MONTHLY, customerId, boxId);

        assertTrue(subscription3.getSubscriptionCode().startsWith("MTH-"));
    }

    @Test
    void testQuarterlyCode() {
        Subscription subscription2 = new Subscription(SubscriptionType.QUARTERLY, customerId, boxId);

        assertTrue(subscription2.getSubscriptionCode().startsWith("QTR-"));
    }

    @Test
    void testSemiAnnualCode() {
        assertTrue(subscription.getSubscriptionCode().startsWith("SAA-"));
    }


}