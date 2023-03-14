package com.task.dropit.model.holidays;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class HolidaysPayload {

    @JsonProperty("holidays")
    private List<HolidayJson> holidays;

    @Data
    @NoArgsConstructor
    public static class HolidayJson {
        private String name;
        private LocalDate date;
        private String country;
    }

}
