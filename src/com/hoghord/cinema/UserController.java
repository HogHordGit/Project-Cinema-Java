package com.hoghord.cinema;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {

    private static final String TAG = "---";
    private static final Pattern LOGIN = Pattern.compile("Login: (.+)");
    private static final Pattern PASSWORD = Pattern.compile("Password: (.+)");
    private static final Pattern STATUS = Pattern.compile("Status: (.+)");

    private static final String FILE_PATH = "resourses/users.txt";

    public User findUser(String login, String password) {

        boolean TagClosed = true;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals(TAG)) {
                    TagClosed = false;
                    continue;
                }

                Matcher matcher = LOGIN.matcher(line);

                if (matcher.matches()) {
                    String matchLogin = matcher.group(1);

                    line = br.readLine().trim();
                    matcher = PASSWORD.matcher(line);

                    if (matcher.matches()) {
                        String matchPassword = matcher.group(1);

                        line = br.readLine().trim();
                        matcher = STATUS.matcher(line);

                        if (matcher.matches()) {
                            String matchStatus = matcher.group(1);

                            if (login.equals(matchLogin) && password.equals(matchPassword)) {
                                String tag = br.readLine();

                                if ((tag != null) && (tag).equals(TAG)) {
                                    TagClosed = true;
                                    return new User(matchLogin, matchPassword, matchStatus);
                                } else {
                                    throw new IOException("Ошибка при чтении базы данных, проверте на правильность оформления!");
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public User addUser(String login, String password) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write('\n' + TAG + '\n');

            writer.write("Login: " + login + '\n');
            writer.write("Password: " + password + '\n');
            writer.write("Status: user" + '\n');

            writer.write(TAG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new User(login, password, "user");
    }

    public boolean checkUserExist(String login) {
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {

                Matcher matcher = LOGIN.matcher(line);

                if (matcher.matches()) {
                    if (matcher.group(1).equals(login)) return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}