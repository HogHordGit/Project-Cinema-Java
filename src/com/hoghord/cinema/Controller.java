package com.hoghord.cinema;

import java.util.ArrayList;
import java.util.Objects;
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

    public void checkFilmsList(String status) {
        switch (status) {
            case "admin": {
                view.filmListHeader();
                filmsList = filmsController.findAllFilms();
                filmsController.printFilmList(filmsList);
                view.filmListEnd();
                break;
            }
            case "user": {
                view.filmListHeader();
                filmsList = filmsController.findAllFilms();
                filmsController.printFilmList(filmsList);
                break;
            }
            default: {
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
                        checkFilmsList(user.getStatus());
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
                        checkFilmsList(user.getStatus());
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
                    view.filmListHeader();
                    checkFilmsList(user.getStatus());
                    break;
                }
                case "2": {
                    view.signLoginInterface();
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
        String login = scanner.nextLine();

        Pattern regExp = Pattern.compile("/(\\s+)?\\w+|/+");
        Matcher matcher = regExp.matcher(login.replaceAll(" ",""));

        if (login.isEmpty()) {
            System.out.println("Вы ввели не коректные данные");
            view.signLoginInterface();
            logIn();
        } else if (matcher.matches()) {
            System.out.println("Вы ввели консольную команду!");
            view.signLoginInterface();
            logIn();
        } else if (login.replaceAll(" ","").equals("/reg")) {
            view.regLoginInterface();
            regUser();
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
