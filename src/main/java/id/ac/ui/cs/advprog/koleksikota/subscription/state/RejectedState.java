package id.ac.ui.cs.advprog.koleksikota.subscription.state;

import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;

public class RejectedState implements SubscriptionState {
    @Override
    public void approve(Subscription subscription) {
        // Admin can only approve a pending state
    }

    @Override
    public void cancel(Subscription subscription) {
        // User cannot cancel a rejected state
    }

    @Override
    public void reject(Subscription subscription) {
        // Already rejected
    }
}