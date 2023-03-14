package com.task.dropit.service.delivery;

import com.task.dropit.model.delivery.DeliveryEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface Delivery {
    Long bookDelivery(Long userId, Long timeslotId);

    void cancelDelivery(long id);

    List<DeliveryEntity> getDeliveriesByTimeSlot(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
