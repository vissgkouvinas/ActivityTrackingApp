package Codebase;

import java.io.Serializable;

public class Result implements Serializable {
    private final float distance;
    private final float velocity;
    private final float elevation;
    private final float time;

    public Result(float distance, float velocity,float elevation,float time){
        this.distance = distance;
        this.velocity = velocity;
        this.elevation = elevation;
        this.time = time;
    }

    public float getDistance() {
        return distance;
    }

    public float getElevation() {
        return elevation;
    }

    public float getVelocity() {
        return velocity;
    }

    public float getTime() {
        return time;
    }
    @Override
    public String toString() {
        return String.format("===========================\nResults: \n Distance Overall: %5.2f\n Velocity Average: %5.2f\n Elevation Overall: %5.2f\n Time Overall: %5.2f\n===========================\n",distance,velocity,elevation,time);
    }
}
