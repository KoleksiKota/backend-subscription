package id.ac.ui.cs.advprog.koleksikota.subscription.state;


import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;

public interface SubscriptionState {
    void approve(Subscription subscription);
    void reject(Subscription subscription);
    void cancel(Subscription subscription);
}