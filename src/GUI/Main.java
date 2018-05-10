package GUI;
	
import java.io.IOException;

import static GUI.WindowCreator.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    public static Stage PrincipalStage;
//    public static Stage SecondStage;
//    public static AnchorPane rootPane;

    /**
     * Creacion de ventanas segun su respectiva invocacion
     *
     * @param NombreDeVentana
     */
//    public void PrincipalWindowCreator(String WindowName) {
//
//        if ("GUI".equals(WindowName)) {
//            try {
//            	FXMLLoader loader = new FXMLLoader(Main.class.getResource("GUI.fxml"));
//                rootPane = (AnchorPane) loader.load();
//                Scene scene = new Scene(rootPane);
//                PrincipalStage.setResizable(false);
////                stagePrincipal.initModality(Modality.WINDOW_MODAL);
//                PrincipalStage.setScene(scene);
//                PrincipalStage.setTitle("Odyssey");
//                GUIController controller = loader.getController();
//                controller.setPrincipalProgram(this);
//                PrincipalStage.show();
//            } catch (IOException ex) {
//                System.out.println(ex.toString());
//                System.out.println("Error con la Ventana");
//            }
//        }
//    }

    /**
     * Llamado a la ventana principal
     *
     * @param stagePrincipal
     * @throws Exception
     */
    @Override
    public void start(Stage PrincipalStage) throws Exception {
        this.PrincipalStage = PrincipalStage;
        WindowCreator("GUI");
        WindowCreator("LogInWindow");
    }

    /**
     * Llamado a la ventana principal
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

