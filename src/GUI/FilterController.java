package GUI;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FilterController implements Initializable {

	private Stage SecondStage;

	public void setSecondStage(Stage newSecondStage) {
		this.SecondStage = newSecondStage;
	}
	@FXML
	private AnchorPane parentFilter;
	@FXML
	private Button aceptar;
	@FXML
	private Button cerrar;
	
	/**
	 * Cierre de la ventana
	 * 
	 * @param event
	 */
	@FXML
	private void CloseWindow(ActionEvent event) {
		System.exit(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}