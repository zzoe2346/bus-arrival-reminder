package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.dto.BusStopInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BusStopInfoApiService {

    private static final int DAEGU_CITY_CODE = 22;
    private static final int MAX_RETRY_COUNT = 3;

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

        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                return client.get()
                        .uri(uri)
                        .exchange((request, response) -> {
                            if (response.getHeaders().getContentType().isCompatibleWith(MediaType.TEXT_XML)) {
                                throw new ResourceAccessException("");
                            }
                            if (response.getStatusCode().is4xxClientError()) {
                                throw new HttpClientErrorException(response.getStatusCode());
                            }
                            if (response.getStatusCode().is5xxServerError()) {
                                throw new HttpServerErrorException(response.getStatusCode());
                            }
                            return response.bodyTo(BusStopInfoResponse.class);
                        });
            } catch (ResourceAccessException e) {
                ++retryCount;
            }
        }
        throw new RestClientException("외부 버스API에 이상이 있습니다.");
    }
}
