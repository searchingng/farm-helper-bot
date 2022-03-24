package com.company.weather;

import com.company.weather.City;

public class PostDTO {
    private City city;
    private MyList[] list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public MyList[] getList() {
        return list;
    }

    public void setList(MyList[] list) {
        this.list = list;
    }
}
