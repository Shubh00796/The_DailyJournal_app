package com.sprinboot2025.demo.utility;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SeriesResult {
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        private int id;

        @JsonProperty("series_id")
        private int seriesId;

        private String venue;
        private String date;
        private String status;
        private String result;

        @JsonProperty("match_title")
        private String matchTitle;

        @JsonProperty("match_subtitle")
        private String matchSubtitle;

        private Team home;
        private Team away;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(int seriesId) {
            this.seriesId = seriesId;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMatchTitle() {
            return matchTitle;
        }

        public void setMatchTitle(String matchTitle) {
            this.matchTitle = matchTitle;
        }

        public String getMatchSubtitle() {
            return matchSubtitle;
        }

        public void setMatchSubtitle(String matchSubtitle) {
            this.matchSubtitle = matchSubtitle;
        }

        public Team getHome() {
            return home;
        }

        public void setHome(Team home) {
            this.home = home;
        }

        public Team getAway() {
            return away;
        }

        public void setAway(Team away) {
            this.away = away;
        }
    }

    public static class Team {
        private int id;
        private String name;
        private String code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
