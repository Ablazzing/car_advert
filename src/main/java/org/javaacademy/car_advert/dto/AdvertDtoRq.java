package org.javaacademy.car_advert.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Request (rq) - запрос
 */
@Data
public class AdvertDtoRq {
    private String brand;
    private String color;
    private String model;
    private BigDecimal price;
}
