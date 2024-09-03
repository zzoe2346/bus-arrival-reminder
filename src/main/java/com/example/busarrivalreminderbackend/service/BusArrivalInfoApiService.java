package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.dto.BusArrivalInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BusArrivalInfoApiService {

    private static final int DAEGU_CITY_CODE = 22;
    private static final int MAX_RETRY_COUNT = 3;

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

    public BusArrivalInfoResponse retrieveBusArrivalInfoByBusStopIdAndBusId(String busStopId, String busId) {
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
                            return response.bodyTo(BusArrivalInfoResponse.class);
                        });
            } catch (ResourceAccessException e) {
                ++retryCount;
            }
        }
        throw new RestClientException("외부 버스API에 이상이 있습니다.");
    }
}
