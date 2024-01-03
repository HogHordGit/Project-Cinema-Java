package com.hoghord.cinema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmsController {
    private static final Pattern NAME_FILM_PAT = Pattern.compile("Name: (.+)");
    private static final Pattern DAY_FILM_PAT = Pattern.compile("Day: (.+)");
    private static final Pattern DATE_FILM_PAT = Pattern.compile("Date: (\\d{2}-\\d{2}-\\d{4})");
    private static final Pattern TIME_FILM_PAT = Pattern.compile("Time: (\\d{2}:\\d{2})");
    private static final Pattern TICKETS_FILM_PAT = Pattern.compile("Tickets: (\\d+)");
    private static final Pattern DURATION_FILM_PAT = Pattern.compile("Duration: (\\d+)");

    private static final Pattern PRISE_FILM_PAT = Pattern.compile("Prise: (\\d+\\.\\d+\\$)");

    private static final Pattern DESCRIPTION_FILM_PAT = Pattern.compile("Description: (.+)");

    private static final DateTimeFormatter EUROPEAN_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATER = DateTimeFormatter.ofPattern("HH:mm");

    private static final String FILE_PATH = "resourses/films.txt";

    private static DaysOfWeek todaysDay = DaysOfWeek.valueOf("MONDAY");

    ArrayList<Films> sortedFilms = new ArrayList<>();

    public ArrayList<Films> findAllFilms() {

        ArrayList<Films> films = new ArrayList<>();

        String filmName;
        DaysOfWeek filmDay;
        LocalDate filmDate;
        LocalTime filmTime;
        Short filmTickets;
        Integer filmDuration;
        String filmPrise;
        String filmDescription;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher matcher = NAME_FILM_PAT.matcher(line);

                if (matcher.matches()) {
                    filmName = matcher.group(1);

                    line = br.readLine().trim();

                    matcher = TICKETS_FILM_PAT.matcher(line);
                    if (matcher.matches()) {
                        filmTickets = Short.parseShort(matcher.group(1));

                        if (filmTickets == 0) continue;

                        line = br.readLine().trim();

                        matcher = DAY_FILM_PAT.matcher(line);
                        if (matcher.matches()) {
                            filmDay = DaysOfWeek.valueOf(matcher.group(1));

                            line = br.readLine().trim();

                            matcher = DATE_FILM_PAT.matcher(line);
                            if (matcher.matches()) {
                                filmDate = LocalDate.parse(matcher.group(1), EUROPEAN_FORMATTER);

                                line = br.readLine().trim();

                                matcher = TIME_FILM_PAT.matcher(line);
                                if (matcher.matches()) {
                                    filmTime = LocalTime.parse(matcher.group(1), TIME_FORMATER);

                                    line = br.readLine().trim();

                                    matcher = DURATION_FILM_PAT.matcher(line);
                                    if (matcher.matches()) {
                                        filmDuration = Integer.parseInt(matcher.group(1));

                                        line = br.readLine().trim();

                                        matcher = PRISE_FILM_PAT.matcher(line);
                                        if (matcher.matches()) {
                                            filmPrise = matcher.group(1);

                                            line = br.readLine().trim();

                                            matcher = DESCRIPTION_FILM_PAT.matcher(line);
                                            if (matcher.matches()) {
                                                filmDescription = matcher.group(1);

                                                films.add(new Films(filmName, filmDay, filmDate, filmTime, filmTickets, filmDuration, filmPrise, filmDescription));
                                            }
                                        }
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

    public void printFilmList(ArrayList<Films> array) {
        String soutResult;

        for (short i = 0; i < array.size(); i++) {
            System.out.println(i);
            soutResult = 1 + i + ") " + array.get(i).getName() + "\n" +
                    array.get(i).getDay() + " / " +  array.get(i).getDate() + "\n";

            if (todaysDay == array.get(i).getDay()) soutResult += "today: " + array.get(i).getTime() + "\n";
            else {
                soutResult += array.get(i).getTime() + "\n";
            }

            System.out.println(soutResult);
        }
    }

    public String findFilmByArgument(ArrayList<Films> array, String value) {
        String result = "zib";

        if(Pattern.compile("(\\d{2}-\\d{2}-\\d{4})").matcher(value).matches()) {
            findFilmByDate(array, LocalDate.parse(value, EUROPEAN_FORMATTER));
        } else if (Pattern.compile("(\\d{2}:\\d{2})").matcher(value).matches()) {
            findFilmByTime(array, LocalTime.parse(value, TIME_FORMATER));
        } else if (Pattern.compile("(\\d+)").matcher(value).matches()) {
            findFilmByTicket(array, Short.parseShort(value));
        } else if (Pattern.compile("(^\\w+$)").matcher(value.replaceAll(" ","").trim()).matches()) {
            findFilmByName(array, value);
        } else {
            System.out.println("Вы ввели некоректный формат");
            return "false";
        }

        return "true";
    }

    public void findFilmByDate(ArrayList<Films> array, LocalDate value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getDate().equals(value)) sortedFilms.add(array.get(i));
        }
        printFilmList(sortedFilms);
    }

    public void findFilmByTime(ArrayList<Films> array, LocalTime value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getTime().equals(value)) sortedFilms.add(array.get(i));
        }
        printFilmList(sortedFilms);
    }

    public void findFilmByTicket(ArrayList<Films> array, Short value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getTickets().equals(value)) sortedFilms.add(array.get(i));
        }
        printFilmList(sortedFilms);
    }

    public void findFilmByName(ArrayList<Films> array, String value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getName().equals(value)) sortedFilms.add(array.get(i));
        }
        printFilmList(sortedFilms);
    }
}
