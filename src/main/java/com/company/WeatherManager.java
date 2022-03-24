package com.company;

import com.company.weather.MyList;
import com.company.weather.PostDTO;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class WeatherManager {
    public static HashMap<String, String> emoji = new HashMap<>();
    public static Double LATITUDE = 41.311081d;
    public static Double LONGITUDE = 69.240562d;
    static {
        emoji.put("Clear", "🌅");
        emoji.put("Rain", "🌧️");
        emoji.put("Sun", "☀️");
        emoji.put("Snow", "❄️");
        emoji.put("Clouds", "☁️");
    }

    public static PostDTO getDto(Double lat, Double lon) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://community-open-weather-map.p.rapidapi.com/forecast/daily?lat=" +
                        lat + "&lon=" + lon + "&cnt=10"))
                .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .header("x-rapidapi-key", "853a2ee22amsh97093faee10b370p1a3687jsn5cc27505d7cc")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        Gson gson = new Gson();
        PostDTO dto = gson.fromJson(response.body(), PostDTO.class);
        System.out.println(dto.getCity().getName());

        return dto;
    }

    private static PostDTO jsonToDto(String response) {
        Gson gson = new Gson();
        PostDTO dto = gson.fromJson(response, PostDTO.class);
        System.out.println(dto.getCity().getName());
        return dto;
    }

    public static String getWeatherByIndex(Double lat, Double lon){
        PostDTO dto = null;
        try {
            dto = getDto(lat, lon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnText(0, dto);
    }

    private static String returnText(int index, PostDTO dto){
        MyList weather = dto.getList()[index];
        String result = "<b>" + dto.getCity().getName() + "</b> - " +
                longToDate(weather.getDt()) + "\n";
        result += "Ob - havo: " + getWithEmoji(weather.getWeather()[0].getMain()) + " - " +
                weather.getWeather()[0].getDescription() + "\n\n";
        result += "🏙️ <b>Kunduzi</b>: " + Manager.DTOS(Math.round(weather.getTemp().getDay()) - 273d) + " ºC\n";
        result += "🌆 <b>Kechasi</b>: " + Manager.DTOS(Math.round(weather.getTemp().getNight()) - 273d) + " ºC\n";
        return result;
    }

    public static String getForecastWeekly(Double lat, Double lon){
        String result = "";
        PostDTO dto = null;
        try {
            dto = getDto(lat, lon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 7; i++){
            result += returnText(i, dto);
            result += "\n";
        }

        return result;
    }

    private static String longToDate(Long dt){
        LocalDate date = LocalDateTime.ofEpochSecond(dt, 1000,
                ZoneOffset.UTC).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy EEEE"));
    }

    public static String getWithEmoji(String text){
        return text + " " + emoji.get(text);
    }

    public static Double getLATITUDE() {
        return LATITUDE;
    }

    public static void setLATITUDE(Double LATITUDE) {
        WeatherManager.LATITUDE = LATITUDE;
    }

    public static Double getLONGITUDE() {
        return LONGITUDE;
    }

    public static void setLONGITUDE(Double LONGITUDE) {
        WeatherManager.LONGITUDE = LONGITUDE;
    }
}
