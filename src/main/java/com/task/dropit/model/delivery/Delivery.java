package com.task.dropit.model.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Delivery {
    private Long id;
    private String status;
    private Long timeSlotId;
}
