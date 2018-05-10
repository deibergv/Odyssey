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
    
    public static Button SignUpButton;
    @FXML
    private Button BtSignUp;
    @FXML       /// Accion del boton Sign Up ///
    private void SignUp(ActionEvent event) {
    	WindowCreator("SignUpWindow");
//    	SignUpButton.setDisable(true);
    }
    
    public static Button LogInButton;
    @FXML
    private Button BtLogIn;
    @FXML       /// Accion del boton Sign Up ///
    private void LogIn(ActionEvent event) {
//    	WindowCreator("GUI");
//    	LogInButton.setDisable(true);
//    	SignUpButton.setDisable(true);
    }
    
    @FXML       /// Cerrar ventana ///
    private void handleClose(ActionEvent event) {
//    	WindowCreator("SignUpWindow");
//    	SecondStage.close();
//    	PrincipalStage.close();
//    	SecondStage.setTitle("asd");
    	System.exit(0);
//    	WindowCreator("GUI");
//    	WindowDestructor("LogInWindow");
    }
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SignUpButton = BtSignUp;
		LogInButton = BtLogIn;
	}
}