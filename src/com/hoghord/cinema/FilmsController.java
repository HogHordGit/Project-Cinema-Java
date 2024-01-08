package com.hoghord.cinema;

import java.io.*;
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
    private static final Pattern TIME_FILM_PAT = Pattern.compile("Time: (\\d{2}:\\d{2})");
    private static final Pattern TICKETS_FILM_PAT = Pattern.compile("Tickets: (\\d+)");
    private static final Pattern DURATION_FILM_PAT = Pattern.compile("Duration: (\\d+)");

    private static final Pattern PRISE_FILM_PAT = Pattern.compile("Price: (\\d+\\.\\d+\\$)");

    private static final Pattern DESCRIPTION_FILM_PAT = Pattern.compile("Description: (.+)");

    private static final DateTimeFormatter EUROPEAN_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATER = DateTimeFormatter.ofPattern("HH:mm");

    private static final String FILE_PATH = "out/production/Cinema/films.txt";

    private static DaysOfWeek todaysDay = DaysOfWeek.valueOf("MONDAY");

    ArrayList<Films> sortedFilms = new ArrayList<>();

    final String[] infoStrArray = {"Name: ", "Tickets: ", "Day: ", "Date: ", "Time: ", "Duration: ", "Price: ", "Description: "};

    Integer gay = 0;

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

                        if (filmTickets == 0) {
                            gay = 10;
                            continue;
                        };

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

    public void changeValueInFile(Films films, int action, String value, ArrayList<Films> check, String status) {
        int checkOnSpace = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = br.readLine()) != null) {
                checkOnSpace++;
                if (line.contains("Name: " + films.getName())) {

                    for (int i = 0; i < action; i++) {
                        content.append(line).append("\n");

                        line = br.readLine().trim();
                    }

                    if (line.matches(infoStrArray[action] + ".+")) {
                        try {
                            int checkNum = Integer.parseInt(value);

                            int num = status.equals("user") ? Integer.parseInt(line.replaceAll(infoStrArray[action], "").trim()) - checkNum : checkNum;

                            line = line.replaceFirst(infoStrArray[action] + ".+", infoStrArray[action] + num);
                        } catch (NumberFormatException e) {
//                            System.out.println("line");
//                            System.out.println(infoStrArray[numArray]);
//                            System.out.println(numArray);
                            line = line.replaceFirst(infoStrArray[action] + ".+", infoStrArray[action] + value);
                        }
                    }
                }

                content.append(line).append("\n");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                bw.write(content.toString());

                System.out.println(status.equals("user") ? "Ticket purchased successfully!" : "Data changed successfully!");
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
                + "Duration: " + films.getDuration() + " minutes" + "\n" + "\n"
                + films.getDate() + "/" + films.getTime() + "\n"
                + films.getDay() + "\n" + "\n"
                + "Available tickets: " + films.getTickets() + "\n"
                + "Ticket price: " + films.getPrise()
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
            System.out.println("You entered an incorrect format!");
            return sortedFilms;
        }
    }

    public ArrayList<Films> findFilmByDate(ArrayList<Films> array, LocalDate value) {
        for (short i = 0; i < array.size(); i++) {
            if (array.get(i).getDate().equals(value)) sortedFilms.add(array.get(i));
        }
        if (sortedFilms.isEmpty()) {
            System.out.println("The film/films were not found for your request.");
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
            System.out.println("The film/films were not found for your request.");
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
            System.out.println("The film/films were not found for your request.");
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
            System.out.println("The film/films were not found for your request.");
            return sortedFilms;
        } else {
            printFilmList(sortedFilms);
            return sortedFilms;
        }
    }

    public void addFilmToListByAdmin(String film) {
        Pattern[] patArray = {Pattern.compile("(.+)"), Pattern.compile("(\\d+)"),
                Pattern.compile("(.+)"), Pattern.compile("(\\d{2}-\\d{2}-\\d{4})"),
                Pattern.compile("(\\d{2}:\\d{2})"), Pattern.compile("(\\d+)"),
                Pattern.compile("((\\d+\\.\\d+\\$)|(\\d+)\\$)"), Pattern.compile("(.+)")};
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
                System.out.println("Film added successfully");
            } else {
                System.out.println("You entered an incorrect format!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}