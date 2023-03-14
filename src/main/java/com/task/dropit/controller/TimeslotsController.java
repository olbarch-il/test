package com.task.dropit.controller;

import com.task.dropit.model.address.Address;
import com.task.dropit.model.timeslots.TimeSlots;
import com.task.dropit.service.timeslots.TimeSlotsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/timeslots")
@RequiredArgsConstructor
public class TimeslotsController {
    private final TimeSlotsService timeSlotsService;
    @PostMapping
    public TimeSlots getAvailableTimeslots(@RequestBody Address address) {
        return timeSlotsService.getAvailableTimeslots(address);
    }
}
