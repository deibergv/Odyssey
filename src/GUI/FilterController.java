package GUI;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
	@FXML
	private RadioButton artista;
	@FXML
	private RadioButton cancion;
	@FXML
	private RadioButton album;
	@FXML
	private RadioButton letra;
	
	/**
	 * Cierre de la ventana
	 * 
	 * @param event
	 */
	@FXML
	private void CloseWindow(ActionEvent event) {
		SecondStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		final ToggleGroup group = new ToggleGroup();
		
		artista.setToggleGroup(group);
		artista.setSelected(true);
		cancion.setToggleGroup(group);
		album.setToggleGroup(group);
//		letra.setToggleGroup(group);
	}
	
	
}