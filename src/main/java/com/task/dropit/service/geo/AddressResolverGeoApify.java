package com.task.dropit.service.geo;

import com.task.dropit.model.address.Address;
import com.task.dropit.model.address.FeatureCollection;
import com.task.dropit.service.geo.mapper.Mapper;
import com.task.dropit.service.geo.requester.Requester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressResolverGeoApify implements AddressResolver {
    private final Requester<FeatureCollection> requester;
    private final Mapper<FeatureCollection> mapper;
    @Override
    public Address resolve(String address) {
        var featureCollection = requester.request(address);
        return mapper.mapToAddress(featureCollection);
    }
}
