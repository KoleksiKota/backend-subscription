package id.ac.ui.cs.advprog.koleksikota.subscription.enums;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String value;
    private ApprovalStatus(String value) {
        this.value = value;
    }

}