package org.javaacademy.car_advert.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.car_advert.dto.AdvertDtoRq;
import org.javaacademy.car_advert.dto.AdvertDtoRs;
import org.javaacademy.car_advert.entity.Advert;
import org.javaacademy.car_advert.repository.AdvertRepository;
import org.javaacademy.car_advert.repository.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;

    public AdvertDtoRs create(AdvertDtoRq advertDtoRq) {
        Advert entity = convert(advertDtoRq);
        advertRepository.save(entity);
        return convert(entity);
    }

    public List<AdvertDtoRs> findByDate(LocalDate advertDate) {
        List<Advert> adverts = advertRepository.getByAdvertDate(advertDate);
        return new ArrayList<>(adverts.stream()
                .map(this::convert)
                .toList());
    }

    public AdvertDtoRs getByKey(UUID key) {
        Advert entity = advertRepository.getByKey(key).orElseThrow(EntityNotFoundException::new);
        return convert(entity);
    }

    private AdvertDtoRs convert(Advert entity) {
        return AdvertDtoRs.builder()
                .id(entity.getId())
                .brand(entity.getBrand())
                .color(entity.getColor())
                .createdDate(entity.getCreatedDate())
                .model(entity.getModel())
                .price(entity.getPrice())
                .build();
    }

    private Advert convert(AdvertDtoRq dto) {
        return Advert.builder()
                .price(dto.getPrice())
                .color(dto.getColor())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .createdDate(LocalDate.now())
                .build();
    }

    public void deleteByKey(UUID key) {
        advertRepository.deleteByKey(key);
    }

    public List<AdvertDtoRs> findByFilter(LocalDate date,
                                          String model,
                                          String brand,
                                          String color,
                                          BigDecimal price) {
        return advertRepository.findByFilter(date, model, brand, color, price).stream()
                .map(this::convert)
                .toList();
    }
}
