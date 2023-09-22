package com.example.opencommerce.domain;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class OperationResult<T> {

    private final List<String> errors = new ArrayList<>();
    private final ResultType type;
    private final T result;

    private OperationResult(T result, ResultType type) {
        this.result = result;
        this.type = requireNonNull(type);
    }

    private OperationResult(T result, ResultType type, List<String> errors) {
        this(result, type);
        this.errors.addAll(requireNonNull(errors));
    }

    public static <T> OperationResult<T> success(T payload) {
        return new OperationResult<>(payload, ResultType.OK);
    }

    public static <T> OperationResult<T> failed(List<String> errors) {
        return new OperationResult<>(null, ResultType.FAILED, errors);
    }

    public static <T> OperationResult<T> failed(String error) {
        return new OperationResult<>(null, ResultType.FAILED, List.of(error));
    }

    public T result() {
        if (!successful()) {
            throw new IllegalStateException("Can't pull result from failed operation!");
        }
        return result;
    }

    public boolean successful() {
        return this.type == ResultType.OK;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    enum ResultType {
        FAILED, OK
    }
}
