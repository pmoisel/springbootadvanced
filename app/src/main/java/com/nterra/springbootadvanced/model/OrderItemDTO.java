package com.nterra.springbootadvanced.model;

public record OrderItemDTO(String name,
                           double price,
                           int quantity) {

}
