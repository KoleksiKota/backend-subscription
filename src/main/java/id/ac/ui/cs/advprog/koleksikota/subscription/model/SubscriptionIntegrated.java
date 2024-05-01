package id.ac.ui.cs.advprog.koleksikota.subscription.model;

import jakarta.persistence.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class SubscriptionIntegrated {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscription_id", updatable = false, nullable = false)
    private UUID subscriptionId;

    @Column(name="created_date", updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name="subscription_code", updatable = false, nullable = false)
    private String subscriptionCode;

    @Column(name="approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

    @Column(name="subscription_status", nullable = false)
    private SubscriptionStatus subscriptionStatus;

    @Column(name="subscription_type", updatable = false, nullable = false)
    private SubscriptionType subscriptionType;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="customer_id", updatable = false, nullable = false)
    private String customerId;

    @Column(name="box_id", updatable = false, nullable = false)
    private String boxId;

    public SubscriptionIntegrated() {
        this.subscriptionId = UUID.randomUUID();
    }

    public SubscriptionIntegrated(SubscriptionType subsType, String customerId, String boxId) {
        this.subscriptionId = UUID.randomUUID();
        this.createdDate = new Date();
        this.subscriptionCode = subsType.getValue() + Long.toHexString(Double.doubleToLongBits(Math.random()));
        this.approvalStatus = ApprovalStatus.PENDING;
        this.subscriptionStatus = SubscriptionStatus.PENDING;
        this.subscriptionType = subsType;
        this.customerId = customerId;
        this.boxId = boxId;
    }
}