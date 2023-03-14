package com.task.dropit.service.geo.requester;

import com.task.dropit.model.address.FeatureCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GeoApifyRequester implements Requester<FeatureCollection> {
    @Value(value = "${geoapify.api.key}")
    private String key;
    @Value(value = "${geoapify.api.url}")
    private String geoUrl;
    private final RestTemplate restTemplate;

    @Override
    public FeatureCollection request(String address) {
        String uri = geoUrl + address + "&apiKey=" + key;
        return restTemplate.getForObject(uri, com.task.dropit.model.address.FeatureCollection.class);
    }
}
