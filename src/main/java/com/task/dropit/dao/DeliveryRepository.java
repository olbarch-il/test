package com.task.dropit.dao;

import com.task.dropit.model.delivery.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    long countByTimeSlotId(Long timeSlotId);

    List<DeliveryEntity> findByTimeSlotStartTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);

}
