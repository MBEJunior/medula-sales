package com.medulasales.products.exceptions;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
abstract class AppException extends Exception {
    private AppError error;
    private List<ErrorItem> items;
    private Object[] args;

    private void initItems() {
        if(items == null) {
            items = new ArrayList<>();
        }
    }

    public AppException(AppError error, Object ...args) {
        super(String.format(error.getMessageTemplate(), args));
        this.error = error;
    }

    public AppException(AppError error, List<ErrorItem> items, Object ...args) {
        super(String.format(error.getMessageTemplate(), args));
        this.error = error;
        this.items = items;
    }

    public AppException addItem(String target, String description) {
        this.initItems();
        this.items.add(new ErrorItem(target, description));
        return this;
    }

    public AppException addItem(ErrorItem item) {
        this.initItems();
        this.items.add(item);
        return this;
    }

    public AppException removeItem(ErrorItem item) {
        this.initItems();
        this.items.remove(item);
        return this;
    }

    public AppException removeAll(@NonNull String target) {
        this.initItems();
        for (ErrorItem item : this.items) {
            if(target.equals(item.getTarget())) {
                this.items.remove(item);
            }
        }
        return this;
    }
}
