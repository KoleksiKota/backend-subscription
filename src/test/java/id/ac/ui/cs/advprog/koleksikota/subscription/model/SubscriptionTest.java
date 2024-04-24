package id.ac.ui.cs.advprog.koleksikota.subscription.model;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        this.subscription = new Subscription();
    }

    @Test
    void testMonthlyCode() {
        this.subscription.setSubscriptionType(SubscriptionType.MONTHLY);

        assertTrue(subscription.getCode().startsWith("MTH-"));
    }

    void testQuarterlyCode() {
        this.subscription.setSubscriptionType(SubscriptionType.QUARTERLY);

        assertTrue(subscription.getCode().startsWith("QTR-"));
    }

    void testSemiAnnualCode() {
        this.subscription.setSubscriptionType(SubscriptionType.SEMI_ANNUAL);

        assertTrue(subscription.getCode().startsWith("SAA-"));
    }

}
