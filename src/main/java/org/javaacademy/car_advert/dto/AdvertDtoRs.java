package org.javaacademy.car_advert.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Response (rs) - ответ
 */
@Data
@Builder
public class AdvertDtoRs {
    private UUID id;
    private String brand;
    private String color;
    private String model;
    private BigDecimal price;
    private LocalDate createdDate;
}
