package com.task.dropit.service.geo.mapper;


import com.task.dropit.model.address.Address;

public interface Mapper<A> {
    Address mapToAddress(A featureCollection);
}
