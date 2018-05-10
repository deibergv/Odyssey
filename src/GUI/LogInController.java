package GUI;

import static GUI.WindowCreator.WindowCreator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Constructor de la clase de la Ventana Log In
 *
 * @author deiber
 */
public class LogInController implements Initializable {

	private Stage SecondStage;

	public void setSecondStage(Stage newSecondStage) {
		this.SecondStage = newSecondStage;
	}

	@FXML /// Cerrar ventana ///
	private void handleClose(ActionEvent event) {
		SecondStage.close();
	}
	
	public static Button SignUpButton;
	@FXML
	private Button BtSignUp;

	@FXML /// Accion del boton Sign Up, cambio de ventana ///
	private void SignUp(ActionEvent event) {
		SecondStage.close();
		WindowCreator("SignUpWindow");
	}

	public static Button LogInButton;
	@FXML
	private Button BtLogIn;

	@FXML /// Accion del boton Log In ///
	private void LogIn(ActionEvent event) {
//		SecondStage.close(); // if inicia sesion de forma correcta, cierra ventana y sigue
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SignUpButton = BtSignUp;
		LogInButton = BtLogIn;
	}
}