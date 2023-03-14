package com.task.dropit.controller;

import com.task.dropit.exception.InvalidParameterException;
import com.task.dropit.model.delivery.Delivery;
import com.task.dropit.model.delivery.DeliveryEntity;
import com.task.dropit.model.delivery.DeliveryRequest;
import com.task.dropit.service.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<String> bookDelivery(@RequestBody DeliveryRequest deliveryRequest) {
        Long userId = deliveryRequest.getUserId();
        Long timeslotId = deliveryRequest.getTimeslotId();
        if(userId == null || timeslotId == null) {
            throw new InvalidParameterException("Invalid request");
        }
        Long deliveryId = deliveryService.bookDelivery(userId, timeslotId);
        return ResponseEntity.ok("Delivery has been reserved. Delivery ID: " + deliveryId);
    }

    @DeleteMapping("{deliveryId}")
    public ResponseEntity<String> cancelDelivery(@PathVariable("deliveryId") Long deliveryId) {
        deliveryService.cancelDelivery(deliveryId);
        return ResponseEntity.ok("Delivery with id " + deliveryId + " has been canceled.");
    }

    @GetMapping("/daily")
    public List<Delivery> getDailyDeliveries() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusSeconds(1);
        return getDeliveries(startOfDay, endOfDay);
    }

    @GetMapping("/monthly")
    public List<Delivery> getMonthlyDeliveries() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atStartOfDay().plusDays(1).minusSeconds(1);
        return getDeliveries(startOfMonth, endOfMonth);
    }

    private List<Delivery> getDeliveries(LocalDateTime startOfMonth, LocalDateTime endOfMonth) {
        List<DeliveryEntity> deliveries = deliveryService.getDeliveriesByTimeSlot(startOfMonth, endOfMonth);
        return deliveries.stream()
                .map(d -> new Delivery(d.getId(), d.getStatus(), d.getTimeSlotId()))
                .collect(Collectors.toList());
    }


}

