package id.ac.ui.cs.advprog.koleksikota.subscription.state;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;

import java.util.Date;

public class ApprovedState implements SubscriptionState {
    @Override
    public void approve(Subscription subscription) {
        // Already approved
    }

    @Override
    public void cancel(Subscription subscription) {
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
        subscription.setState(new CancelledState());
        subscription.setEndDate(new Date());
        subscription.setSavedState(SubscriptionStatus.CANCELLED.toString());
    }

    @Override
    public void reject(Subscription subscription) {
        // Admin can only reject a pending state
    }
}