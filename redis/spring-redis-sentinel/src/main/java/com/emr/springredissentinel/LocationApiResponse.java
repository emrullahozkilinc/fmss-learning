package com.emr.springredissentinel;


import java.io.Serializable;
import java.util.List;

public record LocationApiResponse(
        String status,
        List<CityData> data
) implements Serializable {
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
    ) implements Serializable{
        public record Coordinates(
                double latitude,
                double longitude
        ) implements Serializable{
        }

        public record Maps(
                String googleMaps,
                String openStreetMap
        ) implements Serializable{
        }

        public record Region(
                String en,
                String tr
        ) implements Serializable{
        }

        public record District(
                int id,
                String name,
                int area,
                int population
        ) implements Serializable{
        }
    }
}
