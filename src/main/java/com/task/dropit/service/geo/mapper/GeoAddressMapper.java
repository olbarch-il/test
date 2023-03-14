package com.task.dropit.service.geo.mapper;

import com.task.dropit.exception.GeoMappingException;
import com.task.dropit.model.address.Address;
import com.task.dropit.model.address.FeatureCollection;
import com.task.dropit.model.address.FeatureCollection.Feature;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GeoAddressMapper implements Mapper<FeatureCollection> {

    private final ModelMapper modelMapper;
    @Override
    public Address mapToAddress(FeatureCollection featureCollection) {
        var features = featureCollection.features();
        Optional<Address> address = features.stream()
                .findFirst()
                .map(Feature::properties)
                .map(toAddress())
                .map(p -> modelMapper.map(p, Address.class));
        return address.stream().findFirst().orElseThrow(() -> new GeoMappingException("can't resolve the address"));
    }

    private static Function<Feature.Properties, Address> toAddress() {
        return p -> Address.builder()
                .postcode(p.postcode())
                .addressLine1(p.addressLine1())
                .addressLine2(p.addressLine2())
                .street(p.street())
                .country(p.country())
                .build();
    }


}

