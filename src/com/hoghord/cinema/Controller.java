package com.hoghord.cinema;

import java.util.Scanner;

public class Controller {

    static View view = new View();
    static User user;
    static UserController userReader = new UserController();
    Scanner scanner = new Scanner(System.in);

    public void authorisedUser(User user) {
        if (user.getStatus().equals("admin")) {
            view.adminInterface();

        } else if (user.getStatus().equals("user")) {
            System.out.println("chlen");
            // интерфейс с листом фильмов
        }
    }

    public void startApp() {
        if (user != null){
            authorisedUser(user);
        } else {
            view.mainInterface();

            switch (scanner.nextLine()) {
                case "1": {
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

        if (login.isEmpty()) {
            System.out.println("Вы ввели не коректные данные!");
            view.signLoginInterface();
            logIn();
        }

        if (login.equals("/reg")) {
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

            if ((user = userReader.addUser(login, password)) != null) {
                startApp();
            }
        } else {
            System.out.println("Такой логин уже создан, выберите другой!");
            view.regLoginInterface();
            regUser();
        }
    }
}
