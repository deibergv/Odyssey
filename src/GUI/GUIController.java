package GUI;

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
import static GUI.WindowCreator.WindowCreator;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Constructor de la clase de la Ventana principal
 *
 * @author deiber
 */
public class GUIController implements Initializable {

    private Scene PrincipalProgram;
    public void setPrincipalProgram(Scene scene) {
        this.PrincipalProgram = scene;
    }
	
    public static Button LogInButton;
    @FXML
    private Button BtLogIn;
    
    
    @FXML
    private Button kk;
    @FXML       /// Cerrar ventana ///
    private void handleClose(ActionEvent event) {
    	System.exit(0);
    }
    
    @FXML       /// Accion del boton Log In///
    private void LogIn(ActionEvent event) {
    	WindowCreator("LogInWindow");
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LogInButton = BtLogIn;
	}

}
