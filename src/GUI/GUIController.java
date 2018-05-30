/**
 * @author deiber
 */

package GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTML.Tag;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
//import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import com.jfoenix.controls.JFXListView;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import streamplayer.StreamPlayer;
import visualizer.Visualizer;

/**
 * @author deiber Controlador de la ventana Principal
 */
public class GUIController implements Initializable {

	Player player = null;
	Visualizer visualizer = null;
	StreamPlayer streamPlayer = null;
	
	private int minsum = 0;

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
	private MaterialDesignIconView PauseAndResumeBt;
	@FXML
	private FontAwesomeIconView RepeatBt;
	@FXML
	private FontAwesomeIconView ShuffleBt;
	@FXML
	private Slider VolumeBar;
	@FXML
	private ProgressBar TimePlaying;
	@FXML
	private Label NameSong;
	@FXML
	private ListView<String> listView;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		Sidebar.setVisible(false);
		animationGenerator = new AnimationGenerator();

		visualizer = new Visualizer("WavesVisualizer");
		WavesView.getChildren().add(visualizer);
		streamPlayer = new StreamPlayer();
		player = new Player(visualizer, streamPlayer);

		listView.setCellFactory(lv -> new ListCellWithContextMenu(this));
		PauseAndResumeBt.setDisable(true);
		
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

	Integer NumberSong = 0;
	@FXML
	Label Duration;
	@FXML
	Label Time;

	/**
	 * Opciones de lista
	 * 
	 * @param index
	 */
	public void PlayListIndex(int index) {
		String SongPath = DataList.get(index);
		player.stopSong();
		player.playSong(new File(SongPath));
		IsPlaying = true;
		String SongName = listView.getItems().get(index);
		NameSong.setText(SongName);
		PauseAndResumeBt.setGlyphName("PAUSE");
		NumberSong = index;
		PauseAndResumeBt.setDisable(false);

		String songname = listView.getItems().get(index);
		Caratula(songname);

		File SongDuration = new File(SongPath);
		try {
			Duration.setText(getDurationWithMp3Spi(SongDuration));
			String time = getDurationWithMp3Spi(SongDuration);
			String[] minsec = time.split(":");
			int min = Integer.parseInt(minsec[0]);
			int sec = Integer.parseInt(minsec[1]);
			int totalSec = sec + min*60;
			minsum = totalSec/10000;
			Duration.setVisible(true);
			//metodo que lleve el tiempo transcurrido
			Time.setVisible(true);
		} catch (Exception e) {
			 System.out.println("Error al conseguir tiempo de duracion: " + e);
		}
		ProgressRegulation.start();
	}

	/**
	 * Metodo utilizado par conseguir la duracion de la cancion
	 * 
	 * @param file
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	private static String getDurationWithMp3Spi(File file) throws UnsupportedAudioFileException, IOException {

		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
		if (fileFormat instanceof TAudioFileFormat) {
			Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
			String key = "duration";
			Long microseconds = (Long) properties.get(key);
			int mili = (int) (microseconds / 1000);
			int sec = (mili / 1000) % 60;
			int min = (mili / 1000) / 60;
			String TimeDuration = (min + ":" + sec);
			return TimeDuration;
		} else {
			throw new UnsupportedAudioFileException();
		}

	}

	public void ListDelete(int index) {
		listView.getItems().remove(index);
	}

	public void OrderList(ListView<String> list) {
		// ventana de seleccion de modo de orden
		// llamar al servidor para que ordene
	}

	public void ListEdit(int index) {
		listView.edit(index);
	}

	/**
	 * Llamado a la ventana de busqueda
	 * 
	 * @param event
	 */
	@FXML
	private void Search(MouseEvent event) {
		WindowCreator.WindowCreator("SearchWindow");
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
	 * 
	 * @param event
	 */
	@FXML
	private void PauseAndResumeSong(MouseEvent event) {
		if (IsPlaying == true) {
			player.pauseSong();
			PauseAndResumeBt.setGlyphName("PLAY");
			IsPlaying = false;
			ProgressRegulation.stop();
		} else if (IsPlaying == false) {
			player.resumeSong();
			PauseAndResumeBt.setGlyphName("PAUSE");
			IsPlaying = true;
			ProgressRegulation.resume();
		}
	}

	/**
	 * Salto a siguiente cancion
	 * 
	 * @param event
	 */
	@FXML
	private void Next(MouseEvent event) {
		if (NumberSong < DataList.size() - 1) {
			NumberSong = NumberSong + 1;
			String SongPath = DataList.get(NumberSong);
			player.stopSong();
			player.playSong(new File(SongPath));
			IsPlaying = true;
			String SongName = listView.getItems().get(NumberSong);
			NameSong.setText(SongName);
			PauseAndResumeBt.setGlyphName("PAUSE");
			listView.getSelectionModel().select(NumberSong);
			listView.getFocusModel().focus(NumberSong);
			listView.scrollTo(NumberSong);
		}
	}

	/**
	 * Retroceso a cancion anterior
	 * 
	 * @param event
	 */
	@FXML
	private void Previous(MouseEvent event) {
		if (NumberSong > 0) {
			NumberSong = NumberSong - 1;
			String SongPath = DataList.get(NumberSong);
			player.stopSong();
			player.playSong(new File(SongPath));
			IsPlaying = true;
			String SongName = listView.getItems().get(NumberSong);
			NameSong.setText(SongName);
			PauseAndResumeBt.setGlyphName("PAUSE");
			listView.getSelectionModel().select(NumberSong);
			listView.getFocusModel().focus(NumberSong);
			listView.scrollTo(NumberSong);
		}
	}

	boolean ShuffleState = false; // Flag Shuffle Button State

	/**
	 * Activacion/Desactivacion del modo aleatorio
	 * 
	 * @param event
	 */
	@FXML
	private void Shuffle(MouseEvent event) { /// hacerlo /////////////////////////////////////

		if (ShuffleState == false) {

			// Activacion del modo aleatorio
			ShuffleState = true;
			ShuffleBt.setFill(Color.ORANGE);

		} else if (ShuffleState == true) {

			// Desactivacion del modo aleatorio
			ShuffleState = false;
			ShuffleBt.setFill(Color.WHITE);

		}
	}

	boolean RepeatState = false; // Flag Repeat Button State

	/**
	 * Activacion/Desactivacion del modo repeticion
	 * 
	 * @param event
	 */
	@FXML
	private void Repeat(MouseEvent event) { /// hacerlo /////////////////////////////////////

		if (RepeatState == false) {

			// Activacion del modo repeticion
			RepeatState = true;
			RepeatBt.setFill(Color.ORANGE);

		} else if (RepeatState == true) {

			// Desactivacion del modo repeticion
			RepeatState = false;
			RepeatBt.setFill(Color.WHITE);

		}
	}

	/**
	 * Regulacion del volumen
	 * 
	 * @param event
	 */
	@FXML
	private void VolumeRegulation(MouseEvent event) { /// hacerlo /////////////////////////////////////

		double VolumeValue = VolumeBar.getValue(); // regulacion
		System.out.println(VolumeValue);
		// player.setVolume(VolumeValue);
		// System.out.println("soy el float: " + (float)VolumeValue);
		// VolumeControl.setVolume((float)VolumeValue);
	}

	private String rute = "/home/deiber/";
	private final JFileChooser abrirFile = new JFileChooser(new File(rute));
	FileNameExtensionFilter filtrado = new FileNameExtensionFilter("Only mp3", "mp3");
	private javax.swing.JPanel ArchiveSearchWindow;
	private File archive = null;
	private AudioFile audiofile = new AudioFile();
	private final ArrayList<String> DataList = new ArrayList<>();

	/**
	 * Agregado de canciones
	 * 
	 * @param event
	 */
	@FXML
	private void SearchDirectory(MouseEvent event) {

		abrirFile.setDialogTitle("Ruta Absoluta Busqueda..");
		abrirFile.setFileFilter(filtrado);
		abrirFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		if (abrirFile.showOpenDialog(ArchiveSearchWindow) == 0) {
			File file = new File("" + abrirFile.getSelectedFile());

			String mp3Comprove = file.getName(); // Obtenemos el nombre del archivo...
			mp3Comprove = mp3Comprove.substring(mp3Comprove.length() - 3, mp3Comprove.length());// Obtenemos los tres

			/**
			 * Comprobacion de existencia del archivo Comprobacion de tipo de archivo .mp3
			 */
			if (file.exists() & mp3Comprove.equals("mp3")) {

				archive = abrirFile.getSelectedFile(); // Ruta del archivo...

				try {
					audiofile = AudioFileIO.read(archive);

				} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
						| InvalidAudioFrameException ex) {
				}
				rute = abrirFile.getCurrentDirectory().toString();

				DataList.add(archive.toString()); // archive.toString() saca el path

				String SongName = archive.getName();
				SongName = SongName.substring(0, SongName.length() - 4);
				listView.getItems().add(SongName);
			}
		}
	}

	@FXML
	ImageView caratula;

	@FXML
	private void Caratula(String namesong) {

		if (namesong.compareTo("Kansas - Dust in the Wind - Point of Know Return") == 0) {
			Image ImageSong = new Image("/GUI/img/Point of Know Return.jpg", 245, 180, false, true);
			caratula.setImage(ImageSong);
		} else if (namesong.compareTo("Switchfoot - Awakening - Oh! Gravity") == 0) {
			Image ImageSong = new Image("/GUI/img/Oh Gravity!.jpg", 245, 180, false, true);
			caratula.setImage(ImageSong);
		} else if (namesong.compareTo("The Lumineers - Ophelia - Cleopatra") == 0) {
			Image ImageSong = new Image("/GUI/img/Cleopatra.jpg", 245, 180, false, true);
			caratula.setImage(ImageSong);
		} else if (namesong.compareTo("The Paper Kites - Bloom - Woodland - EP") == 0) {
			Image ImageSong = new Image("/GUI/img/Woodland - EP.jpeg", 245, 180, false, true);
			caratula.setImage(ImageSong);
		}
	}
	
	/**
	 * Regulacion del progreso de reproduccion de la cancion
	 */
	Thread ProgressRegulation = new Thread(new Runnable() {
		int a = 0;
        public void run() {
    			TimePlaying.setProgress(a);
    			try {
    				Thread.sleep(1000);
    			} catch (Exception e) {
    				
    			}
    			a+= minsum;
    			
        }
   });  


	// private final BasicPlayer Audio = new BasicPlayer();
	//
	// public void basic_playerlistener() {
	// Audio.addBasicPlayerListener(new BasicPlayerListener() {
	// @Override // Este metodo se cumple cuando abrimos la cancion...
	// public void opened(Object o, Map map) {
	// // System.out.println(map);
	//
	// // LLamamos al metodo para que nos imprima el tiempo de duracion de la
	// // cancion....
	// CalculoSecundero(map.get("duration").toString(), "Duracion: ", jLabelTiempo);
	//
	// new JLaTexto(fuente1, "Tasa de bits: " + map.get("bitrate"), jLabelBitrate,
	// c, 15);
	// new JLaTexto(fuente1, "Velocidad Muestreo: " + map.get("mp3.frequency.hz"),
	// jLabelFRate, c, 15);
	//
	// jSliderProgresoMp3.setMaximum(Integer.parseInt(map.get("mp3.length.bytes").toString()));
	// jSliderProgresoMp3.setMinimum(0);
	// }
	//
	// @Override // Este metodo se cumple cuando la cancion esta en progreso....
	// public void progress(int i, long l, byte[] bytes, Map propiedades) {
	//
	// // LLamamos al este metodo que nos calcula el tiempo trancurrido...
	// CalculoSecundero(propiedades.get("mp3.position.microseconds").toString(),
	// "Transcurrido: ",
	// jLabelTranscurrido);
	//
	// Object bytesTranscurrido = propiedades.get("mp3.position.byte");
	// bytesTranscurrido = Integer.parseInt(bytesTranscurrido.toString());
	// jSliderProgresoMp3.setValue((int) bytesTranscurrido);
	// }
	//
	// @Override
	// public void stateUpdated(BasicPlayerEvent bpe) {
	//
	// if (!bloquear) {
	// if (Audio.getStatus() == 2 & repitaCancion) {
	// jButtonReproducir.doClick();
	// }
	// if (jListListaCanciones.getSelectedIndex() + 1 != agregaCanciones.length) {
	// if (Audio.getStatus() == 2 & siguiente) {
	// int pista = jListListaCanciones.getAnchorSelectionIndex();
	// jListListaCanciones.setSelectedIndex(pista + 1);
	// repaint();
	// jButtonReproducir.doClick();
	// }
	// }
	// }
	// }
	//
	// @Override
	// public void setController(BasicController bc) {
	//
	// }
	// });
	//
	// }


}