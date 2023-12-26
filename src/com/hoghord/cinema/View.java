package com.hoghord.cinema;

public class View {

    public void mainInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("            Enter yout action               ");
        System.out.println("            1. Films list                   ");
        System.out.println("            2. Sign up / sign in            ");
        System.out.println("            3. Exit                         ");
        System.out.println("--------------------------------------------");
        System.out.println("Your chose: ");
    }

    public void signLoginInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("            Enter your login                ");
        System.out.println("      or enter /reg for registration        ");
        System.out.println("--------------------------------------------");
        System.out.println("Enter: ");
    }

    public void signPassInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("            Enter your password              ");
        System.out.println("--------------------------------------------");
        System.out.println("Enter: ");
    }

    public void adminInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("            Enter yout action               ");
        System.out.println("            1. Films list                   ");
        System.out.println("            2. Edit list of films           ");
        System.out.println("            3. Log out                      ");
        System.out.println("            4. Exit                         ");
        System.out.println("--------------------------------------------");
        System.out.println("Your chose: ");
    }

    public void regLoginInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("      Enter your login for registration     ");
        System.out.println("--------------------------------------------");
        System.out.println("Enter: ");
    }

    public void regPassInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("     Enter your password for registration   ");
        System.out.println("--------------------------------------------");
        System.out.println("Enter: ");
    }
}
