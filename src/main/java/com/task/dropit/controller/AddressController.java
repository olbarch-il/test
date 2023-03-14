package com.task.dropit.controller;

import com.task.dropit.model.address.Address;
import com.task.dropit.service.geo.AddressResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressResolver addressResolver;

    @PostMapping("/resolve-address")
    public Address resolveAddress(@RequestBody Map<String, String> requestBody) {
        String searchTerm = requestBody.get("searchTerm");
        return addressResolver.resolve(searchTerm);
    }


}
