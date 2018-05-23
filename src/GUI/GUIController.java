/**
 * @author deiber
 */

package GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import streamplayer.StreamPlayer;
import visualizer.Visualizer;

/**
 * @author deiber Controlador de la ventana Principal
 */
public class GUIController implements Initializable {

	Player player = null;
	Visualizer visualizer = null;
	StreamPlayer streamPlayer = null;

	private double xOffset = 0;
	private double yOffset = 0;
	private static final int DEFAULT_STARTING_X_POSITION = 0;
	private static final int DEFAULT_ENDING_X_POSITION = -120;
	AnimationGenerator animationGenerator = null;

	@FXML
	private HBox parent;
	@FXML
	private Pane Sidebar;
	@FXML
	private StackPane WavesView;
	@FXML
	MaterialDesignIconView PauseAndResumeBt;
	@FXML
	FontAwesomeIconView SuffleBt;
	@FXML
	FontAwesomeIconView RepeatBt;
	@FXML
	Slider VolumeBar;
	@FXML
	ProgressBar TimePlaying;
	@FXML
	Label NameSong;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		Sidebar.setVisible(false);
		animationGenerator = new AnimationGenerator();

		visualizer = new Visualizer("WavesVisualizer");
		WavesView.getChildren().add(visualizer);
		streamPlayer = new StreamPlayer();
		player = new Player(visualizer, streamPlayer);

	}

	/**
	 * Creacion de animacion de movimiento de ventana
	 */
	public void makeStageDrageable() {
		parent.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Launch.PrincipalStage.setX(event.getScreenX() - xOffset);
				Launch.PrincipalStage.setY(event.getScreenY() - yOffset);
				Launch.PrincipalStage.setOpacity(0.7f);
			}
		});
		parent.setOnDragDone((e) -> {
			Launch.PrincipalStage.setOpacity(1.0f);
		});
		parent.setOnMouseReleased((e) -> {
			Launch.PrincipalStage.setOpacity(1.0f);
		});
	}

	/**
	 * Apertura y cierre de menu
	 * 
	 * @param event
	 */
	@FXML
	private void Open_or_Close_SideBar(MouseEvent event) {
		if (!Sidebar.isVisible()) {
			Sidebar.setVisible(true);
			animationGenerator.applyTranslateAnimationOn(Sidebar, 500, DEFAULT_ENDING_X_POSITION,
					DEFAULT_STARTING_X_POSITION);
			animationGenerator.applyFadeAnimationOn(Sidebar, 3000, 0f, 1.0f, null);
		} else {
			animationGenerator.applyFadeAnimationOn(Sidebar, 2000, 1.0f, 0.0f, (e) -> {
				Sidebar.setVisible(false);
			});
		}
	}

	/**
	 * Cierre del programa
	 * 
	 * @param event
	 */
	@FXML
	private void Close_app(MouseEvent event) {
		System.exit(0);
	}

	boolean IsPlaying = false; // Flag playing state

	/**
	 * Pausa o resumen de reproduccion
	 * @param event
	 */
	@FXML
	private void PauseAndResumeSong(MouseEvent event) {
		if (IsPlaying == true) {
			player.pauseSong();
			PauseAndResumeBt.setGlyphName("PLAY");
			IsPlaying = false;
		} else if (IsPlaying == false) {
			player.resumeSong();
			PauseAndResumeBt.setGlyphName("PAUSE");
			IsPlaying = true;
		}
	}

	boolean Changesong = true; // regulacion de cancion		// Borrar luego

	/**
	 * Salto a siguiente cancion
	 * @param event
	 */
	@FXML
	private void Next(MouseEvent event) {		/// hacerlo			/////////////////////////////////////
		if (Changesong == true) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Switchfoot - Awakening.mp3"));

			PauseAndResumeBt.setGlyphName("PAUSE");
			IsPlaying = true;
			Changesong = false;
			//
		} else if (Changesong == false) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Reckless Love.mp3"));
			Changesong = true;
		}
	}

	/**
	 * Retroceso a cancion anterior
	 * @param event
	 */
	@FXML
	private void Previous(MouseEvent event) {		/// hacerlo			/////////////////////////////////////
		if (Changesong == true) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Switchfoot - Awakening.mp3"));

			PauseAndResumeBt.setGlyphName("PAUSE");
			IsPlaying = true;
			Changesong = false;
			//
		} else if (Changesong == false) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Reckless Love.mp3"));
			Changesong = true;
		}
	}
	
	boolean ShuffleState = false;	// Flag Shuffle Button State

	/**
	 * Activacion/Desactivacion del modo aleatorio
	 * @param event
	 */
	@FXML
	private void Shuffle(MouseEvent event) {		/// hacerlo			/////////////////////////////////////
		if (ShuffleState = false) {
			ShuffleState = true;
			// SuffleBt. // cambiar color a naranja luego de ser presionado
			// hacer aleatoria la lista
		} else if (ShuffleState = true) {
			ShuffleState = false;
			// hacer que se desactive (cambio de color), y tratar de devolver orden a la
			// lista
		}
	}

	boolean RepeatState = false; // Flag Repeat Button State

	/**
	 * Activacion/Desactivacion del modo repeticion
	 * @param event
	 */
	@FXML
	private void Repeat(MouseEvent event) {		/// hacerlo			/////////////////////////////////////
		if (RepeatState = false) {
			RepeatState = true;
			// RepeatBt. // cambiar color a naranja luego de ser presionado
			// hacer que se repita la cancion
		} else if (RepeatState = true) {
			RepeatState = false;
			// hacer que se desactive (cambio de color), y hacer que se desactive la
			// repeticion
		}
	}
	
	/**
	 * Regulacion del volumen
	 */
	@FXML
	private void VolumeRegulation() {		/// hacerlo			/////////////////////////////////////
//		VolumeBar.					// regulacion
	}
	
	/* 
	 * FALTA:
	 * 	-cambio de nombre de cancion
	 *  -barra de reproduccion
	 *  -Ventana de busqueda (artista, nombre de cancion, album, letra, genero, puntuacion, año)
	 *  -Ventana de la otra busqueda (ahorcado raro)
	 *  -Playlist
	 *  -Aleatoriedad
	 *  -Repeticion
	 *  -Volumen
	 *  -Barra de reproduccion
	 *  -Arreglar Next y Previous
	 */
	
}