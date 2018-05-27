/**
 * @author deiber
 */
package GUI;

import static GUI.Launch.*;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Constructor de clase encargada de crear las SecondStages segun respectivo
 * llamado
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
			if ("GUI".equals(WindowName)) {

				Parent rootPane = FXMLLoader.load(Launch.class.getResource(WindowName + ".fxml"));
				Scene scene = new Scene(rootPane);
				scene.setFill(Color.TRANSPARENT);
				PrincipalStage.initStyle(StageStyle.TRANSPARENT);
				PrincipalStage.setScene(scene);
				PrincipalStage.setTitle("Odyssey");
				PrincipalStage.show();

			} else if ("LogInWindow".equals(WindowName)) {

				FXMLLoader loader = new FXMLLoader(Launch.class.getResource(WindowName + ".fxml"));
				AnchorPane SecondPane = (AnchorPane) loader.load();
				Stage SecondStage = new Stage();
				Scene scene = new Scene(SecondPane);
				SecondStage.setScene(scene);
				LogInController controller = loader.getController();
				controller.setSecondStage(SecondStage);
				SecondStage.setResizable(false);
				SecondStage.initModality(Modality.WINDOW_MODAL);
				SecondStage.initStyle(StageStyle.UNDECORATED); // quita barra de opciones de arriba
				SecondStage.initOwner(PrincipalStage);
				SecondStage.setTitle("Odyssey - Log In - Odyssey");
				SecondStage.show();

			} else if ("SignUpWindow".equals(WindowName)) {

				FXMLLoader loader = new FXMLLoader(Launch.class.getResource(WindowName + ".fxml"));
				AnchorPane SecondPane = (AnchorPane) loader.load();
				Stage SecondStage = new Stage();
				Scene scene = new Scene(SecondPane);
				SecondStage.setScene(scene);
				SignUpController controller = loader.getController();
				controller.setSecondStage(SecondStage);
				SecondStage.setResizable(false);
				SecondStage.initModality(Modality.WINDOW_MODAL);
				SecondStage.initStyle(StageStyle.UNDECORATED); // quita barra de opciones de arriba
				SecondStage.initOwner(PrincipalStage);
				SecondStage.setTitle("Odyssey - Sing Up - Odyssey");
				SecondStage.show();
			} else if ("SearchWindow".equals(WindowName)) {

				FXMLLoader loader = new FXMLLoader(Launch.class.getResource(WindowName + ".fxml"));
				AnchorPane SecondPane = (AnchorPane) loader.load();
				Stage SecondStage = new Stage();
				Scene scene = new Scene(SecondPane);
				SecondStage.setScene(scene);
				SearchController controller = loader.getController();
				controller.setSecondStage(SecondStage);
				SecondStage.setResizable(false);
				SecondStage.initModality(Modality.WINDOW_MODAL);
				SecondStage.initStyle(StageStyle.UNDECORATED); // quita barra de opciones de arriba
				SecondStage.initOwner(PrincipalStage);
				SecondStage.setTitle("Odyssey - Search - Odyssey");
				SecondStage.show();
			}
		} catch (IOException ex) {
			System.out.println(ex.toString());
			System.out.println("Stage Error");
		}
	}
}
