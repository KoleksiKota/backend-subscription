package id.ac.ui.cs.advprog.koleksikota.subscription.model;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {
    @BeforeEach
    void setUp() {
        this.subscription = new Subscription();
    }

    @Test
    void testMonthlyCode() {
        this.subscription.setSubscriptionType(SubscriptionType.MONTHLY.getValue());

        assertTrue(subscription.getCode().startsWith("MTH-"));
    }

    void testQuarterlyCode() {
        this.subscription.setSubscriptionType(SubscriptionType.QUARTERLY.getValue());

        assertTrue(subscription.getCode().startsWith("QTR-"));
    }

    void testSemiAnnualCode() {
        this.subscription.setSubscriptionType(SubscriptionType.SEMI_ANNUAL.getValue());

        assertTrue(subscription.getCode().startsWith("SAA-"));
    }

}
