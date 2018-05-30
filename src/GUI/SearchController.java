package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Constructor de la clase encargada de invocar una ventana Busqueda
 *
 * @author deiber
 */
public class SearchController implements Initializable {

	private Stage SecondStage;

	public void setSecondStage(Stage NewSecondStage) {
		this.SecondStage = NewSecondStage;
	}
	
	private double xOffset = 0;
	private double yOffset = 0;
	AnimationGenerator animationGenerator = null;

	@FXML
	private AnchorPane parentSearch;
	@FXML
	private TableView<Table> table;
	@FXML
	private TableColumn<Table, String> Artist;
	@FXML
	private TableColumn<Table, String> Song;
	@FXML
	private TableColumn<Table, String> Album;
	@FXML
	private TableColumn<Table, String> Genre;
	@FXML
	private TableColumn<Table, String> Score;
	@FXML
	private TableColumn<Table, String> Released;
	@FXML
	private TextField filterField;

	public ObservableList<Table> list = FXCollections.observableArrayList(

			new Table("Swichtfoot", "Awakening", "Oh! Gravity", "Rock, Pop", "★★★★★", "2006"),
			new Table("The Paper Kites", "Bloom", "Woodland - EP", "Folk", "★★★⭐⭐", "2011")
	// ⭐⭐⭐⭐⭐⭐⭐★★★★★★★
	);

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		animationGenerator = new AnimationGenerator();
		
		/////////////////////////////////// tabla////////////////////////////////////////
		Artist.setCellValueFactory(new PropertyValueFactory<Table, String>("Artist"));
		Song.setCellValueFactory(new PropertyValueFactory<Table, String>("Song"));
		Album.setCellValueFactory(new PropertyValueFactory<Table, String>("Album"));
		Genre.setCellValueFactory(new PropertyValueFactory<Table, String>("Genre"));
		Score.setCellValueFactory(new PropertyValueFactory<Table, String>("Score"));
		Released.setCellValueFactory(new PropertyValueFactory<Table, String>("Released"));

		FilteredList<Table> DataFiltered = new FilteredList<>(list, p -> true); // Filtrado de tabla
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			DataFiltered.setPredicate(SongData -> {
				// Si el filtro de busqueda esta vacio, muestra todo
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				// Comparaciones
				if (SongData.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (SongData.getSong().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (SongData.getAlbum().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (SongData.getGenre().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (SongData.getScore().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (SongData.getReleased().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		SortedList<Table> ListFiltered = new SortedList<>(DataFiltered);
		ListFiltered.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(ListFiltered);
	}
	
	/**
	 * Creacion de animacion de movimiento de ventana
	 */
	public void makeStageDrageable() {
		parentSearch.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		parentSearch.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SecondStage.setX(event.getScreenX() - xOffset);
				SecondStage.setY(event.getScreenY() - yOffset);
				SecondStage.setOpacity(0.7f);
			}
		});
		parentSearch.setOnDragDone((e) -> {
			SecondStage.setOpacity(1.0f);
		});
		parentSearch.setOnMouseReleased((e) -> {
			SecondStage.setOpacity(1.0f);
		});
	}
	
	/**
	 * Cierre de la ventana
	 * 
	 * @param event
	 */
	@FXML
	private void CloseWindow(MouseEvent event) {
		SecondStage.close();
	}
	
}
