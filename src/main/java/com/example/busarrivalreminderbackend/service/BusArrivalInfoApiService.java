package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.dto.BusArrivalInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BusArrivalInfoApiService {

    private static final int DAEGU_CITY_CODE = 22;

    private final RestClient client;
    private final String busArrivalInfoUri;
    private final String serviceKey;

    public BusArrivalInfoApiService(RestClient.Builder builder,
                                    @Value("${api.busArrival.info.uri}") String busArrivalInfoUri,
                                    @Value("${api.busArrival.info.service-key}") String serviceKey) {
        this.client = builder.build();
        this.busArrivalInfoUri = busArrivalInfoUri;
        this.serviceKey = serviceKey;
    }

    public BusArrivalInfoResponse  retrieveBusArrivalInfoByBusStopIdAndBusId(String busStopId, String busId) {
        String uri = UriComponentsBuilder.fromUriString(busArrivalInfoUri)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("_type", "json")
                .queryParam("cityCode", DAEGU_CITY_CODE)
                .queryParam("nodeId", busStopId)
                .queryParam("routeId", busId)
                .build(false)
                .toUriString();

        return client.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(BusArrivalInfoResponse.class);
    }
}
