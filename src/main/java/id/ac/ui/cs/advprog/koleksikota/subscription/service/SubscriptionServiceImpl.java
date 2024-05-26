package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
import id.ac.ui.cs.advprog.koleksikota.subscription.repository.SubscriptionRepository;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.ApprovalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(SubscriptionType subsType, String customerId, String boxId) {
        Subscription subscription = new Subscription(subsType, customerId, boxId);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription cancelSubscription(String subscriptionId) {
        Subscription subscription = this.findSubscriptionById(subscriptionId);
        subscription.cancel();
        subscriptionRepository.save(subscription);
        return subscription;
    }

    @Override
    public Subscription findSubscriptionById(String subscriptionId) {
        return subscriptionRepository.findById(UUID.fromString(subscriptionId)).orElseThrow(() -> new IllegalArgumentException("Subscription with ID " + subscriptionId + " not found"));
    }

    @Override
    public List<Subscription> findAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription changeApprovalStatus(String subscriptionId, String status) {
        Subscription subscription = this.findSubscriptionById(subscriptionId);
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

