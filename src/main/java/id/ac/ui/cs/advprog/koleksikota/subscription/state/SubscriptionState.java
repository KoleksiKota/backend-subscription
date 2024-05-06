package id.ac.ui.cs.advprog.koleksikota.subscription.state;


import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;

public interface SubscriptionState {
    void approve(SubscriptionIntegrated subscription);
    void reject(SubscriptionIntegrated subscription);
    void cancel(SubscriptionIntegrated subscription);
}