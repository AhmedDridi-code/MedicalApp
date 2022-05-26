package com.example.medical;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    private String title;
    private String date;
    private String time;

    public Appointment() {
    }

    public Appointment(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}
