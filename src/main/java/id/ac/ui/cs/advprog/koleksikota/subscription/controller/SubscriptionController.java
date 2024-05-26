package id.ac.ui.cs.advprog.koleksikota.subscription.controller;

import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;
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
    public ResponseEntity<List<SubscriptionIntegrated>> getAllSubscriptions() {
        List<SubscriptionIntegrated> subscriptions = subscriptionService.findAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionIntegrated> findSubscriptionById(@PathVariable String subscriptionId) {
        SubscriptionIntegrated subscription = subscriptionService.findSubscriptionById(subscriptionId);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/subscription-box/{boxId}/subscribe")
    public ResponseEntity<SubscriptionIntegrated> createSubscription(@PathVariable String boxId, @RequestBody Map<String, String> requestBody) {
        SubscriptionType subsType = SubscriptionType.getFromString(requestBody.get("subsType"));
        String customerId = requestBody.get("customerId");
        SubscriptionIntegrated newSubscription = subscriptionService.createSubscription(subsType, customerId, boxId);
        return ResponseEntity.ok(newSubscription);
    }

    @PatchMapping("subscriptions/{id}/change-status")
    public ResponseEntity<SubscriptionIntegrated> updateApprovalStatus(@PathVariable String id, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        SubscriptionIntegrated updatedSubscription = subscriptionService.changeApprovalStatus(id, status);
        return ResponseEntity.ok(updatedSubscription);
    }

    @PatchMapping("subscriptions/{id}/unsubscribe")
    public ResponseEntity<SubscriptionIntegrated> cancelSubscription(@PathVariable String id) {
        SubscriptionIntegrated cancelledSubscription = subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok(cancelledSubscription);
    }
}