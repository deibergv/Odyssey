/**
 * @author deiber
 */

package GUI;

import static GUI.WindowCreator.WindowCreator;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Constructor de la clase de la Ventana Log In para el inicio de sesion
 * 
 * @author deiber
 *
 */
public class LogInController implements Initializable {

	private Stage SecondStage;

	public void setSecondStage(Stage newSecondStage) {
		this.SecondStage = newSecondStage;
	}

	private double xOffset = 0;
	private double yOffset = 0;
	AnimationGenerator animationGenerator = null;

	@FXML
	AnchorPane parentLogIn;
	@FXML
	JFXTextField UserField;
	@FXML
	JFXPasswordField PassField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		animationGenerator = new AnimationGenerator();

	}

	/**
	 * Creacion de animacion de movimiento de ventana
	 */
	public void makeStageDrageable() {
		parentLogIn.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		parentLogIn.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SecondStage.setX(event.getScreenX() - xOffset);
				SecondStage.setY(event.getScreenY() - yOffset);
				SecondStage.setOpacity(0.7f);
			}
		});
		parentLogIn.setOnDragDone((e) -> {
			SecondStage.setOpacity(1.0f);
		});
		parentLogIn.setOnMouseReleased((e) -> {
			SecondStage.setOpacity(1.0f);
		});
	}

	/**
	 * Cierre de la ventana
	 * 
	 * @param event
	 */
	@FXML
	private void CloseWindow(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * Cambio de ventana para registro
	 */
	@FXML
	private void SignUp(ActionEvent event) {
		SecondStage.close();
		WindowCreator("SignUpWindow");
	}

	/**
	 * Envio de datos al servidor para inicio de sesion
	 */
	@FXML
	private void LogIn(ActionEvent event) {

		if (VerifyAcount.VerifyAcountData(UserField.getText(), PassField.getText()) == true) {

			JOptionPane.showMessageDialog(new JFrame(), "¡Successfully verified account!", "", JOptionPane.INFORMATION_MESSAGE);
			SecondStage.close();

		} else {

			JOptionPane.showMessageDialog(new JFrame(), "Incorrect user or password", "Error", JOptionPane.ERROR_MESSAGE);

		}
	}

}