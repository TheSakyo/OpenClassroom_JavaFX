package fr.thesakyo.JavaFX.view;

import fr.thesakyo.JavaFX.JavaFXMain;
import fr.thesakyo.JavaFX.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Mapping {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> firstnameColumn;
    @FXML
    private Label nameValue;
    @FXML
    private Label firstnameValue;
    @FXML
    private Label dateValue;
    @FXML
    private Label genderValue;
    @FXML
    private Button delete;
    @FXML
    private Button edit;

    //Objet servant de référence à notre classe principale
    //afin de pouvoir récupérer la liste de nos objets.
    private JavaFXMain main;

    //Objet pour détecter le type de panel de l'élément graphique à initialiser
    @FXML
    private Pane pane;

    //Un constructeur par défaut
    public Mapping() {}


    //Méthode qui initialise notre interface graphique
    //avec nos données métier
    @FXML
    private void initialize() {

        if(pane instanceof BorderPane) return;

        //Initialise le tableau 'Personne' avec les deux colonnes.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstName());

        //Nous récupérons le model de notre tableau (vous connaissez maintenant)
        //où nous récupérons l'item sélectionné et où nous y attachons un écouteur
        //Qui va utiliser notre méthode de mise à jour d'IHM
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> initializeDescription(newValue));
    }


    //Méthode qui va mettre les valeurs de notre objet dans les composants
    public void initializeDescription(Person p) {

        //On réinitialise par défaut
        nameValue.setText("");
        firstnameValue.setText("");
        dateValue.setText("");
        genderValue.setText("");

        //Si un objet est passé en paramètre, on modifie l'IHM
        if(p != null) {

            //ATTENTION : les accesseurs retournent des objets Property Java FX
            //Pour récupérer leurs vraies valeurs vous devez utiliser la méthode get()
            nameValue.setText(p.getName().get());
            firstnameValue.setText(p.getFirstName().get());

            //Sur les deux champs ci-dessous, en plus de get()
            //vous devez utiliser toString() car ce sont des objets particuliers
            dateValue.setText(p.getBirthDate().get().toString());
            genderValue.setText(p.getGender().get().toString());

        }
    }

    //Méthode qui sera utilisée dans l'initialisation de l'IHM
    //dans notre classe principale
    public void setMainApp(JavaFXMain mainApp) {

        this.main = mainApp;

        //On lie notre liste observable au composant TableView
        if(this.pane instanceof AnchorPane) personTable.setItems(main.getListPerson());
    }



    /******************************/
    /* PARTIE ÉVÈNEMENTS (ACTION) */
    /*****************************/


    // Ferme l'application //
    @FXML
    public void exit() {

        //On affiche un message car on est poli.
        Alert close = new Alert(Alert.AlertType.INFORMATION);

        close.setTitle("Au revoir !");
        close.setHeaderText("À bientôt...");
        close.setContentText("on espère vous revoir :)");
        close.showAndWait();

        //Et on clos le stage principal, donc l'application
        this.main.getStage().close();
    }
    // Ferme l'application //



    // Ajoute une Personne //
    @FXML
    public void addPerson() {

        //On affiche le popup avec une personne inexistante
        this.main.initDialogPerson(new Person(), "Création d'une personne");
    }
    // Ajoute une Personne //


    // Édite une Personne //
    @FXML
    public void editPerson() {

        int index = personTable.getSelectionModel().getSelectedIndex();

        //Si aucune ligne n'est sélectionnée, index vaudra -1
        if(index > -1) {

            Person actual_person = personTable.getItems().get(index);

            //On affiche le popup avec la personne à éditer
            this.main.initDialogPerson(actual_person, "Édition d'une personne");

        }  else {

            Alert problem = new Alert(Alert.AlertType.ERROR);
            problem.setTitle("Erreur");
            problem.setHeaderText("Veuillez sélectionnez une ligne dans le tableau");
            problem.showAndWait();
        }
    }
    // Édite une Personne //


    // Supprime une Personne //
    @FXML
    public void removePerson() {

        int index = personTable.getSelectionModel().getSelectedIndex();

        //Si aucune ligne n'est sélectionnée, index vaudra -1
        if(index > -1) { personTable.getItems().remove(index); }

        else {

            Alert problem = new Alert(Alert.AlertType.ERROR);
            problem.setTitle("Erreur");
            problem.setHeaderText("Veuillez sélectionnez une ligne dans le tableau");
            problem.showAndWait();
        }
    }
    // Supprime une Personne //



    /******************************/
    /* PARTIE ÉVÈNEMENTS (ACTION) */
    /*****************************/
}