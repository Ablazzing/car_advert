package org.javaacademy.car_advert.repository;

import org.javaacademy.car_advert.entity.Advert;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Component
public class AdvertRepository {
    private final Map<UUID, Advert> advertMap = new HashMap<>();

    public void save(Advert advert) {
        advert.setId(UUID.randomUUID());
        advertMap.put(advert.getId(), advert);
    }

    public Optional<Advert> getByKey(UUID key) {
        return Optional.ofNullable(advertMap.get(key));
    }

    public List<Advert> getByAdvertDate(LocalDate advertDate) {
        return new ArrayList<>(advertMap.values().stream()
                .filter(advert -> advert.getCreatedDate().equals(advertDate))
                .toList());
    }

    public void deleteByKey(UUID key) {
        Advert advert = advertMap.remove(key);
        if (advert == null) {
            throw new EntityNotFoundException();
        }
    }

    public ArrayList<Advert> findByFilter(LocalDate date, String model, String brand,
                                          String color, BigDecimal price) {
        return new ArrayList<>(
                advertMap.values().stream()
                    .filter(createPredicate(date, model, brand, color, price))
                .toList()
        );
    }

    private Predicate<Advert> createPredicate(LocalDate date, String model, String brand,
                                              String color, BigDecimal price) {
        Predicate<Advert> datePredicate = createDatePredicate(date);
        Predicate<Advert> modelPredicate = advert -> model == null || advert.getModel().equals(model);
        Predicate<Advert> brandPredicate = advert -> brand == null || advert.getBrand().equals(brand);
        Predicate<Advert> pricePredicate = advert -> price == null || advert.getPrice().compareTo(price) == 0;
        Predicate<Advert> colorPredicate = advert -> color == null || advert.getColor().equals(color);
        return datePredicate
                .and(modelPredicate)
                .and(brandPredicate)
                .and(pricePredicate)
                .and(colorPredicate);
    }

    private Predicate<Advert> createDatePredicate(LocalDate date) {
        return advert -> date == null || advert.getCreatedDate().equals(date);
    }
}
