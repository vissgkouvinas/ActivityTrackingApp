package Codebase;
public class Codebase {
    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static float distance(float lat1, float lat2, float lon1,
                                 float lon2, float height) {

        final int R = 6371; // Radius of the earth

        float latDistance = (float) Math.toRadians(lat2 - lat1);
        float lonDistance = (float) Math.toRadians(lon2 - lon1);
        float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        float distance = R * c * 1000; // convert to meters

        distance = (float) (Math.pow(distance, 2) + Math.pow(height, 2));

        return (float) Math.sqrt(distance);
    }

    /**
     * This is the Master's reducer method.
     * Used to gather all results in one Result Object.
     * That object will be returned to the Client.
     *
     * results[] Containing all sub-results
     * @returns Final Result of calculations(Contains Distance,  Velocity, Elevation, Time
     */
    public static Result reducer(Result[] results){
        float distance = 0.0F;
        float velocity = 0.0F;
        float elevation = 0.0F;
        float time = 0.0F;
        for(Result currentResult:results){
            distance += currentResult.getDistance();
            velocity += currentResult.getVelocity();
            elevation += currentResult.getElevation();
            time += currentResult.getTime();
        }
        velocity = velocity / results.length;
        return new Result(distance,velocity,elevation,time);
    }
}
