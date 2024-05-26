package id.ac.ui.cs.advprog.koleksikota.subscription.state;

import id.ac.ui.cs.advprog.koleksikota.subscription.enums.*;
import id.ac.ui.cs.advprog.koleksikota.subscription.model.Subscription;

import java.util.Date;

public class PendingState implements SubscriptionState {
    @Override
    public void approve(Subscription subscription) {
        subscription.setApprovalStatus(ApprovalStatus.APPROVED);
        subscription.setStartDate(new Date());
        subscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        subscription.setState(new ApprovedState());
        subscription.setSavedState(ApprovalStatus.APPROVED.toString());
    }

    @Override
    public void cancel(Subscription subscription) {
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
        subscription.setState(new CancelledState());
        subscription.setSavedState(SubscriptionStatus.CANCELLED.toString());
    }

    @Override
    public void reject(Subscription subscription) {
        subscription.setApprovalStatus(ApprovalStatus.REJECTED);
        subscription.setState(new RejectedState());
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
        subscription.setSavedState(ApprovalStatus.REJECTED.toString());
    }
}