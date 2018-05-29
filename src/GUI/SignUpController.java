/**
 * @author deiber
 */
package GUI;

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

	private double xOffset = 0;
	private double yOffset = 0;
	AnimationGenerator animationGenerator = null;

	@FXML
	AnchorPane parentSignUp;
	@FXML
	JFXTextField UserField;
	@FXML
	JFXTextField Name;
	@FXML
	JFXTextField Age;
	@FXML
	JFXTextField FavoriteGenres;
	@FXML
	JFXPasswordField PassField;
//	@FXML
//	JFXTextField Friends;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		animationGenerator = new AnimationGenerator();

	}

	/**
	 * Creacion de animacion de movimiento de ventana
	 */
	public void makeStageDrageable() {
		parentSignUp.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		parentSignUp.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SecondStage.setX(event.getScreenX() - xOffset);
				SecondStage.setY(event.getScreenY() - yOffset);
				SecondStage.setOpacity(0.7f);
			}
		});
		parentSignUp.setOnDragDone((e) -> {
			SecondStage.setOpacity(1.0f);
		});
		parentSignUp.setOnMouseReleased((e) -> {
			SecondStage.setOpacity(1.0f);
		});
	}

	/**
	 * Cierre de la ventana y cambio a Log In
	 * 
	 * @param event
	 */
	@FXML
	private void CloseWindow(ActionEvent event) {
		SecondStage.close();
		WindowCreator.WindowCreator("LogInWindow");
	}

	/**
	 * Cambio de ventana a Log In para inicio de sesion
	 */
	@FXML
	private void LogIn(ActionEvent event) {

		SecondStage.close();
		WindowCreator.WindowCreator("LogInWindow");
	}

	/**
	 * Envio de datos al servidor para registro
	 */
	@FXML
	private void SignUp(ActionEvent event) {

		if (CreateAcount.CreateAcountData(UserField.getText(), Name.getText(), Age.getText(),
				FavoriteGenres.getText(), PassField.getText()) == true) {//, Friends.getText()) == true) {
			
			JOptionPane.showMessageDialog(new JFrame(), "¡Account created successfully!", "", JOptionPane.INFORMATION_MESSAGE);
			SecondStage.close();
			WindowCreator.WindowCreator("LogInWindow");

		} else {

			JOptionPane.showMessageDialog(new JFrame(), "User already exists", "Error", JOptionPane.ERROR_MESSAGE);

		}
	}

}