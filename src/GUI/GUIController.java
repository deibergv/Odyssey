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
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
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

	Thread ProgressRegulation = null;

	Player player = null;
	Visualizer visualizer = null;
	StreamPlayer streamPlayer = null;

	private double minsum = 0.0;
	private int totalsec = 0;
	private int secondsR = 0;

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
		IsPlaying = false;
		try {
			if (ProgressRegulation != null) {
				if (ProgressRegulation.isAlive()) {
					ProgressRegulation.join();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ProgressRegulation = null;
		totalsec = 0;
		secondsR = 0;
		TimePlaying.setProgress(0);

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

		File SongDuration = new File(SongPath);
		try {
			Duration.setText(getDurationWithMp3Spi(SongDuration));
			String time = getDurationWithMp3Spi(SongDuration);
			String[] minsec = time.split(":");
			int min = Integer.parseInt(minsec[0]);
			int sec = Integer.parseInt(minsec[1]);
			totalsec = sec + min * 60;
			// minsum = (double)secondsR/totalSec/100;
			Duration.setVisible(true);
			// metodo que lleve el tiempo transcurrido
			Time.setVisible(true);
			ProgressRegulation = new Thread(new Runnable() {
				public void run() {
					while (IsPlaying) {
						String TimeTrans = String.valueOf((int) secondsR / 60) + ":" + String.valueOf(secondsR % 60);
						// Time.setText(TimeTrans);
						System.out.println(TimeTrans);
						minsum = (double) secondsR / totalsec;
						TimePlaying.setProgress(minsum);
						secondsR++;
						try {
							Thread.sleep(1000);
						} catch (Exception e) {

						}
					}
				}
			});
			ProgressRegulation.start();
		} catch (Exception e) {
			System.out.println("Error al conseguir tiempo de duracion: " + e);
		}
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
			try {
				if (ProgressRegulation.isAlive()) {
					ProgressRegulation.join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (IsPlaying == false) {
			player.resumeSong();
			PauseAndResumeBt.setGlyphName("PAUSE");
			IsPlaying = true;

			ProgressRegulation = new Thread(new Runnable() {
				public void run() {
					while (IsPlaying) {
						minsum = (double) secondsR / totalsec;
						TimePlaying.setProgress(minsum);
						secondsR++;
						try {
							Thread.sleep(1000);
						} catch (Exception e) {

						}
					}
				}
			});
			ProgressRegulation.start();
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
			PlayListIndex(NumberSong);
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
			PlayListIndex(NumberSong);
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

				String Song = archive.getName();
				Song = Song.substring(0, Song.length() - 4);
				listView.getItems().add(Song);
				String crude = Base64MP3.encodeToB64(archive.toString());
				SongName.Song(archive.getName(), "Point of Know Return", "1977", crude.substring(1, 100), "Soft Rock", "asd");
			}
		}
	}
}