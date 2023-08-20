package com.example.opencommerce.domain;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class OperationResult {

    public static final OperationResult OK = new OperationResult(ResultType.OK);
    public static final OperationResult FAILED = new OperationResult(ResultType.FAILED);

    private final List<String> errors = new ArrayList<>();
    private final ResultType type;

    private OperationResult(ResultType type) {
        this.type = requireNonNull(type);
    }

    private OperationResult(ResultType type, List<String> errors) {
        this.type = requireNonNull(type);
        this.errors.addAll(requireNonNull(errors));
    }

    public static OperationResult failed(List<String> errors) {
        return new OperationResult(ResultType.FAILED, errors);
    }

    public static OperationResult failed(String error) {
        return new OperationResult(ResultType.FAILED, List.of(error));
    }

    public boolean succesful() {
        return this.type == ResultType.OK;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    enum ResultType {
        FAILED, OK
    }
}
