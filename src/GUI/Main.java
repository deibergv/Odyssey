package GUI;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main.java
 * 
 * @author deiber
 */
public class Main extends Application {

    public static Stage PrincipalStage;
    public static AnchorPane rootPane;

    /**
     * Creacion de ventanas segun su respectiva invocacion
     *
     * @param NombreDeVentana
     */
    public void WindowCreator(String WindowName) {

        if ("GUI".equals(WindowName)) {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("GUI.fxml"));
                rootPane = (AnchorPane) loader.load();
                Scene scene = new Scene(rootPane);
                PrincipalStage.setResizable(false);
//                stagePrincipal.initModality(Modality.WINDOW_MODAL);
                PrincipalStage.setScene(scene);
                PrincipalStage.setTitle("Odyssey");
                GUIController controller = loader.getController();
                controller.setPrincipalProgram(this);
                PrincipalStage.show();
            } catch (IOException ex) {
                System.out.println(ex.toString());
                System.out.println("Error con la Ventana");
            }
        }
    }

    /**
     * Llamado a la ventana principal
     *
     * @param PrincipalStage
     * @throws Exception
     */
    @Override
    public void start(Stage PrincipalStage) throws Exception {
        this.PrincipalStage = PrincipalStage;
        WindowCreator("GUI");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
