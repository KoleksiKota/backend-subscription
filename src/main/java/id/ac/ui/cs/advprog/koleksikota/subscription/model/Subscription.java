package id.ac.ui.cs.advprog.koleksikota.subscription.model;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.ApprovalStatus;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionStatus;
import id.ac.ui.cs.advprog.koleksikota.subscription.enums.SubscriptionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
public class Subscription {
    private String subscriptionId;
    private Date startDate;
    private Date endDate;
    private String boxId;
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    private String customerId;
    private String code;
    private Date createdDate;

    public Subscription() {
        this.subscriptionId = UUID.randomUUID().toString();
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        this.code = subscriptionType.getValue();
    }
}