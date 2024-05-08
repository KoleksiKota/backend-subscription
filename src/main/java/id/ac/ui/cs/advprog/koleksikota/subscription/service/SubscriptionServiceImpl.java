package id.ac.ui.cs.advprog.koleksikota.subscription.service;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
import id.ac.ui.cs.advprog.koleksikota.subscription.repository.SubscriptionIntegratedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionIntegratedRepository subscriptionRepository;

    @Override
    public SubscriptionIntegrated createSubscription(SubscriptionType subsType, String customerId, String boxId) {
        return null;
    }

    public SubscriptionIntegrated cancelSubscription(String subscriptionId) {
        return null;
    }

    public SubscriptionIntegrated findSubscriptionById(String subscriptionId) {
        return null;
    }

    public List<SubscriptionIntegrated> findAllSubscriptions() {
        return null;
    }
}
