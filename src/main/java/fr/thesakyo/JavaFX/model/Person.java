package fr.thesakyo.JavaFX.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Person {

    private ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();
    private ObjectProperty<Gender> gender = new SimpleObjectProperty<>();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();

    public Person() {

        gender.set(Gender.UNKNOWN);
        name.set("");

        firstName.set("");
        birthDate.set(LocalDate.of(0, 1, 1));
    }

    public Person(String n, String f, LocalDate bd, Gender g) {

        name.set(n);
        firstName.set(f);
        birthDate.set(bd);
        gender.set(g);
    }

    public ObjectProperty<LocalDate> getBirthDate() { return birthDate; }

    public void setBirthDate(ObjectProperty<LocalDate> birthDate) { this.birthDate = birthDate; }

    public ObjectProperty<Gender> getGender() { return gender; }

    public void setGender(ObjectProperty<Gender> gender) { this.gender = gender; }

    public StringProperty getName() { return name; }

    public void setName(StringProperty name) { this.name = name; }

    public StringProperty getFirstName() { return firstName; }

    public void setFirstName(StringProperty firstName) { this.firstName = firstName ;}

    public String toString() { return "# Nom : " + name.get() + " - Prénom : " + firstName.get() + " #";}
}
