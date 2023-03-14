package com.task.dropit.model.delivery;

import com.task.dropit.model.timeslots.TimeSlotEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {
    List<Delivery> deliveries;
}
