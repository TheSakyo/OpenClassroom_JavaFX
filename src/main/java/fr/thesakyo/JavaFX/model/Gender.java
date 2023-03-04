package fr.thesakyo.JavaFX.model;

public enum Gender {

    MAN("Masculin"),
    WOMEN("Féminin"),
    UNKNOWN("Inconnu");

    private final String name;

    Gender(String n) { name = n; }

    public String toString() { return name; }
}
