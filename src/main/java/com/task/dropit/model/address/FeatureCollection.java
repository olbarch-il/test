package com.task.dropit.model.address;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FeatureCollection(List<Feature> features) {
    public record Feature(Properties properties) {
        public record Properties(
                String country,
                String postcode,
                String street,
                @JsonProperty("address_line1") String addressLine1,
                @JsonProperty("address_line2") String addressLine2
        ) {
        }
    }

}

