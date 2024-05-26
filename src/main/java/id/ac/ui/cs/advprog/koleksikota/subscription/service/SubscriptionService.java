package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
import java.util.List;

public interface SubscriptionService {
    public Subscription createSubscription(SubscriptionType subsType, String customerId, String boxId);
    public Subscription cancelSubscription(String subscriptionId);
    public Subscription findSubscriptionById(String subscriptionId);
    public List<Subscription> findAllSubscriptions();
    public Subscription changeApprovalStatus(String subscriptionId, String status);

}