package com.fabloplatforms.business.store.models;

public class OrdersCount {
    int preOrder,readyOrder,pickedOrder;

    public OrdersCount() {
    }

    public int getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(int preOrder) {
        this.preOrder = preOrder;
    }

    public int getReadyOrder() {
        return readyOrder;
    }

    public void setReadyOrder(int readyOrder) {
        this.readyOrder = readyOrder;
    }

    public int getPickedOrder() {
        return pickedOrder;
    }

    public void setPickedOrder(int pickedOrder) {
        this.pickedOrder = pickedOrder;
    }
}
