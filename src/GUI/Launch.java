/**
 * @author deiber
 */
package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main, llamado a la ventana principal
 * 
 * @author deiber
 */
public class Launch extends Application {

	public static Stage PrincipalStage = null;
//	public static Stage SecondStage = null;

	@Override
	public void start(Stage PrincipalStage) throws Exception {
		this.PrincipalStage = PrincipalStage;
		WindowCreator.WindowCreator("GUI");
		WindowCreator.WindowCreator("LogInWindow");
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
