package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.dto.BusArrivalInfoResponse;
import com.example.busarrivalreminderbackend.dto.BusStopInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class BusApiService {

    private static final int DAEGU_CITY_CODE = 22;
    private static final int MAX_RETRY_COUNT = 3;

    private final RestClient client;
    private final String busApiBaseUri;
    private final String serviceKey;
    private final String busArrivalInfoPath;
    private final String busStopInfoPath;

    public BusApiService(RestClient.Builder builder,
                         @Value("${api.bus.baseUri}") String busApiBaseUri,
                         @Value("${api.bus.serviceKey}") String serviceKey,
                         @Value("${api.busArrivalInfo.path}") String busArrivalInfoPath,
                         @Value("${api.busStopInfo.path}") String busStopInfoPath) {
        this.client = builder.build();
        this.busApiBaseUri = busApiBaseUri;
        this.serviceKey = serviceKey;
        this.busArrivalInfoPath = busArrivalInfoPath;
        this.busStopInfoPath = busStopInfoPath;
    }

    public BusArrivalInfoResponse retrieveBusArrivalInfoByBusStopIdAndBusId(String busStopId, String busId) {
        String uri = buildUri(busArrivalInfoPath, Map.of(
                "nodeId", busStopId,
                "routeId", busId
        ));

        return makeApiCall(uri, BusArrivalInfoResponse.class);
    }

    public BusStopInfoResponse retrieveBusIdsAtBusStop(String busStopId) {
        String uri = buildUri(busStopInfoPath, Map.of(
                "nodeid", busStopId
        ));

        return makeApiCall(uri, BusStopInfoResponse.class);
    }

    private String buildUri(String path, Map<String, Object> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(busApiBaseUri + path)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("_type", "json")
                .queryParam("cityCode", DAEGU_CITY_CODE);

        queryParams.forEach(uriBuilder::queryParam);

        return uriBuilder.build(false).toUriString();
    }

    private <T> T makeApiCall(String uri, Class<T> responseType) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                return client.get()
                        .uri(uri)
                        .exchange((request, response) -> {
                            if (response.getHeaders().getContentType().isCompatibleWith(MediaType.TEXT_XML)) {
                                throw new ResourceAccessException("Invalid content type: TEXT_XML");
                            }
                            if (response.getStatusCode().is4xxClientError()) {
                                throw new HttpClientErrorException(response.getStatusCode());
                            }
                            if (response.getStatusCode().is5xxServerError()) {
                                throw new HttpServerErrorException(response.getStatusCode());
                            }
                            return response.bodyTo(responseType);
                        });
            } catch (ResourceAccessException e) {
                ++retryCount;
            }
        }
        throw new RestClientException("External bus API is unavailable after " + MAX_RETRY_COUNT + " retries.");
    }
}
