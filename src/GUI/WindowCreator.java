package GUI;

import static GUI.Main.PrincipalStage;
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
        String Window = WindowName;
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(Window + ".fxml"));
            AnchorPane SecondWindow = (AnchorPane) loader.load();
            Stage window = new Stage();
            window.setResizable(false);
            window.initModality(Modality.WINDOW_MODAL);
            window.initOwner(PrincipalStage);
            Scene scene = new Scene(SecondWindow);
            window.setScene(scene);
            if (null != WindowName) {
                switch (WindowName) {
                    case "LoginWindow": {
                        window.setTitle("Login/Registro");
                        LoginController controller = loader.getController();
                        controller.setStagePrincipal(window);
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
}
