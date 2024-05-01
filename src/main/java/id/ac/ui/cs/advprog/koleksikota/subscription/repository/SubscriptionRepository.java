package id.ac.ui.cs.advprog.koleksikota.subscription.repository;

import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
}