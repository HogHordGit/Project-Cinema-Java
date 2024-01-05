package com.hoghord.cinema;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
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

    private static final String FILE_PATH = "out/production/Cinema/films.txt";

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

    public void buyTicketAfterSearching(Films films) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.contains("Name: " + films.getName())) {
                    content.append(line).append("\n");
                    line = br.readLine().trim();

                    if (line.matches("Tickets: (\\d+)")) {
                        int num = Integer.parseInt(line.replaceAll("Tickets: ", "").trim()) - 1;

                        line = line.replaceFirst("Tickets: \\d+", "Tickets: " + num);
                    }
                }
                content.append(line).append("\n");
            }

            // Записываем новое содержимое в файл
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                bw.write(content.toString());
                System.out.println("Билет успешно куплен!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printFilmList(ArrayList<Films> array) {
        String soutResult;

        for (short i = 0; i < array.size(); i++) {
            soutResult = 1 + i + ") " + array.get(i).getName() + "\n" +
                    array.get(i).getDay() + " / " +  array.get(i).getDate() + "\n";

            if (todaysDay == array.get(i).getDay()) soutResult += "today: " + array.get(i).getTime() + "\n";
            else {
                soutResult += array.get(i).getTime() + "\n";
            }

            System.out.println(soutResult);
        }
    }

    public void printInfoAboutChosenFilm(Films films) {
        System.out.println("Name: " + films.getName() + "\n"
                + films.getDescription() + "\n" + "\n"
                + "Продолжительность: " + films.getDuration() + "\n" + "\n"
                + films.getDate() + "/" + films.getTime() + "\n"
                + films.getDay() + "\n" + "\n"
                + "Доступные билеты: " + films.getTickets() + "\n"
                + "Цена билета: " + films.getPrise()
        );
    }

    public ArrayList<Films> findFilmByArgument(ArrayList<Films> array, String value) {
        if(Pattern.compile("(\\d{2}-\\d{2}-\\d{4})").matcher(value).matches()) {
            return findFilmByDate(array, LocalDate.parse(value, EUROPEAN_FORMATTER));
        } else if (Pattern.compile("(\\d{2}:\\d{2})").matcher(value).matches()) {
            return findFilmByTime(array, LocalTime.parse(value, TIME_FORMATER));
        } else if (Pattern.compile("(\\d+)").matcher(value).matches()) {
            return findFilmByTicket(array, Integer.parseInt(value));
        } else if (Pattern.compile("(^\\w+$)").matcher(value.replaceAll(" ","").trim()).matches()) {
            return findFilmByName(array, value);
        } else {
            System.out.println("Вы ввели некоректный формат");
            return sortedFilms;
        }
    }

    public ArrayList<Films> findFilmByDate(ArrayList<Films> array, LocalDate value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getDate().equals(value)) sortedFilms.add(array.get(i));
        }
        if (sortedFilms.isEmpty()) {
            System.out.println("Фильм/фильмы не были найдена по вашему запросу.");
            return sortedFilms;
        } else {
            printFilmList(sortedFilms);
            return sortedFilms;
        }
    }

    public ArrayList<Films> findFilmByTime(ArrayList<Films> array, LocalTime value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getTime().equals(value)) sortedFilms.add(array.get(i));
        }
        if (sortedFilms.isEmpty()) {
            System.out.println("Фильм/фильмы не были найдена по вашему запросу.");
            return sortedFilms;
        } else {
            printFilmList(sortedFilms);
            return sortedFilms;
        }
    }

    public ArrayList<Films> findFilmByTicket(ArrayList<Films> array, Integer value) {
        for (short i = 0; i < array.size(); i++) {
            if (value <= array.get(i).getTickets()) sortedFilms.add(array.get(i));
        }
        if (sortedFilms.isEmpty()) {
            System.out.println("Фильм/фильмы не были найдены по вашему запросу.");
            return sortedFilms;
        } else {
            printFilmList(sortedFilms);
            return sortedFilms;
        }
    }

    public ArrayList<Films> findFilmByName(ArrayList<Films> array, String value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getName().trim().equalsIgnoreCase(value.trim())) sortedFilms.add(array.get(i));
        }
        if (sortedFilms.isEmpty()) {
            System.out.println("Фильм/фильмы не были найдена по вашему запросу.");
            return sortedFilms;
        } else {
            printFilmList(sortedFilms);
            return sortedFilms;
        }
    }
//    Name: Hot boobs forever
//    Tickets: 10
//    Day: MONDAY
//    Date: 27-12-2023
//    Time: 15:00
//    Duration: 90
//    Prise: 4.99$
//    Description: Good film with excellent plot

//    123, 123, FRIDAY, 12-12-1221, 12:21, 123, 123$, 123

    public void addFilmToListByAdmin(String film) {
        Pattern[] patArray = {Pattern.compile("(.+)"), Pattern.compile("(\\d+)"),
                Pattern.compile("(.+)"), Pattern.compile("(\\d{2}-\\d{2}-\\d{4})"),
                Pattern.compile("(\\d{2}:\\d{2})"), Pattern.compile("(\\d+)"),
                Pattern.compile("((\\d+\\.\\d+\\$)|(\\d+)\\$)"), Pattern.compile("(.+)")};
        String[] infoStrArray = {"Name: ", "Tickets: ", "Day: ", "Date: ", "Time: ", "Duration: ", "Prise: ", "Description: "};
        String[] array = film.split(", ");

        String result = "";

        try(FileWriter fw = new FileWriter(FILE_PATH, true);
        BufferedWriter bw = new BufferedWriter(fw)) {
            if (patArray.length == array.length) {
                Matcher matcher;

                for (int i = 0; i < array.length; i++) {
                    matcher = patArray[i].matcher(array[i]);

                    if (matcher.matches()) {
                        result += infoStrArray[i] + array[i] + "\n";
                    }
                }

                bw.newLine();
                bw.write("---");
                bw.newLine();

                bw.write(result);

                bw.write("---");
                System.out.println("Фильм успешно добавлен");
            } else {
                System.out.println("Админ ты конч, заново!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
