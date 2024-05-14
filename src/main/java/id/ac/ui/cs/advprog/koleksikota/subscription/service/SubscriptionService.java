package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    SubscriptionIntegrated createSubscription(SubscriptionType subsType, String customerId, String boxId);
    SubscriptionIntegrated cancelSubscription(String subscriptionId);
    SubscriptionIntegrated findSubscriptionById(String subscriptionId);
    List<SubscriptionIntegrated> findAllSubscriptions();
    SubscriptionIntegrated changeApprovalStatus(String subscriptionId, String status);

}