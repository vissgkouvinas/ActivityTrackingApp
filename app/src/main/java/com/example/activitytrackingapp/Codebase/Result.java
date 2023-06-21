package Codebase;

import java.io.Serializable;

public class Result implements Serializable {
    private final float distance;
    private final float velocity;
    private final float elevation;
    private final float time;
    private final String name;
    private final String date;
    private final String timeframe;

    public Result(float distance, float velocity,float elevation,float time){
        this.distance = distance;
        this.velocity = velocity;
        this.elevation = elevation;
        this.time = time;
        this.name = "";
        this.date = "";
        this.timeframe = "";
    }
    public Result(float distance, float velocity,float elevation,float time,String name,String date, String timeframe){
        this.distance = distance;
        this.velocity = velocity;
        this.elevation = elevation;
        this.time = time;
        this.name = name;
        this.date = date;
        this.timeframe = timeframe;
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
    public String getName(){return name;}
    public String getDate(){return date;}
    public String getTimeframe(){ return timeframe;}
    @Override
    public String toString() {
        return String.format("===========================\nResults: \n Distance Overall: %5.2f\n Velocity Average: %5.2f\n Elevation Overall: %5.2f\n Time Overall: %5.2f\n===========================\n",distance,velocity,elevation,time);
    }
}
