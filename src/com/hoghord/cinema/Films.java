package com.hoghord.cinema;

import java.time.LocalDate;
import java.time.LocalTime;

public class Films {
    private String name;
    private DaysOfWeek day;
    private LocalDate date;
    private LocalTime time;
    private Short tickets;
    private Integer duration;

    private String prise;

    private String description;

    public Films(String name, DaysOfWeek day, LocalDate date, LocalTime time, Short tickets, Integer duration, String prise, String description) {
        this.name = name;
        this.day = day;
        this.date = date;
        this.time = time;
        this.tickets = tickets;
        this.duration = duration;
        this.prise = prise;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public DaysOfWeek getDay() {
        return day;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Short getTickets() {
        return tickets;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getPrise() {
        return prise;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Films{" +
                "name='" + name + '\'' +
                ", day=" + day +
                ", date=" + date +
                ", time=" + time +
                ", tickets=" + tickets +
                ", duration=" + duration +
                ", prise=" + prise +
                ", description=" + description +
                '}';
    }
}