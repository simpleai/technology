package com.xiaoruiit.knowledge.point.designpattern.ChainOfResponsibility;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    String orderId;
    List<Item> items;

    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", items=" + items +
                '}';
    }
}

@Data
class Item {
    String name;
    String category;
    String region;
    int quantity;
    int stock;

    public Item(String name, String category, String region, int quantity, int stock) {
        this.name = name;
        this.category = category;
        this.region = region;
        this.quantity = quantity;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", region='" + region + '\'' +
                ", quantity=" + quantity +
                ", stock=" + stock +
                '}';
    }
}