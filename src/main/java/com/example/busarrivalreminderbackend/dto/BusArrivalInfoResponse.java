package com.example.busarrivalreminderbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BusArrivalInfoResponse {

    public int getMinLeftTime() {
        return response.body
                .items
                .item
                .stream()
                .mapToInt(Item::getArrtime)
                .min()
                .orElse(-1);
    }

    public int getMinLeftBusStopCount() {
        return response.body
                .items
                .item
                .stream()
                .mapToInt(Item::getArrprevstationcnt)
                .min()
                .orElse(-1);
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
        @JsonProperty("arrprevstationcnt")
        private int arrprevstationcnt;

        @JsonProperty("arrtime")
        private int arrtime;

        @JsonProperty("nodeid")
        private String nodeid;

        @JsonProperty("nodenm")
        private String nodenm;

        @JsonProperty("routeid")
        private String routeid;

        @JsonProperty("routeno")
        private String routeno;


        public int getArrprevstationcnt() {
            return arrprevstationcnt;
        }

        public int getArrtime() {
            return arrtime;
        }
    }
}
