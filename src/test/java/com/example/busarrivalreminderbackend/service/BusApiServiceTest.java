package com.example.busarrivalreminderbackend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@RestClientTest(BusApiService.class)
@ActiveProfiles("test")
class BusApiServiceTest {

    @Autowired
    private BusApiService busApiService;
    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void testRetrieveBusArrivalInfoFailure() {
        //Given
        String busStopId = "12345";
        String busId = "67890";
        String expectedUri = "testUritestPath?serviceKey=testServiceKey&pageNo=1&numOfRows=10&_type=json&cityCode=22&routeId=67890&nodeId=12345";

        //When
        mockServer.expect(ExpectedCount.times(3), requestTo(expectedUri))
                .andRespond(MockRestResponseCreators.withSuccess("XML text", MediaType.TEXT_XML));

        //Then
        assertThrows(RestClientException.class, () ->
                busApiService.retrieveBusArrivalInfoByBusStopIdAndBusId(busStopId, busId)
        );
    }

    @Test
    void testRetrieveBusArrivalInfoSuccess() {
        //Given
        String busStopId = "12345";
        String busId = "67890";
        String expectedUri = "testUritestPath?serviceKey=testServiceKey&pageNo=1&numOfRows=10&_type=json&cityCode=22&routeId=67890&nodeId=12345";

        //When
        mockServer.expect(ExpectedCount.times(2), requestTo(expectedUri))
                .andRespond(MockRestResponseCreators.withSuccess("XML text", MediaType.TEXT_XML));
        mockServer.expect(ExpectedCount.times(1), requestTo(expectedUri))
                .andRespond(MockRestResponseCreators.withSuccess("{\"resultCode\":\"00\",\"resultMsg\":\"정상적으로 처리되었습니다.\"}", MediaType.APPLICATION_JSON));

        //Then
        assertDoesNotThrow(() ->
                busApiService.retrieveBusArrivalInfoByBusStopIdAndBusId(busStopId, busId));
    }
}
