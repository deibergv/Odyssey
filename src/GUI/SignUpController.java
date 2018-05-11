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
 * Constructor de la clase de la Ventana Sign Up para el registro de nuevos
 * usuarios
 *
 * @author deiber
 */
public class SignUpController implements Initializable {

	private Stage SecondStage;

	public void setSecondStage(Stage NewSecondStage) {
		this.SecondStage = NewSecondStage;
	}

	/**
	 * Cierre de ventana y cambio a ventana Log In
	 * 
	 * @param event
	 */
	@FXML
	private void handleClose(ActionEvent event) {
		SecondStage.close();
		WindowCreator("LogInWindow");
	}

	@FXML
	private Button BtLogIn;

	/**
	 * Cambio a ventana de inicio de sesion
	 * 
	 * @param event
	 */
	@FXML
	private void LogIn(ActionEvent event) {
		SecondStage.close();
		WindowCreator("LogInWindow");
	}

	@FXML
	private Button BtSignUp;

	/**
	 * Envio de datos al servidor para registro
	 * 
	 * @param event
	 */
	@FXML
	private void SignUp(ActionEvent event) {
		// SecondStage.close(); // if inicia sesion de forma correcta, cierra ventana y
		// sigue
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}