package com.hoghord.cinema;

public class View {

    public void mainInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("            Enter your action               ");
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
        System.out.println("            Enter your action               ");
        System.out.println("            1. Films list                   ");
        System.out.println("            2. Edit list of films           ");
        System.out.println("            3. Log out                      ");
        System.out.println("            4. Exit                         ");
        System.out.println("--------------------------------------------");
        System.out.println("Your chose: ");
    }

    public void userInterface() {
        System.out.println("--------------------------------------------");
        System.out.println("            Enter your action               ");
        System.out.println("            1. Films list                   ");
        System.out.println("            2. Log out                      ");
        System.out.println("            3. Exit                         ");
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

    public void filmListHeader() {
        System.out.println("--------------------------------------------");
        System.out.println("          Here is films for you             ");
    }
    public void authorisedShowAfterChoseFilm() {
        System.out.println("---------------------------------------------");
        System.out.println("Введите номер фильма для просмотра информации");
        System.out.println("    Для фильтрации фильмов напишите /find    ");
        System.out.println("     Или напишите /back, чтобы вернутся      ");
        System.out.println("---------------------------------------------");
        System.out.println("Enter: ");
    }

    public void authorisedShowAfterChoseFilmForAdmin() {
        System.out.println("----------------------------------------------");
        System.out.println("Введите номер фильма для редактирования фильма");
        System.out.println("     Для фильтрации фильмов напишите /find    ");
        System.out.println("      Или напишите /back, чтобы вернутся      ");
        System.out.println("----------------------------------------------");
        System.out.println("Enter: ");
    }

    public void noAuthorisedShowAfterChoseFilm() {
        System.out.println("--------------------------------------------");
        System.out.println("    Чтобы узнать всю информацию про фильм   ");
        System.out.println("              или купить билет              ");
        System.out.println("         ВАМ нужно войти в систему!         ");
        System.out.println("--------------------------------------------");
        System.out.println("            Enter your action               ");
        System.out.println("            1. Sign up / sign in            ");
        System.out.println("            2. Exit                         ");
        System.out.println("--------------------------------------------");
        System.out.println("Your chose: ");
    }

    public void showActionsForFindingFilm() {
        System.out.println("--------------------------------------------");
        System.out.println("     Напишите критерий по которому нужно    ");
        System.out.println("             найти фильм/фильмы             ");
        System.out.println("--------------------------------------------");
        System.out.println("              Примеры для ввода:            ");
        System.out.println("    По названию: Big small coins            ");
        System.out.println("    Количество мест: 2                      ");
        System.out.println("    Датой или временем: 27-12-2023/13:00    ");
        System.out.println("       Нажмите Enter для возврата           ");
        System.out.println("--------------------------------------------");
        System.out.println("Enter: ");
    }

    public void selectFilmAfterSorting() {
        System.out.println("---------------------------------------------");
        System.out.println("Введите номер фильма для просмотра информации");
        System.out.println("     Или напишите /back, чтобы вернутся      ");
        System.out.println("---------------------------------------------");
        System.out.println("Enter: ");
    }

    public void askToBuyATicket() {
        System.out.println("----------------------------------------------");
        System.out.println(" Вы хотите купить билет/билеты на этот фильм? ");
        System.out.println("               Введите yes/no                 ");
        System.out.println("----------------------------------------------");
        System.out.println("Enter: ");
    }

    public void selectActionAfterChoseEdit() {
        System.out.println("--------------------------------------------");
        System.out.println("            Enter your action               ");
        System.out.println("            1. Редактировать фильм          ");
        System.out.println("            2. Добавить фильм               ");
        System.out.println("            3. Назад                        ");
        System.out.println("--------------------------------------------");
        System.out.println("Your chose: ");
    }

    public void showToAdminCorrectFormatToAddFilm() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("            Введите данные о фильме через запятую             ");
        System.out.println("                  Формат записи для фильма:                   ");
        System.out.println(" name, tickets, DAY, date, time, duration, prise, description ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Enter: ");
    }

    public void showToAdminCorrectFormatToChangeLine() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("        Введите название строки, которую надо изменить        ");
        System.out.println("                        Доступные линии:                      ");
        System.out.println("     Name|Tickets|Day|Date|Time|Duration|Price|Description    ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Enter: ");
    }

    public void writeAdminAnswerEditFilm() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("            Напишите свое значение для изменения              ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Enter: ");
    }
}