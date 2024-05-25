package id.ac.ui.cs.advprog.koleksikota.subscription.state;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;

import java.util.Date;

public class CancelledState implements SubscriptionState {
    @Override
    public void approve(SubscriptionIntegrated subscription) {
        // Admin can only approve a pending state
    }

    @Override
    public void cancel(SubscriptionIntegrated subscription) {
        // Already cancelled
    }

    @Override
    public void reject(SubscriptionIntegrated subscription) {
        // Admin can only reject a pending state
    }
}