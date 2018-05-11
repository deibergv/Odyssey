/**
 * @author deiber
 */
package GUI;

import static GUI.WindowCreator.WindowCreator;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Constructor de la clase de la Ventana Log In para inicio de sesion
 * 
 * @author deiber
 */
public class LogInController implements Initializable {

	private Stage SecondStage;

	public void setSecondStage(Stage newSecondStage) {
		this.SecondStage = newSecondStage;
	}

	/**
	 * Cierre del programa
	 * 
	 * @param event
	 */
	@FXML
	private void handleClose(ActionEvent event) {
		// SecondStage.close();
		System.exit(0);
	}

	@FXML
	private Button BtSignUp;

	/**
	 * Cambio de ventana para registro
	 */
	@FXML
	private void SignUp(ActionEvent event) {
		SecondStage.close();
		WindowCreator("SignUpWindow");
	}

	@FXML
	private Button BtLogIn;

	/**
	 * Envio de datos al servidor para inicio de sesion
	 */
	@FXML
	private void LogIn(ActionEvent event) {
		// SecondStage.close(); // if inicia sesion de forma correcta, cierra ventana y
		// sigue
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}