package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.dto.BusStopInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BusStopInfoApiService {

    private static final int DAEGU_CITY_CODE = 22;

    private final RestClient client;
    private final String busStopInfoUri;
    private final String serviceKey;

    public BusStopInfoApiService(RestClient.Builder builder,
                                 @Value("${api.busStop.info.uri}") String busStopInfoUri,
                                 @Value("${api.busStop.info.service-key}") String serviceKey) {
        this.client = builder.build();
        this.busStopInfoUri = busStopInfoUri;
        this.serviceKey = serviceKey;
    }

    public BusStopInfoResponse retrieveBusIdsAtBusStop(String busStopId) {

        String uri = UriComponentsBuilder.fromUriString(busStopInfoUri)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("_type", "json")
                .queryParam("cityCode", DAEGU_CITY_CODE)
                .queryParam("nodeid", busStopId)
                .build(false)
                .toUriString();
        System.out.println(uri);

        return client.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(BusStopInfoResponse.class);
    }
}
