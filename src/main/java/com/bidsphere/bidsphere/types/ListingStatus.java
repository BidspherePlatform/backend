package com.bidsphere.bidsphere.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ListingStatus {
    WAITING(0),
    ACTIVE(1),
    COMPLETED(2),
    CLOSED(3),
    TERMINATED(4),
    MODERATED(5);

    private final int code;

    ListingStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
