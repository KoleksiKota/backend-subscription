package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    public SubscriptionIntegrated createSubscription(SubscriptionType subsType, String customerId, String boxId);
    public SubscriptionIntegrated cancelSubscription(String subscriptionId);
    public SubscriptionIntegrated findSubscriptionById(String subscriptionId);
    public List<SubscriptionIntegrated> findAllSubscriptions();
    public SubscriptionIntegrated changeApprovalStatus(String subscriptionId, String status);

}