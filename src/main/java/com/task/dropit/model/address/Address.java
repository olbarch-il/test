package com.task.dropit.model.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public final class Address {
    private String country;
    private String postcode;
    private String street;
    private String addressLine1;
    private  String addressLine2;

}

