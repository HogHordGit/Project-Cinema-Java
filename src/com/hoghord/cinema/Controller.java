package com.hoghord.cinema;

import java.io.BufferedReader;
import java.io.FileReader;
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
        actionAfterShowFilmList();
    }

    public void selectFilmByNum() {
        view.selectFilmAfterSorting();

        String action = scanner.nextLine();

        if (action.equals("/back")) {

        } else if (Integer.parseInt(action) > (filmsList.size() + 1) || Integer.parseInt(action) < 0) {
            System.out.println("Вы дебил!");
            selectFilmByNum();
        } else {
            filmsController.printInfoAboutChosenFilm(filmsList.get(Integer.parseInt(action) - 1));
            buyTicketBySelectedFilm(filmsList.get(Integer.parseInt(action) - 1));
        }
    }

    public void buyTicketBySelectedFilm(Films films) {
        view.askToBuyATicket();

        switch (scanner.nextLine()) {
            case "yes": {
                filmsController.buyTicketAfterSearching(films);
                authorisedUser(user);
                break;
            }
            case "no": {
                authorisedUser(user);
                break;
            }
            default: {
                System.out.println("Вы дебил!");
                buyTicketBySelectedFilm(films);
                break;
            }
        }
    }

    public void actionAfterShowFilmList() {
        if(user != null) {
            view.authorisedShowAfterChoseFilm();

            String action = scanner.nextLine();

            if (action.equals("/find")) {
                view.showActionsForFindingFilm();

                if ((filmsList = filmsController.findFilmByArgument(filmsList, scanner.nextLine())).isEmpty()) actionAfterShowFilmList();

                selectFilmByNum();

            } else if (action.equals("/back")) {
                authorisedUser(user);
            } else {
                try {
                    int intValue = Integer.parseInt(action);
                    System.out.println("Число: " + intValue);
                } catch (NumberFormatException e) {
                    System.out.println("Вы дебил!");
                    actionAfterShowFilmList();
                }
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
                    System.out.println("Вы дебил!");
                    actionAfterShowFilmList();
                    break;
                }
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
                        System.out.println("Вы дебыл!");
                        view.adminInterface();
                        actionWindowAfterLogIn();
                        break;
                    }
                }
            }
            case "user": {
                String action = scanner.nextLine();

                switch (action.trim()) {
                    case "1": {
                        showFilmsList();
                        break;
                    }
                    case "2": {
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
                        System.out.println("Вы дебыл!");
                        view.userInterface();
                        actionWindowAfterLogIn();
                        break;
                    }
                }
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
            System.out.println("Вы ввели не коректные данные");
            view.signLoginInterface();
            logIn();
        } else if (login.replaceAll(" ","").equals("/reg")) {
                view.regLoginInterface();
                regUser();
        } else if (matcher.matches()) {
            System.out.println("Вы ввели консольную команду!");
            view.signLoginInterface();
            logIn();
        } else {
            view.signPassInterface();

            String password = scanner.nextLine();

            if ((user = (userReader.findUser(login, password))) != null) {
                System.out.println("Вы успешно ввошли в систему!");
                startApp();
            } else {
                System.out.println("Пользователь не найден или был введён не правильный пароль!");
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
                System.out.println("Вы ввели не коректные данные!");
                view.regLoginInterface();
                regUser();
            }

            if ((user = userReader.addUser(login, password)) != null) startApp();

        } else {
            System.out.println("Такой логин уже создан, выберите другой!");
            view.regLoginInterface();
            regUser();
        }
    }
}
