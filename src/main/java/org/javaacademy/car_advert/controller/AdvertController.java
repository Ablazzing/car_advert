package org.javaacademy.car_advert.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.car_advert.dto.AdvertDtoRq;
import org.javaacademy.car_advert.dto.AdvertDtoRs;
import org.javaacademy.car_advert.repository.EntityNotFoundException;
import org.javaacademy.car_advert.service.AdvertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/advert")
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService advertService;

    @PostMapping
    public ResponseEntity<AdvertDtoRs> createAdvert(@RequestBody AdvertDtoRq dtoRq) {
        AdvertDtoRs dtoRs = advertService.create(dtoRq);
        return status(HttpStatus.CREATED).body(dtoRs);
    }

    @GetMapping("/date")
    public ResponseEntity<?> getAdvertsByDate(@RequestParam String date) {
        try {
            return ok(advertService.findByDate(parseParamDate(date)));
        } catch (DateTimeParseException e) {
            return badRequest().body("Ошибка с форматом даты:\n" + e.getMessage());
        }
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<?> deleteByKey(@PathVariable UUID key) {
        try {
            advertService.deleteByKey(key);
            return accepted().build();
        } catch (EntityNotFoundException e) {
            return notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findByParams(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) BigDecimal price
            ) {
        try {
            LocalDate localDate = date != null
                    ? parseParamDate(date)
                    : null;
            return ok(advertService.findByFilter(localDate, model, brand, color, price));
        } catch (DateTimeParseException e) {
            return badRequest().body("Ошибка с форматом даты:\n" + e.getMessage());
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> findByKey(@PathVariable UUID key) {
        try {
            return ok(advertService.getByKey(key));
        } catch (EntityNotFoundException e) {
            return notFound().build();
        }

    }

    private LocalDate parseParamDate(String date) {
        String datePattern = "dd_MM_yyyy";
        DateTimeFormatter dateTimeFormatter = ofPattern(datePattern);
        return parse(date, dateTimeFormatter);
    }

}
