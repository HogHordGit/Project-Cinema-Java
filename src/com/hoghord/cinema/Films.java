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

    public Films(String name, DaysOfWeek day, LocalDate date, LocalTime time, Short tickets, Integer duration) {
        this.name = name;
        this.day = day;
        this.date = date;
        this.time = time;
        this.tickets = tickets;
        this.duration = duration;
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

    @Override
    public String toString() {
        return "Films{" +
                "name='" + name + '\'' +
                ", day=" + day +
                ", date=" + date +
                ", time=" + time +
                ", tickets=" + tickets +
                ", duration=" + duration +
                '}';
    }
}

//Name: Hot boobs forever
//Day: Monday
//Date: 27-12-2023
//Time: 15:00:00, 18:00:00
//Tickets: 10
//Duration: 90
