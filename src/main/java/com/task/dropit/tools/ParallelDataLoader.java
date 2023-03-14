package com.task.dropit.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.dropit.dao.HolidaysRepository;
import com.task.dropit.dao.PostCodeRepository;
import com.task.dropit.dao.TimeSlotRepository;
import com.task.dropit.model.holidays.HolidayEntity;
import com.task.dropit.model.holidays.HolidaysPayload;
import com.task.dropit.model.holidays.HolidaysPayload.HolidayJson;
import com.task.dropit.model.postcodes.PostCodeEntity;
import com.task.dropit.model.timeslots.TimeSlotEntity;
import com.task.dropit.model.timeslots.TimeSlotsPayload;
import com.task.dropit.model.timeslots.TimeSlotsPayload.TimeSlot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParallelDataLoader implements ApplicationRunner {
    private final TimeSlotRepository timeslotsRepository;
    private final HolidaysRepository holidaysRepository;
    private final PostCodeRepository postCodeRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    @Value("classpath:timeslots.json")
    private Resource timeslots;
    @Value("classpath:holidays.json")
    private Resource holidays;

    @Override
    public void run(ApplicationArguments args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> saveTimeSlots(loadTimeSlots()), executorService);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> saveHolidays(loadHolidays()), executorService);
        CompletableFuture.allOf(future1, future2).join();
        executorService.shutdown();
    }

    private TimeSlotsPayload loadTimeSlots() {
        try {
            File timeslotsFile = timeslots.getFile();
            return objectMapper.readValue(timeslotsFile, TimeSlotsPayload.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load time slots data", e);
        }
    }

    private HolidaysPayload loadHolidays() {
        try {
            File holidaysFile = holidays.getFile();
            return objectMapper.readValue(holidaysFile, HolidaysPayload.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load holidays data", e);
        }
    }

    private void saveTimeSlots(TimeSlotsPayload timeSlotsJson) {
        if(timeslotsRepository.count() == 0 && postCodeRepository.count() == 0) {
            Set<TimeSlot> timeslotsList = timeSlotsJson.getTimeslots();
            Set<TimeSlotEntity> timeSlotEntities = timeslotsList.stream().map(ParallelDataLoader::toTimeSlot).collect(Collectors.toSet());
            Set<PostCodeEntity> postcodes = timeSlotEntities.stream().flatMap(t -> t.getPostcodes().stream()).collect(Collectors.toSet());
            postCodeRepository.saveAll(postcodes);
            timeslotsRepository.saveAll(timeSlotEntities);
        }
    }

    private void saveHolidays(HolidaysPayload holidaysJson) {
        if(holidaysRepository.count() == 0) {
            List<HolidayJson> holydaysList = holidaysJson.getHolidays();
            List<HolidayEntity> holidayEntities = holydaysList.stream()
                    .map(s -> modelMapper.map(s, HolidayEntity.class))
                    .toList();
            holidaysRepository.saveAll(holidayEntities);
        }

    }

    private static TimeSlotEntity toTimeSlot(TimeSlot s) {
        Set<PostCodeEntity> postcodes = s.getPostcodes().stream().map(p -> PostCodeEntity.builder().postalCode(p).build()).collect(Collectors.toSet());
        return TimeSlotEntity.builder()
                .startTime(s.getStartTime())
                .endTime(s.getEndTime())
                .postcodes(postcodes)
                .build();
    }

}
