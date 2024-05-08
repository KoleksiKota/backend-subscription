package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
import id.ac.ui.cs.advprog.koleksikota.subscription.repository.SubscriptionRepository;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.ApprovalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionIntegrated createSubscription(SubscriptionType subsType, String customerId, String boxId) {
        SubscriptionIntegrated subscription = new SubscriptionIntegrated(subsType, customerId, boxId);
        return subscriptionRepository.save(subscription);
    }

    public SubscriptionIntegrated cancelSubscription(String subscriptionId) {
        SubscriptionIntegrated subscription = this.findSubscriptionById(subscriptionId);
        subscription.cancel();
        subscriptionRepository.save(subscription);
        return subscription;
    }

    public SubscriptionIntegrated findSubscriptionById(String subscriptionId) {
        return subscriptionRepository.findById(UUID.fromString(subscriptionId)).orElseThrow(() -> new IllegalArgumentException("Subscription with ID " + subscriptionId + " not found"));
    }

    public List<SubscriptionIntegrated> findAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public SubscriptionIntegrated changeApprovalStatus(String subscriptionId, String status) {
        SubscriptionIntegrated subscription = this.findSubscriptionById(subscriptionId);
        if (status.equalsIgnoreCase(ApprovalStatus.APPROVED.toString())) {
            subscription.approve();
        } else if (status.equalsIgnoreCase(ApprovalStatus.REJECTED.toString())) {
            subscription.reject();
        } else {
            throw new IllegalArgumentException("Invalid approval status");
        }
        subscriptionRepository.save(subscription);
        return subscription;
    }
}