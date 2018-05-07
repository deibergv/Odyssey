package GUI;

import static GUI.Main.*;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Constructor de clase encargada de crear las ventanas segun respectivo llamado
 *
 * @author deiber
 */
public class WindowCreator {

    /**
     * Filtro de creacion
     *
     * @param WindowName
     */
    public static void WindowCreator(String WindowName) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(WindowName + ".fxml"));
            AnchorPane SecondWindow = (AnchorPane) loader.load();
            Stage window = new Stage();
            window.setResizable(false);
            window.initModality(Modality.WINDOW_MODAL);
            window.initOwner(PrincipalStage);
            Scene scene = new Scene(SecondWindow);
            window.setScene(scene);
            if (null != WindowName) {
                switch (WindowName) {
                    case "LogInWindow": {
                        window.setTitle("Log In");
                        LogInController controller = loader.getController();
                        controller.setPrincipalStage(window);
                        break;
                    }
                    case "SignUpWindow": {
                        window.setTitle("¡WELCOME TO ODYSSEY!");
                        SignUpController controller = loader.getController();
                        controller.setPrincipalStage(window);
                        break;
                    }
                    // falta otra para ordenar canciones
                    // tal vez otra para busqueda de canciones
                    
                    
                    default:
                        break;
                }
            }
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.toString());
            System.out.println("Error en Ventana");
        }
    }
    
    public static void WindowDestructor(String WindowName) {
    	
    }
}
