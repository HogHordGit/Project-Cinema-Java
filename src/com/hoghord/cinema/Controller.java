package com.hoghord.cinema;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    static FilmsController filmsController = new FilmsController();
    static View view = new View();
    static User user;
    static UserController userReader = new UserController();
    Scanner scanner = new Scanner(System.in);

    ArrayList<Films> filmsList;

    public void authorisedUser(User user) {
        if (user.getStatus().equals("admin")) {
            view.adminInterface();
            actionWindowAfterLogIn();

        } else if (user.getStatus().equals("user")) {
            view.userInterface();
            actionWindowAfterLogIn();
        }
    }

    public void showFilmsList() {
        view.filmListHeader();
        filmsList = filmsController.findAllFilms();
        filmsController.printFilmList(filmsList);
        view.authorisedShowAfterChoseFilm();
        actionAfterShowFilmList();
    }

    public void editShowFilmsListForAdmin() {
        view.filmListHeader();
        filmsList = filmsController.findAllFilms();
        filmsController.printFilmList(filmsList);
        view.authorisedShowAfterChoseFilmForAdmin();
        actionAfterShowFilmList();
    }

    public void buyTicketBySelectedFilm(Films films) {
        view.askToBuyATicket();

        switch (scanner.nextLine()) {
            case "yes": {
                filmsController.changeValueInFile(films, 1, "1", filmsList, user.getStatus());
                authorisedUser(user);
                break;
            }
            case "no": {
                authorisedUser(user);
                break;
            }
            default: {
                System.out.println("You've entered wrong action!");
                buyTicketBySelectedFilm(films);
                break;
            }
        }
    }

    public void selectFilmByNumSortedFilm() {
        view.selectFilmAfterSorting();

        String action = scanner.nextLine();

        if (action.equals("/back")) {
            showFilmsList();
        } else if (Integer.parseInt(action) > (filmsList.size() + 1) || Integer.parseInt(action) < 0) {
            System.out.println("You've entered wrong action!");
            selectFilmByNumSortedFilm();
        } else {
            filmsController.printInfoAboutChosenFilm(filmsList.get(Integer.parseInt(action) - 1));
            buyTicketBySelectedFilm(filmsList.get(Integer.parseInt(action) - 1));
        }
    }

    public void selectFilmByNumAllFilms(Integer num, String status) {
        if (num > (filmsList.size() + 1) || num < 0) {
            System.out.println("You've entered wrong action!");
            selectFilmByNumSortedFilm();
        } else if (status.equals("user")) {
            filmsController.printInfoAboutChosenFilm(filmsList.get(num - 1));
            buyTicketBySelectedFilm(filmsList.get(num - 1));
        } else if (status.equals("admin")) {
            filmsController.printInfoAboutChosenFilm(filmsList.get(num - 1));
            view.showToAdminCorrectFormatToChangeLine();

            String adminAction = scanner.nextLine();
            int adminActionAnswer = selectFilmByNumAllFilmsChecker(adminAction, "action");
            if (adminActionAnswer == -1) {
                System.out.println("Wrong action entered!");
                selectFilmByNumAllFilms(num, status);
            }

            view.writeAdminAnswerEditFilm();

            String value = scanner.nextLine();
            int adminValueAnswer = selectFilmByNumAllFilmsChecker(value, "value");
            if (adminValueAnswer == -1) {
                System.out.println("Wrong data entered, check your value!");
                selectFilmByNumAllFilms(num, status);
            }

            System.out.println(value);

            filmsController.changeValueInFile(filmsList.get(num - 1), adminActionAnswer, value, filmsList, user.getStatus());

            editShowFilmsListForAdmin();
        }
    }

    public int selectFilmByNumAllFilmsChecker(String value, String action) {
        Pattern[] PATT_TO_EDIT_FIELD = {Pattern.compile("[nN]ame"), Pattern.compile("[tT]icket(s)?"),
                Pattern.compile("[dD]ay"), Pattern.compile("[dD]ate"), Pattern.compile("[tT]ime"),
                Pattern.compile("[dD]urati?o?n?"), Pattern.compile("[pP]rice"), Pattern.compile("[dD]escr?i?p?t?i?o?n?")};
        Pattern[] PATT_ARRAY = {Pattern.compile("(.+)"), Pattern.compile("(\\d+)"),
                Pattern.compile("(.+)"), Pattern.compile("(\\d{2}-\\d{2}-\\d{4})"),
                Pattern.compile("(\\d{2}:\\d{2})"), Pattern.compile("(\\d+)"),
                Pattern.compile("((\\d+\\.\\d+\\$)|(\\d+)\\$)"), Pattern.compile("(.+)")};

        int numArray = -1;

        if (action.equals("action")) {
            for (int i = 0; i < PATT_TO_EDIT_FIELD.length; i++) {
                if (PATT_TO_EDIT_FIELD[i].matcher(value).matches()) {
                    numArray = i;
                    break;
                }
            }
        }
        if (action.equals("value")) {
            for (int i = 0; i < PATT_ARRAY.length; i++) {
                if (PATT_ARRAY[i].matcher(value).matches()) {
                    numArray = 1;
                    break;
                }
            }
        }

        return numArray;
    }

    public void actionAfterShowFilmList() {
        if(user != null) {
            String action = scanner.nextLine();

            if (action.equals("/find")) {
                view.showActionsForFindingFilm();

                if ((filmsList = filmsController.findFilmByArgument(filmsList, scanner.nextLine())).isEmpty()) actionAfterShowFilmList();

                selectFilmByNumSortedFilm();

            } else if (action.equals("/back")) {
                authorisedUser(user);
            } else {
                if (action.isEmpty()) {
                    System.out.println("You've entered wrong action!");
                    actionAfterShowFilmList();
                }

                selectFilmByNumAllFilms(Integer.parseInt(action), user.getStatus());
            }
        } else {
            view.noAuthorisedShowAfterChoseFilm();

            switch (scanner.nextLine()) {
                case "1": {
                    logIn();
                    break;
                }
                case "2": {
                    break;
                }
                default: {
                    System.out.println("You've entered wrong action!");
                    actionAfterShowFilmList();
                    break;
                }
            }
        }
    }

    public void actionWindowAfterLogInForAdmin() {
        view.selectActionAfterChoseEdit();

        String action = scanner.nextLine();

        switch (action) {
            case "1": {
                editShowFilmsListForAdmin();
                break;
            }
            case "2": {
                view.showToAdminCorrectFormatToAddFilm();

                filmsController.addFilmToListByAdmin(scanner.nextLine());
                actionWindowAfterLogInForAdmin();
                break;
            }
            case "3": {
                authorisedUser(user);
                break;
            }
        }
    }

    public void actionWindowAfterLogIn() {
        switch (user.getStatus()) {
            case "admin": {
                String action = scanner.nextLine();

                switch (action.trim()) {
                    case "1": {
                        showFilmsList();
                        break;
                    }
                    case "2": {
                        actionWindowAfterLogInForAdmin();
                        break;
                    }
                    case "3": {
                        user = null;
                        startApp();
                        break;
                    }
                    case "4": {
                        break;
                    }
                    default: {
                        System.out.println("You've entered wrong action!");
                        view.adminInterface();
                        actionWindowAfterLogIn();
                        break;
                    }
                }
                break;
            }
            case "user": {
                String action = scanner.nextLine();

                switch (action.trim()) {
                    case "1": {
                        showFilmsList();
                        break;
                    }
                    case "2": {
                        user = null;
                        startApp();
                        break;
                    }
                    case "3": {
                        break;
                    }
                    default: {
                        System.out.println("You've entered wrong action!");
                        view.userInterface();
                        actionWindowAfterLogIn();
                        break;
                    }
                }
                break;
            }
            default: {
                break;
            }
        }

    }

    public void startApp() {
        if (user != null){
            authorisedUser(user);
        } else {
            view.mainInterface();

            switch (scanner.nextLine()) {
                case "1": {
                    showFilmsList();
                    break;
                }
                case "2": {
                    logIn();
                    break;
                }
                case "3": break;
                default: {
                    System.out.println("You've entered wrong action, try again!");
                    startApp();
                    break;
                }
            }
        }
    }

    public void logIn() {
        view.signLoginInterface();

        String login = scanner.nextLine();

        Pattern regExp = Pattern.compile("/(\\s+)?\\w+|/+");
        Matcher matcher = regExp.matcher(login.replaceAll(" ",""));

        if (login.isEmpty()) {
            System.out.println("You've entered wrong data!");
            view.signLoginInterface();
            logIn();
        } else if (login.replaceAll(" ","").equals("/reg")) {
            view.regLoginInterface();
            regUser();
        } else if (matcher.matches()) {
            System.out.println("You've entered console command!");
            view.signLoginInterface();
            logIn();
        } else {
            view.signPassInterface();

            String password = scanner.nextLine();

            if ((user = (userReader.findUser(login, password))) != null) {
                System.out.println("You have successfully logged in!");
                startApp();
            } else {
                System.out.println("The user has not found or the wrong password has been entered!");
                startApp();
            }
        }
    }

    public void regUser() {
        String login = scanner.nextLine();

        if (!userReader.checkUserExist(login)) {
            view.regPassInterface();

            String password = scanner.nextLine();

            if (login.isEmpty() || password.isEmpty()) {
                System.out.println("You've entered wrong data!");
                view.regLoginInterface();
                regUser();
            }

            if ((user = userReader.addUser(login, password)) != null) startApp();

        } else {
            System.out.println("This login has already been created, please select another one!");
            view.regLoginInterface();
            regUser();
        }
    }
}