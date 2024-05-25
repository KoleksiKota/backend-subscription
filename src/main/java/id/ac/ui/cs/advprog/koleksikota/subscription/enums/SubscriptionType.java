package id.ac.ui.cs.advprog.koleksikota.subscription.enums;

import lombok.Getter;

@Getter
public enum SubscriptionType {
    MONTHLY("MTH-"),
    QUARTERLY("QTR-"),
    SEMI_ANNUAL("SAA-");

    private final String value;
    private SubscriptionType(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (SubscriptionType subscriptionType : SubscriptionType.values()) {
            if (subscriptionType.name().equals(param)) {
                return true;
            }
        }
        return false;
    }

    public static SubscriptionType getFromString(String type) {
        for (SubscriptionType subscriptionType : SubscriptionType.values()) {
            if (subscriptionType.getValue().equals(type)) {
                return subscriptionType;
            }
        }
        throw new IllegalArgumentException("Invalid subscription type");
    }
}