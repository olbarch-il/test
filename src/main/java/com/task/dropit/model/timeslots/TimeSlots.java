package com.task.dropit.model.timeslots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeSlots {
    private Set<TimeSlot> availableTimeslots;
}
