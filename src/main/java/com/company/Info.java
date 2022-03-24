package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Info {
    private Long userId;
    private String name;
    private Double quantity;
    private String unit;
    private Double amount;
    private LocalDateTime date;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getNameWithoutHTML() {
        String n = name;
        n = n.replaceAll("</i>", "");
        n = n.replaceAll("<i>", "");
        return n;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStringDate() {
        return date.format(formatter);
    }

    public void setStringDate(String line){
        date = LocalDateTime.parse(line, formatter);
    }

    @Override
    public String toString() {
        if (quantity != 0){
             return name + " - " + Manager.DTOS(quantity.doubleValue()) + " " + unit + ": 💰" +
                     Manager.DTOS(amount.doubleValue()) + " so'm  📆" + getStringDate();
        }
        return name + ": " + " 💰" + Manager.DTOS(amount.doubleValue()) + " so'm  📆" + getStringDate();
    }
}
