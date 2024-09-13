package com.example.busarrivalreminderbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PushNotificationApiService {

    private final RestClient client;
    private final String pushNotificationApiBaseUri;
    private final String apiToken;

    public PushNotificationApiService(RestClient.Builder builder,
                                      @Value("${api.pushNotification.baseUri}") String pushNotificationApiBaseUri,
                                      @Value("${api.pushNotification.token}") String apiToken) {
        this.client = builder.build();
        this.pushNotificationApiBaseUri = pushNotificationApiBaseUri;
        this.apiToken = apiToken;
    }

    public void requestPushNotification(String userId) {

        client.post()
                .uri(pushNotificationApiBaseUri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiToken)
                .body(new PushNotificationRequest("userId", userId, "출발하세요!", "버스가 지정하신 정류장에 도착했습니다"));

    }

    static class PushNotificationRequest {
        String targetType;
        String targetIds;
        String title;
        String body;

        public PushNotificationRequest(String targetType, String targetIds, String title, String body) {
            this.targetType = targetType;
            this.targetIds = targetIds;
            this.title = title;
            this.body = body;
        }
    }
}
