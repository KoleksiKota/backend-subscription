package id.ac.ui.cs.advprog.koleksikota.subscription.controller;

import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
import id.ac.ui.cs.advprog.koleksikota.subscription.service.SubscriptionService;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController()
public class SubscriptionController {
    @GetMapping("/")
    public String subscription() {
        return "subscription";
    }

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.findAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<Subscription> findSubscriptionById(@PathVariable String subscriptionId) {
        Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/subscription-box/{boxId}/subscribe")
    public ResponseEntity<Subscription> createSubscription(@PathVariable String boxId, @RequestBody Map<String, String> requestBody) {
        SubscriptionType subsType = SubscriptionType.getFromString(requestBody.get("subsType"));
        String customerId = requestBody.get("customerId");
        Subscription newSubscription = subscriptionService.createSubscription(subsType, customerId, boxId);
        return ResponseEntity.ok(newSubscription);
    }

    @PatchMapping("subscriptions/{id}/change-status")
    public ResponseEntity<Subscription> updateApprovalStatus(@PathVariable String id, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        Subscription updatedSubscription = subscriptionService.changeApprovalStatus(id, status);
        return ResponseEntity.ok(updatedSubscription);
    }

    @PatchMapping("subscriptions/{id}/unsubscribe")
    public ResponseEntity<Subscription> cancelSubscription(@PathVariable String id) {
        Subscription cancelledSubscription = subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok(cancelledSubscription);
    }
}