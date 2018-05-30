/**
 * @author deiber
 */

package GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	FontAwesomeIconView RepeatBt;
	@FXML
	FontAwesomeIconView ShuffleBt;
	@FXML
	Slider VolumeBar;
	@FXML
	ProgressBar TimePlaying;
	@FXML
	Label NameSong;

	//por si es asi que se le dan las opciones de click de derecho a la lista de canciones
	@FXML
	private ListView<String> listView;
//	private javax.swing.JList<String> jListListaCanciones;
	@FXML
	private JFXListView<String> listViewJFX;
	
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
		} else if (IsPlaying == false) {
			player.resumeSong();
			PauseAndResumeBt.setGlyphName("PAUSE");
			IsPlaying = true;
		}
	}

	boolean Changesong = true; // regulacion de cancion // Borrar luego

	/**
	 * Salto a siguiente cancion
	 * 
	 * @param event
	 */
	@FXML
	private void Next(MouseEvent event) { /// hacerlo /////////////////////////////////////
		if (Changesong == true) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Songs/Switchfoot - Awakening.mp3"));
			NameSong.setText("Switchfoot - Awakening");
			
			PauseAndResumeBt.setGlyphName("PAUSE");		// hacer if que compruebe si el boton está en play o pause
			IsPlaying = true;
			Changesong = false;
			//
		} else if (Changesong == false) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Songs/The Paper Kites - Bloom.mp3"));
			Changesong = true;
			NameSong.setText("Switchfoot - Awakening");
		}
	}

	/**
	 * Retroceso a cancion anterior
	 * 
	 * @param event
	 */
	@FXML
	private void Previous(MouseEvent event) { /// hacerlo /////////////////////////////////////
		if (Changesong == true) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Songs/Switchfoot - Awakening.mp3"));

			PauseAndResumeBt.setGlyphName("PAUSE");
			IsPlaying = true;
			Changesong = false;
			//
		} else if (Changesong == false) {
			player.stopSong();
			player.playSong(new File("/home/deiber/Desktop/Songs/The Paper Kites - Bloom.mp3"));
			Changesong = true;
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
//		player.setVolume(new File("/home/deiber/Desktop/Songs/Switchfoot - Awakening.mp3"), a);
		player.setVolume(VolumeValue);
		// System.out.println(a);
		// if (a == 0.0) {
		// // player.mute(true);
		//// player.mute(true);
		// }
	}
	
	private String rute = "/home/deiber/";
    private final JFileChooser abrirFile  = new JFileChooser(new File(rute));
    FileNameExtensionFilter filtrado = new FileNameExtensionFilter("Only mp3","mp3");
    private javax.swing.JPanel ArchiveSearchWindow;
    private File archive= null;
//    private Tag tag;
    private  AudioFile audiofile = new AudioFile();
//    private final String Font1="Georgia";
    private final ArrayList<String> DataList = new ArrayList<>();
    private String AddSongs[]= new String[10];
    private javax.swing.JList<String> jListListaCanciones;
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
        
        
        if (abrirFile.showOpenDialog(ArchiveSearchWindow)==0){
            File file = new File(""+abrirFile.getSelectedFile());        
            
            String mp3Comprove = file.getName(); //Obtenemos el nombre del archivo...
            mp3Comprove = mp3Comprove.substring(mp3Comprove.length()-3,mp3Comprove.length());//Obtenemos los tres ultimos caracteres de la cadena de nombre...
            
            /**
             * Comprobacion de existencia del archivo
             * Comprobacion de tipo de archivo .mp3
             */
            if (file.exists()& mp3Comprove.equals("mp3")){
                
                archive = abrirFile.getSelectedFile(); //Ruta del archivo..
                                 
                try {
                    audiofile = AudioFileIO.read(archive);
//                    tag =  audiofile.getTag();
     
                } catch (CannotReadException | IOException | TagException | 
                        ReadOnlyFileException | InvalidAudioFrameException ex) {
                }                     
                rute = abrirFile.getCurrentDirectory().toString();
//                new JLaTexto(Font1, "Ruta: "+ rute,jLabelRuta , Color.WHITE, 15);
              
                DataList.add(archive.toString());
                
                AddSongs = new String[DataList.size()];
              
                int x=0;
                for (String cancion:DataList){
                    File introduce = new File(cancion);
                    AddSongs[x] = introduce.getName();
                    x++;
                }
                jListListaCanciones.setListData(AddSongs);
//                listView.set
//                listViewJFX.
//                JLaEtiquetas(archive);
            }            
        }       
    }
	
	/**
	 * Regulacion del progreso de reproduccion de la cancion
	 */
	@FXML
	private void ProgressRegulation() {
//		public void basic_playerlistener(){
//	        Audio.addBasicPlayerListener(new BasicPlayerListener() {
//	            @Override//Este metodo se cumple cuando abrimos la cancion...
//	            public void opened(Object o, Map map) {
//	               //System.out.println(map);
//	               
//	               //LLamamos al metodo para que nos imprima el tiempo de duracion de la cancion....
//	               CalculoSecundero(map.get("duration").toString(), "Duracion: ", jLabelTiempo);
//	               
//	               new JLaTexto(fuente1, "Tasa de bits: "+map.get("bitrate"), jLabelBitrate, c, 15);
//	               new JLaTexto(fuente1, "Velocidad Muestreo: "+map.get("mp3.frequency.hz"), jLabelFRate, c, 15);
//
//	               jSliderProgresoMp3.setMaximum(Integer.parseInt(map.get("mp3.length.bytes").toString()));
//	               jSliderProgresoMp3.setMinimum(0);
//	            }
//
//	            @Override//Este metodo se cumple cuando la cancion esta en progreso....
//	            public void progress(int i, long l, byte[] bytes, Map propiedades) {				
//	              
//	                //LLamamos al este metodo que nos calcula el tiempo trancurrido...
//	                CalculoSecundero(propiedades.get("mp3.position.microseconds").toString(), "Transcurrido: ", jLabelTranscurrido);
//
//	                Object bytesTranscurrido =  propiedades.get("mp3.position.byte");
//	                bytesTranscurrido= Integer.parseInt(bytesTranscurrido.toString());               
//	                jSliderProgresoMp3.setValue((int)bytesTranscurrido);                     
//	            }
//
//	            @Override
//	            public void stateUpdated(BasicPlayerEvent bpe) {
//	                    
//	                if (!bloquear){
//	                    if (Audio.getStatus()==2 & repitaCancion){
//	                        jButtonReproducir.doClick();
//	                    }
//	                    if (jListListaCanciones.getSelectedIndex()+1!=agregaCanciones.length){
//	                        if (Audio.getStatus()==2 & siguiente){
//	                            int pista = jListListaCanciones.getAnchorSelectionIndex();                            
//	                            jListListaCanciones.setSelectedIndex(pista+1);
//	                            repaint();
//	                            jButtonReproducir.doClick();
//	                        }
//	                    }
//	                }                
//	            }
//
//	            @Override
//	            public void setController(BasicController bc) {
//	                
//	            }
//	        });
//	        
//	    }
	}
	
}