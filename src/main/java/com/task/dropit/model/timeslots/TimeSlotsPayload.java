package com.task.dropit.model.timeslots;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
public class TimeSlotsPayload {
    @JsonProperty("courier_available_timeslots")
    private HashSet<TimeSlot> timeslots;

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = {"startTime", "endTime"})
    public static class TimeSlot {
        @JsonProperty("start_time")
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;

        @JsonProperty("end_time")
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endTime;
        @JsonProperty("supported_postcodes")
        private List<String> postcodes;
    }
}
