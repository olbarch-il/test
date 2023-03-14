package com.task.dropit.service.geo.requester;

public interface Requester<T> {
    T request(String address);
}
