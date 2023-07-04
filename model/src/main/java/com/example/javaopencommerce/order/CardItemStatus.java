package com.example.javaopencommerce.order;

class CardItemStatus {

    final static CardItemStatus OK = new CardItemStatus(StatusType.OK);
    final static CardItemStatus NOT_ENOUGH_IN_STOCK = new CardItemStatus(StatusType.KO,
            Problem.NOT_ENOUGH_IN_A_STOCK);
    final static CardItemStatus ITEM_NO_LONGER_EXISTS = new CardItemStatus(StatusType.KO,
            Problem.ITEM_NO_LONGER_EXISTS);


    private final StatusType result;
    private Problem problemCause;

    private CardItemStatus(StatusType result) {
        this.result = result;
    }

    private CardItemStatus(StatusType result, Problem problemCause) {
        this.result = result;
        this.problemCause = problemCause;
    }

    boolean ok() {
        return this.result.equals(StatusType.OK);
    }

    Problem getCause() {
        if (ok()) {
            throw new IllegalStateException("Can't pull cause for OK status!");
        }
        return problemCause;
    }

    enum StatusType {
        OK, KO
    }

    enum Problem {
        OUT_OF_STOCK,
        NOT_ENOUGH_IN_A_STOCK,
        ITEM_NO_LONGER_EXISTS
    }
}
