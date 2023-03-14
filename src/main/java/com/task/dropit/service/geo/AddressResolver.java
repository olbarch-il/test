package com.task.dropit.service.geo;

import com.task.dropit.model.address.Address;

public interface AddressResolver {
    Address resolve(String address);
}
