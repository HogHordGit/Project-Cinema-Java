package com.hoghord.cinema;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmsController {
    private static final Pattern NAME_FILM_PAT = Pattern.compile("Name: (.+)");
    private static final Pattern DAY_FILM_PAT = Pattern.compile("Day: (.+)");
    private static final Pattern DATE_FILM_PAT = Pattern.compile("Date: (\\d{2}-\\d{2}-\\d{4})");
    private static final Pattern TIME_FILM_PAT = Pattern.compile("Time: (\\d{2}:\\d{2}:\\d{2})");
    private static final Pattern TICKETS_FILM_PAT = Pattern.compile("Tickets: (\\d+)");
    private static final Pattern DURATION_FILM_PAT = Pattern.compile("Duration: (\\d+)");

    private static final DateTimeFormatter EUROPEAN_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATER = DateTimeFormatter.ofPattern("HH:mm");

    private static final String FILE_PATH = "resourses/films.txt";

    public ArrayList<Films> findAllFilms() {

        ArrayList<Films> films = new ArrayList<>();

        String filmName;
        DaysOfWeek filmDay;
        LocalDate filmDate;
        LocalTime filmTime;
        Short filmTickets;
        Integer filmDuration;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher matcher = NAME_FILM_PAT.matcher(line);

                if (matcher.matches()) {
                    filmName = matcher.group(1);

                    matcher = DAY_FILM_PAT.matcher(line);
                    if (matcher.matches()) {
                        filmDay = DaysOfWeek.valueOf(matcher.group(1));

                        matcher = DATE_FILM_PAT.matcher(line);
                        if (matcher.matches()) {
                            filmDate = LocalDate.parse(matcher.group(1), EUROPEAN_FORMATTER);

                            matcher = TIME_FILM_PAT.matcher(line);
                            if (matcher.matches()) {
                                filmTime = LocalTime.parse(matcher.group(1), TIME_FORMATER);

                                matcher = TICKETS_FILM_PAT.matcher(line);
                                if (matcher.matches()) {
                                    filmTickets = Short.parseShort(matcher.group(1));

                                    matcher = DURATION_FILM_PAT.matcher(line);
                                    if (matcher.matches()) {
                                        filmDuration = Integer.parseInt(matcher.group(1));

                                        films.add(new Films(filmName, filmDay, filmDate, filmTime, filmTickets, filmDuration));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return films;
    }
}
