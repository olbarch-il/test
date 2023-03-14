package com.task.dropit.service.geo.requester;

import com.task.dropit.model.address.FeatureCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class GeoApifyRequesterTest {
    private GeoApifyRequester requester;
    private RestTemplate restTemplate;
    String key = "0961a9368c2242728d3cc34d31128bca";
    String url = "https://api.geoapify.com/v1/geocode/search?text=";
    String address = "1600 Amphitheatre Parkway Mountain View CA 94043";
    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        requester = new GeoApifyRequester(restTemplate);
        ReflectionTestUtils.setField(requester, "key", key);
        ReflectionTestUtils.setField(requester, "geoUrl", url);
    }

    @Test
    public void requestTest() {
        String expectedUri = this.url + address + "&apiKey=" + key;
        FeatureCollection restTemplateResponse = restTemplate.getForObject(expectedUri, FeatureCollection.class);
        FeatureCollection requesterResponse = requester.request(address);
        Assert.assertFalse(requesterResponse.features().isEmpty());
        Assert.assertFalse(restTemplateResponse.features().isEmpty());
        Assert.assertNotNull(requesterResponse.features().get(0).properties().country());
        Assert.assertNotNull(requesterResponse.features().get(0).properties().addressLine1());
        Assert.assertNotNull(requesterResponse.features().get(0).properties().addressLine2());
    }




}

