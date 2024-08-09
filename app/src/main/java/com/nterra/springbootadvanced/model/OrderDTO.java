package com.nterra.springbootadvanced.model;

import java.util.List;

public record OrderDTO(int number,
                       CustomerDTO customerDTO,
                       List<OrderItemDTO> orderItemDTOs) {

}
