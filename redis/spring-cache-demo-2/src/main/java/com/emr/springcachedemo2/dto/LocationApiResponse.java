package com.emr.springcachedemo2.dto;

import java.util.List;

public record LocationApiResponse(
        String status,
        List<CityData> data
) {
    public record CityData(
            int id,
            String name,
            int area,
            int population,
            int altitude,
            List<Integer> areaCode,
            boolean isMetropolitan,
            Coordinates coordinates,
            Maps maps,
            Region region,
            List<District> districts
    ) {
        public record Coordinates(
                double latitude,
                double longitude
        ) {
        }

        public record Maps(
                String googleMaps,
                String openStreetMap
        ) {
        }

        public record Region(
                String en,
                String tr
        ) {
        }

        public record District(
                int id,
                String name,
                int area,
                int population
        ) {
        }
    }
}


