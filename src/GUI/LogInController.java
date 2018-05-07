package GUI;

import static GUI.WindowCreator.WindowCreator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * Constructor de la clase de la Ventana Log In para inicio de secion
 *
 * @author deiber
 */
public class LogInController implements Initializable {

	private Stage PrincipalStage;
    public void setPrincipalStage(Stage stagePrincipal) {
        this.PrincipalStage = stagePrincipal;
    }
    
    public static Button SignUpButton;
    @FXML
    private Button BtSignUp;
    
    @FXML       /// Accion del boton Sign Up///
    private void SignUp(ActionEvent event) {
    	WindowCreator("SignUpWindow");
    	//SignUpButton.setDisable(false); //// Desactiva el boton ///
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SignUpButton = BtSignUp;
	}
}