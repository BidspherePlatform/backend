package com.bidsphere.bidsphere.components;

import lombok.Getter;

@Getter
public class RESTResponse<T> {
    boolean errored;
    String errorMessage;
    T data;

    private RESTResponse(
            boolean errored,
            String errorMessage,
            T data
    ) {
        this.errored = errored;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static <U> RESTResponse<U> passed(U data) {
        return new RESTResponse<>(false, null, data);
    }

    public static <U> RESTResponse<U> failed(String errorMessage) {
        return new RESTResponse<>(true, errorMessage, null);
    }
}
