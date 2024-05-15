package id.ac.ui.cs.advprog.koleksikota.subscription.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.state.*;
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

    @JsonIgnore
    @Column(name="state", nullable=false)
    private String savedState;

    @JsonIgnore
    @Transient
    private SubscriptionState state;

    public SubscriptionIntegrated() {
        this.subscriptionId = UUID.randomUUID();
        this.state = new PendingState();
        this.savedState = SubscriptionStatus.PENDING.toString();
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
        this.state = new PendingState();
        this.savedState = SubscriptionStatus.PENDING.toString();
    }

    @PostLoad
    public void afterLoad(){
        switch(savedState) {
            case "APPROVED":
                this.state = new ApprovedState();
                break;
            case "REJECTED":
                this.state = new RejectedState();
                break;
            case "CANCELLED":
                this.state = new CancelledState();
                break;
            default:
                this.state = new PendingState();
                break;
        }
    }

    public void approve(){
        this.state.approve(this);
    }

    public void reject(){
        this.state.reject(this);
    }

    public void cancel(){
        this.state.cancel(this);
    }

}