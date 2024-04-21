package org.javaacademy.car_advert.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

//(уникальный идентификатор, имя бренда, цвет, цена, модель, дата размещения)
@Data
@Builder
public class Advert {
    private UUID id;
    @NonNull
    private String brand;
    @NonNull
    private String color;
    @NonNull
    private String model;
    @NonNull
    private BigDecimal price;
    @NonNull
    private LocalDate createdDate;
}
