package fr.thesakyo.JavaFX;

import fr.thesakyo.JavaFX.model.Person;
import fr.thesakyo.JavaFX.model.Gender;
import fr.thesakyo.JavaFX.view.DialogMapping;
import fr.thesakyo.JavaFX.view.Mapping;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class JavaFXMain extends Application {

    //Nous créons des variables de classes afin de pouvoir y accéder partout
    //Ceci pour pouvoir y positionner les éléments que nous avons faits.
    //Il y a un BorderPane car le Conteneur principal de notre IHM
    //est un BorderPane, nous reparlerons de l'objet Stage
    private Stage stageMain;
    private Scene scene;
    private BorderPane ContainerMain;

    private final ObservableList<Person> PersonList = FXCollections.observableArrayList();
    public ObservableList<Person> getListPerson(){ return PersonList; }


    public JavaFXMain() {

        PersonList.add(new Person("Proviste", "Alain", LocalDate.of(1970, 1, 1), Gender.MAN));
        PersonList.add(new Person("D'Arc", "Jeanne", LocalDate.of(1431, 5, 30), Gender.WOMEN));
        PersonList.add(new Person("Caisse", "Jean", LocalDate.of(1950, 3, 3), Gender.MAN));
    }


    @Override
    public void start(Stage primaryStage) {

        stageMain = primaryStage;
        //Ça ne vous rappelle pas une JFrame ?

        stageMain.setTitle("Application de gestion de personnes");

        //Nous allons utiliser notre fichier FXML dans ces deux méthodes
        initContainerMain();
        initContent();

    }

    private void initContainerMain() {

        //On créait un chargeur de FXML
        FXMLLoader loader = new FXMLLoader();

        //On lui spécifie le chemin relatif à notre classe
        //du fichier FXML à charger : dans le sous-dossier view
        loader.setLocation(JavaFXMain.class.getResource("view/ContainerMain.fxml"));


        try {

            //Le chargement nous donne notre conteneur
            ContainerMain = loader.load();

            //On définit une scène principale avec notre conteneur
            scene = new Scene(ContainerMain);

            //Que nous affectons à notre Stage
            stageMain.setScene(scene);

            //Initialisation de notre contrôleur
            Mapping control = loader.getController();

            //Puis, on lui spécifie la classe principale
            control.setMainApp(this);

            //On Affiche
            stageMain.show();

        } catch(IOException e) { e.printStackTrace(); }
    }

    private void initContent() {

        //On créait un chargeur de FXML
        FXMLLoader loader = new FXMLLoader();

        //On lui spécifie le chemin relatif à notre classe
        //du fichier FXML à charger : dans le sous-dossier view
        loader.setLocation(JavaFXMain.class.getResource("view/PersonView.fxml"));

        try {

            //Nous récupérons notre conteneur qui contiendra les données
            //Pour rappel, c'est un AnchorPane...
            AnchorPane PersonContainer = loader.load();

            //Nous l'ajoutons à notre conteneur principal
            //Au centre, puisque'il s'agit d'un BorderPane
            ContainerMain.setCenter(PersonContainer);

            String css = getClass().getResource("view/style.css").toExternalForm();
            PersonContainer.getStylesheets().addAll(css);

            //Initialisation de notre contrôleur
            Mapping control = loader.getController();

            //Nous lui passons notre instance de classe
            //pour qu'il puisse récupérer notre liste observable
            control.setMainApp(this);

        } catch(IOException e) { e.printStackTrace(); }
    }

    //Méthode qui va afficher le popup d'édition
    //ou de création d'une personne et initialiser son contrôleur
    public void initDialogPerson(Person person, String title) {

        //On créait un chargeur de FXML
        FXMLLoader loader = new FXMLLoader();

        //On lui spécifie le chemin relatif à notre classe
        //du fichier FXML à charger : dans le sous-dossier view
        loader.setLocation(JavaFXMain.class.getResource("view/PersonDialog.fxml"));

        try {

            //Nous récupérons notre conteneur qui contiendra les données
            //Pour rappel, c'est un AnchorPane...
            AnchorPane popup = loader.load();

            // Création d'un nouveau Stage qui sera dépendant du Stage principal
            Stage stageDialog = new Stage();
            stageDialog.setTitle(title);
            stageDialog.initModality(Modality.WINDOW_MODAL);


            //Interdit à l'utilisateur de modifier la taille de la fenêtre
            stageDialog.setResizable(false);

            String css = getClass().getResource("view/style.css").toExternalForm();
            popup.getStylesheets().addAll(css);

            //Avec cette instruction, notre fenêtre modifiée sera modale
            //par rapport à notre stage principal
            stageDialog.initOwner(stageMain);
            scene = new Scene(popup);
            stageDialog.setScene(scene);

            // initialisation du contrôleur
            DialogMapping control = loader.getController();

            //On passe la personne avec laquelle nous souhaitons travailler
            //une existante ou une nouvelle
            control.setPerson(person);
            control.setMainClass(this, stageDialog);

            // Show the dialog and wait until the user closes it
            stageDialog.showAndWait();

        } catch(IOException e) { e.printStackTrace(); }
    }

    public Stage getStage() { return stageMain; }

    public Scene getScene() { return scene; }

    public static void main(String[] args) { launch(args); }
}
