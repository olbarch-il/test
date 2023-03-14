package com.task.dropit.service.geo.mapper;
import com.task.dropit.exception.GeoMappingException;
import com.task.dropit.model.address.Address;
import com.task.dropit.model.address.FeatureCollection;
import com.task.dropit.model.address.FeatureCollection.Feature;
import com.task.dropit.model.address.FeatureCollection.Feature.Properties;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class GeoAddressMapperTest {
    private GeoAddressMapper mapper;
    @Before
    public void setUp() {
        mapper = new GeoAddressMapper(new ModelMapper());
    }

    @Test
    public void testMapToAddress_withValidFeatureCollection_returnsAddress() {
        Properties properties = new Properties(
                "US",
                "94043",
                "Amphitheatre Parkway",
                "1600",
                "Mountain View"
        );
        Feature feature = new Feature(properties);
        List<Feature> features = Collections.singletonList(feature);
        FeatureCollection featureCollection = new FeatureCollection(features);
        Address expectedAddress = new Address(
                "US",
                "94043",
                "Amphitheatre Parkway",
                "1600",
                "Mountain View"
        );
        Address actualAddress = mapper.mapToAddress(featureCollection);
        assertEquals(expectedAddress, actualAddress);
    }

    @Test
    public void testMapToAddress_withEmptyFeatureCollection_throwsGeoMappingException() {
        FeatureCollection featureCollection = new FeatureCollection(Collections.emptyList());
        assertThrows(GeoMappingException.class, () -> mapper.mapToAddress(featureCollection));
    }
}

