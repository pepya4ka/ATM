package account;

public enum AccountActionsName {
    STARTED_SERVICE ("Начал обслуживание"), //Начал обслуживание
    ADDED_BALANCE ("Пополнил баланс"), //Пополнил баланс
    PULL_OFF ("Снял со счета"), //Снял со счета
    END_OF_SERVICE ("Закончил обслуживание"); //Закончил обслуживание

    private String title;

    AccountActionsName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
