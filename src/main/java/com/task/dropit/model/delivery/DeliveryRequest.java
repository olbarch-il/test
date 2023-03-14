package com.task.dropit.model.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeliveryRequest {
    private Long userId;
    private Long timeslotId;
}


