package com.bidsphere.bidsphere.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ListingStatus {
    UNLISTED(0),
    WAITING(1),
    ACTIVE(2),
    COMPLETED(3),
    CLOSED(4),
    TERMINATED(5),
    ERRORED(6),
    MODERATED(7);

    private final int code;

    ListingStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
