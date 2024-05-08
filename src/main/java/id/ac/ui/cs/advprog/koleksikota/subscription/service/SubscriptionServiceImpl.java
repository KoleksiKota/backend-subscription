package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
import id.ac.ui.cs.advprog.koleksikota.subscription.repository.SubscriptionIntegratedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionIntegratedRepository subscriptionRepository;

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
}
