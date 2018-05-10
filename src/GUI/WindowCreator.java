package GUI;

import static GUI.Main.*;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Constructor de clase encargada de crear las ventanas segun respectivo llamado
 *
 * @author deiber
 */
public class WindowCreator {

	// public static Stage PrincipalStage = new Stage();
	public static AnchorPane rootPane;
	// public static AnchorPane SecondPane;
	// public static Stage SecondStage = new Stage();

	/**
	 * Filtro de creacion
	 *
	 * @param WindowName
	 */
	public static void WindowCreator(String WindowName) {
		try {
			if ("GUI".equals(WindowName)) {
				
				FXMLLoader loader = new FXMLLoader(Main.class.getResource(WindowName + ".fxml"));
				rootPane = (AnchorPane) loader.load();
				Scene scene = new Scene(rootPane);
				PrincipalStage.setScene(scene);
				PrincipalStage.setResizable(false);
				// stagePrincipal.initModality(Modality.WINDOW_MODAL);
				PrincipalStage.setTitle("Odyssey");
				GUIController controller = loader.getController();
				controller.setPrincipalProgram(scene);
				PrincipalStage.show();
				
			} else if ("LogInWindow".equals(WindowName)) {
				
				FXMLLoader loader = new FXMLLoader(Main.class.getResource(WindowName + ".fxml"));
				AnchorPane SecondPane = (AnchorPane) loader.load();
				Stage SecondStage = new Stage();
				Scene scene = new Scene(SecondPane);///////////////////////////////////////
				SecondStage.setScene(scene);
				LogInController controller = loader.getController(); // intentar seguir con cierre de ventana desde
				controller.setSecondStage(SecondStage);
				SecondStage.setResizable(false);
				SecondStage.initModality(Modality.WINDOW_MODAL);
				SecondStage.initOwner(PrincipalStage);
				// SecondWindow.initStyle(StageStyle.UNDECORATED); // quita barra de opciones de
				// arriba
				SecondStage.setTitle("¡WELCOME TO ODYSSEY!");
				SecondStage.show();
				
			} else if ("SignUpWindow".equals(WindowName)) {
				
				FXMLLoader loader = new FXMLLoader(Main.class.getResource(WindowName + ".fxml"));
				AnchorPane SecondPane = (AnchorPane) loader.load();
				Stage SecondStage = new Stage();
				Scene scene = new Scene(SecondPane);///////////////////////////////////////
				SecondStage.setScene(scene);
				SignUpController controller = loader.getController(); // intentar seguir con cierre de ventana desde
				controller.setSecondStage(SecondStage);
				SecondStage.setResizable(false);
				SecondStage.initModality(Modality.WINDOW_MODAL);
				SecondStage.initOwner(PrincipalStage);
				// SecondWindow.initStyle(StageStyle.UNDECORATED); // quita barra de opciones de
				// arriba
				SecondStage.setTitle("¡WELCOME TO ODYSSEY!");
				SecondStage.show();
			}
		} catch (IOException ex) {
			System.out.println(ex.toString());
			System.out.println("Error con la Ventana");
		}

		// try {
		// FXMLLoader loader = new FXMLLoader(Main.class.getResource(WindowName +
		// ".fxml"));
		// AnchorPane SecondWindow = (AnchorPane) loader.load();
		// Stage window = new Stage();
		// window.setResizable(false);
		// window.initModality(Modality.WINDOW_MODAL);
		// window.initOwner(PrincipalStage);
		// Scene scene = new Scene(SecondWindow);
		// window.setScene(scene);
		// if (null != WindowName) {
		// switch (WindowName) {
		// case "LogInWindow": {
		// // window.initStyle(StageStyle.UNDECORATED); // quita barra de opciones de
		// // arriba
		// window.setTitle("¡WELCOME TO ODYSSEY!");
		// LogInController controller = loader.getController(); // intentar seguir con
		// cierre de ventana desde
		// // "X"
		// controller.setSecondStage(window);
		// break;
		// }
		// case "SignUpWindow": {
		// window.setTitle("¡WELCOME TO ODYSSEY!");
		// SignUpController controller = loader.getController();
		// controller.setPrincipalStage(window);
		// break;
		// }
		// // falta otra para ordenar canciones
		// // tal vez otra para busqueda de canciones
		//
		// default:
		// break;
		// }
		// }
		// window.show();
		// } catch (IOException ex) {
		// System.out.println(ex.toString());
		// System.out.println("Error en Ventana");
		// }
	}

	public static void WindowDestructor(String WindowName) {
		if ("LogInWindow".equals(WindowName)) {
			// SecondStage.close();
		}
	}
}
