package controller;

public enum Option {
    GET_BALANCE("Pokaż bilans"),
    ADD_EXPENSE("Dodaj wydatek"),
    ADD_INCOME("Dodaj przychód"),
    SHOW_ALL_EXPENSES("Pokaż wydatki"),
    SHOW_ALL_INCOME("Pokaż przychody"),
    DELETE_ITEM("Usuń wybraną pozycję"),
    EXIT("Wyjście z programu");

    private String description;

    Option(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.ordinal() + " - " + description;
    }
}