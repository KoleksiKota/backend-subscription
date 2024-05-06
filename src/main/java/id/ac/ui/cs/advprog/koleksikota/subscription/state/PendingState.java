package id.ac.ui.cs.advprog.koleksikota.subscription.state;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.SubscriptionIntegrated;

import java.util.Date;

public class PendingState implements SubscriptionState {
    @Override
    public void approve(SubscriptionIntegrated subscription) {
        subscription.setApprovalStatus(ApprovalStatus.APPROVED);
        subscription.setStartDate(new Date());
        subscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        subscription.setState(new ApprovedState());
    }

    @Override
    public void cancel(SubscriptionIntegrated subscription) {
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
        subscription.setState(new CancelledState());
    }

    @Override
    public void reject(SubscriptionIntegrated subscription) {
        subscription.setApprovalStatus(ApprovalStatus.REJECTED);
        subscription.setState(new RejectedState());
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
    }
}