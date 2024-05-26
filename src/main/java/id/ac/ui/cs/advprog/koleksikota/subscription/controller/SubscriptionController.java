package id.ac.ui.cs.advprog.koleksikota.subscription.controller;

import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
import id.ac.ui.cs.advprog.koleksikota.subscription.service.SubscriptionService;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin
@EnableAsync
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Async
    @GetMapping("/subscriptions")
    public CompletableFuture<ResponseEntity<List<Subscription>>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.findAllSubscriptions();
        return CompletableFuture.completedFuture(ResponseEntity.ok(subscriptions));
    }

    @Async
    @GetMapping("/{subscriptionId}")
    public CompletableFuture<ResponseEntity<Subscription>> findSubscriptionById(@PathVariable String subscriptionId) {
        Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
        return CompletableFuture.completedFuture(ResponseEntity.ok(subscription));
    }

    @Async
    @PostMapping("/subscription-box/{boxId}/subscribe")
    public CompletableFuture<ResponseEntity<Subscription>> createSubscription(@PathVariable String boxId, @RequestBody Map<String, String> requestBody) {
        SubscriptionType subsType = SubscriptionType.getFromString(requestBody.get("subsType"));
        String customerId = requestBody.get("customerId");
        Subscription createdSubscription = subscriptionService.createSubscription(subsType, customerId, boxId);
        return CompletableFuture.completedFuture(ResponseEntity.ok(createdSubscription));
    }

    @PutMapping("subscriptions/{id}/change-status")
    public ResponseEntity<Subscription> updateApprovalStatus(@PathVariable String id, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        Subscription updatedSubscription = subscriptionService.changeApprovalStatus(id, status);
        return ResponseEntity.ok(updatedSubscription);
    }

    @Async
    @PatchMapping("subscriptions/{id}/unsubscribe")
    public CompletableFuture<ResponseEntity<Subscription>> cancelSubscription(@PathVariable String id) {
        Subscription canceledSubscription = subscriptionService.cancelSubscription(id);
        return CompletableFuture.completedFuture(ResponseEntity.ok(canceledSubscription));
    }
}
