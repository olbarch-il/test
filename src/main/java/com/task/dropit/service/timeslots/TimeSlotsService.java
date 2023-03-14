package com.task.dropit.service.timeslots;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import com.task.dropit.dao.HolidaysRepository;
import com.task.dropit.dao.TimeSlotRepository;
import com.task.dropit.model.address.Address;
import com.task.dropit.model.holidays.HolidayEntity;
import com.task.dropit.model.timeslots.TimeSlotEntity;
import com.task.dropit.model.timeslots.TimeSlot;
import com.task.dropit.model.timeslots.TimeSlots;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Service
@RestController
public class TimeSlotsService {

    private final TimeSlotRepository timeslotsRepository;
    private final HolidaysRepository holidaysRepository;
    private final ModelMapper mapper;
    private Set<LocalDate> holidays = new HashSet<>();

    public TimeSlotsService(TimeSlotRepository timeslotsRepository, HolidaysRepository holidaysRepository, ModelMapper mapper) {
        this.timeslotsRepository = timeslotsRepository;
        this.holidaysRepository = holidaysRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void loadHolidays() {
        Set<LocalDate> holidayDates = holidaysRepository.findAll()
                .stream()
                .map(HolidayEntity::getDate)
                .collect(Collectors.toSet());
        holidays.addAll(holidayDates);
    }

    @PostMapping("/timeslots")
    @Transactional(readOnly = true)
    public TimeSlots getAvailableTimeslots(@RequestBody Address address) {
        Predicate<TimeSlotEntity> isRelevant = timeslot -> timeslot.getStartTime().isAfter(LocalDateTime.now());
        Predicate<TimeSlotEntity> isNotHoliday = timeslot -> !holidays.contains(timeslot.getStartTime().toLocalDate());
        Set<TimeSlot> timeslots = timeslotsRepository.findByPostcodes_postalCode(address.getPostcode())
                .stream()
                .filter(isRelevant)
                .filter(isNotHoliday)
                .map(t -> mapper.map(t, TimeSlot.class))
                .collect(Collectors.toSet());
        return new TimeSlots(timeslots);
    }

}
