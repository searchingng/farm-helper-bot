package com.company.weather;

public class MyList {
    private Long dt;
    private Long sunrise;
    private Long sunset;
    private Temp temp;
    private Temp feels_like;
    private Integer pressure;
    private Byte humidity;
    private Weather[] weather;

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Temp getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Temp feels_like) {
        this.feels_like = feels_like;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Byte getHumidity() {
        return humidity;
    }

    public void setHumidity(Byte humidity) {
        this.humidity = humidity;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
}
