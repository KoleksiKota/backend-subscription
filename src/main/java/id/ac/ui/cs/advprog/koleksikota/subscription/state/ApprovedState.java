package id.ac.ui.cs.advprog.koleksikota.subscription.state;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;

import java.util.Date;

public class ApprovedState implements SubscriptionState {
    @Override
    public void approve(SubscriptionIntegrated subscription) {
        // Already approved
    }

    @Override
    public void cancel(SubscriptionIntegrated subscription) {
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
        subscription.setState(new CancelledState());
        subscription.setEndDate(new Date());
    }

    @Override
    public void reject(SubscriptionIntegrated subscription) {
        // Admin can only reject a pending state
    }
}