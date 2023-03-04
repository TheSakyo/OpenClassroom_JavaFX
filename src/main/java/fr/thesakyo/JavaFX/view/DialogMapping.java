package fr.thesakyo.JavaFX.view;

import fr.thesakyo.JavaFX.JavaFXMain;
import fr.thesakyo.JavaFX.model.Gender;
import fr.thesakyo.JavaFX.model.Person;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DialogMapping {

    private Stage stageDialog;

    @FXML
    private TextField nameForm;
    @FXML
    private TextField firstnameForm;
    @FXML
    private DatePicker dateForm;
    @FXML
    private ComboBox<Gender> genderForm;
    @FXML
    private Button delete;
    @FXML
    private Button edit;

    private JavaFXMain main;
    private Person person;

    public DialogMapping() {}

    public void setMainClass(JavaFXMain m, Stage stage) { main = m; stageDialog = stage; }

    //On initialise ici les valeurs de la liste déroulante
    //avant de sélectionner la valeur de la personne
    @FXML
    public void initialize() { genderForm.getItems().setAll(Gender.values()); }

    //Afin de récupérer le stage de la 'popup'
    //et pouvoir la clore
    public void setStage(Stage s) { stageDialog = s; }

    public void setPerson(Person p) {

        person = p;
        nameForm.setText(person.getName().get());
        firstnameForm.setText(person.getFirstName().get());
        dateForm.setValue(person.getBirthDate().get());
        genderForm.getSelectionModel().select(person.getGender().get());
    }

    //Méthode de contrôle de la validité des données saisies
    private boolean controlerForm() {

        boolean isOk = true;

        List<String> messageError = new ArrayList<String>();

        if(nameForm.getText() == null || nameForm.getText().isEmpty()) { isOk = false; messageError.add("Le champ \"Nom\" est obligatoire"); }

        if(firstnameForm.getText() == null || firstnameForm.getText().isEmpty()) { isOk = false; messageError.add("Le champ \"Préname\" est obligatoire"); }

        if(dateForm.getValue() == null || dateForm.getValue().toString().isEmpty()) { isOk = false; messageError.add("Le champ \"Date\" est obligatoire"); }

        if(!isOk) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur ! ");

            StringBuilder sb = new StringBuilder();
            messageError.forEach((x) -> sb.append("\n").append(x));

            error.setHeaderText(sb.toString());
            error.showAndWait();
        }

        return isOk;
    }

    //sauvegarde de la personne, que ce soit une édition ou une création
    public void save()  {

        if(controlerForm()) {

            person.setName(new SimpleStringProperty(nameForm.getText()));
            person.setFirstName(new SimpleStringProperty(firstnameForm.getText()));

            //Afin de pouvoir gérer la modification de date à la souris
            //ou en modifiant le texte du composant directement
            //On récupère la date au format texte pour la réinjecter
            //dans le composant                                 //Date du composant au format texte
            dateForm.setValue(dateForm.getConverter().fromString(dateForm.getEditor().getText()));

            person.setBirthDate(new SimpleObjectProperty<LocalDate>(dateForm.getValue()));
            person.setGender(new SimpleObjectProperty<>(genderForm.getValue()));

            //S'il s'agit d'une création, on ajoute la personne dans le tableau
            if(stageDialog.getTitle().startsWith("Création")) { main.getListPerson().add(person); }

            else if(stageDialog.getTitle().startsWith("Édition")) {

                int index = main.getListPerson().indexOf(person);

                main.getListPerson().remove(index);

                Person newPerson = new Person(person.getName().get(), person.getFirstName().get(), person.getBirthDate().get(), person.getGender().get());

                main.getListPerson().add(index, newPerson);
            }

            //On ferme la boîte de dialogue
            stageDialog.close();
        }
    }


    /******************************/
    /* PARTIE ÉVÈNEMENTS (ACTION) */
    /*****************************/


    // Ajoute une Personne //
    @FXML
    public void accept() { save(); }
    // Ajoute une Personne //

    //On ferme la boîte de dialogue
    @FXML
    public void exit() {  main.getScene().setCursor(Cursor.DEFAULT); stageDialog.close(); }

    // Évènement du curseur de la souris //

    /******************************/
    /* PARTIE ÉVÈNEMENTS (ACTION) */
    /*****************************/
}