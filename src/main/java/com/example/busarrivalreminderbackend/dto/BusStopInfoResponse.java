package com.example.busarrivalreminderbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BusStopInfoResponse {

    public Set<String> getBusIdsSet() {
        return response.body.items.item.stream()
                .map(item -> item.routeno)
                .collect(Collectors.toSet());
    }

    @JsonProperty("response")
    private Response response;

    public static class Response {
        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;
    }

    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    public static class Body {
        @JsonProperty("items")
        private Items items;

        @JsonProperty("numOfRows")
        private int numOfRows;

        @JsonProperty("pageNo")
        private int pageNo;

        @JsonProperty("totalCount")
        private int totalCount;
    }

    public static class Items {
        @JsonProperty("item")
        private List<Item> item;
    }

    public static class Item {


        @JsonProperty("endnodenm")
        private String endnodenm;

        @JsonProperty("routeid")
        private String routeid;

        @JsonProperty("routeno")
        private String routeno;

        @JsonProperty("routetp")
        private String routetp;

        @JsonProperty("startnodenm")
        private String startnodenm;

    }
}
