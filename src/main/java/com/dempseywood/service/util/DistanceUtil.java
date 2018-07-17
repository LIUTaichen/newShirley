package com.dempseywood.service.util;

import com.vividsolutions.jts.geom.Coordinate;

public class DistanceUtil {

    public static final double RADIUS_OF_EARTH_IN_KM = 6372.8 * 1000; // In meters

    public static Double getDistance(double lat1, double lon1, double lat2, double lon2){
       return haversine(lat1, lon1,  lat2, lon2);
    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return RADIUS_OF_EARTH_IN_KM * c;
    }
}
