package com.hoghord.cinema;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        FilmsController gay = new FilmsController();

        ArrayList<Films> gay2 = new ArrayList<>();

        gay2 = gay.findAllFilms();

        System.out.println(gay2);

    }

}
