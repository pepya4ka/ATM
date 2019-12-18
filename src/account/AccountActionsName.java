package account;

public enum AccountActionsName {
    STARTED_SERVICE ("Начал поездку"), //Начал обслуживание
    //ADDED_BALANCE ("Пополнил баланс"), //Пополнил баланс
    //PULL_OFF ("Снял со счета"), //Снял со счета
    //TRANSFERRED("Перевел на другой счет"),//Перевел на другой счет
    ON_MY_WAY("В пути..."),//Перевел на другой счет
    END_OF_SERVICE ("Закончил поездку"); //Закончил обслуживание

    private String title;

    AccountActionsName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
