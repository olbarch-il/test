package com.task.dropit.model.timeslots;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class TimeSlot {

        private long id;
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endTime;

}
