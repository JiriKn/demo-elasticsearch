package com.jk.elastic.model;

public class WeatherData
{
    private String stationName;
    private String date;
    private float avgTemp;

    public WeatherData(String stationName, String date, float avgTemp) {
        this.stationName = stationName;
        this.date = date;
        this.avgTemp = avgTemp;
    }

    public WeatherData() {}

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAvgTemp(float avgTemp) {
        this.avgTemp = avgTemp;
    }

    public String getDate() {
        return date;
    }

    public float getAvgTemp() {
        return avgTemp;
    }


}
