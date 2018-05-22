package GUI;

import java.io.File;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import streamplayer.StreamPlayer;
import streamplayer.Status;
import streamplayer.StreamPlayerEvent;
import streamplayer.StreamPlayerException;
import streamplayer.StreamPlayerListener;
import visualizer.Visualizer;

public class Player extends StackPane implements StreamPlayerListener {

	Visualizer		visualizer		= new Visualizer("Example Visualizer");
	StreamPlayer	streamPlayer	= new StreamPlayer();

	/**
	 * Constructor
	 * @param visualizer 
	 * @param streamPlayer 
	 */
	public Player(Visualizer visualizer, StreamPlayer streamPlayer) {
//		setStyle("-fx-background-color:black;");
//		getChildren().add(visualizer);

		this.visualizer = visualizer;
		this.streamPlayer = streamPlayer;
		
		// Add the Listener to the Player
		streamPlayer.addStreamPlayerListener(this);

	}

	/**
	 * An example method of how to play a song with StreamPlayer
	 * 
	 * @param path
	 */
	public void playSong(File file) {
		try {
			// ----------------------Open the Media
			System.out.println("Opening ...");
			streamPlayer.open(file);

			// ---------------------- Play the Media
			System.out.println("Starting to play ...");
			streamPlayer.play();
			
//			visualizer.setShowFPS(true);

		} catch (StreamPlayerException e) {
			e.printStackTrace();
		}
	}

	public void pauseSong() {
		// ---------------------- Pause the Media
		System.out.println("pausing ...");
		streamPlayer.pause();
	}
	
	public void resumeSong() {
		// ---------------------- Pause the Media
		System.out.println("resuming ...");
		streamPlayer.pause();
	}
	
	public void stopSong() {
		System.out.println("stopping ...");
		streamPlayer.stop();
	}
	
	
	@Override
	public void opened(Object dataSource, Map<String, Object> properties) {

	}

	@Override
	public void progress(int nEncodedBytes, long microsecondPosition, byte[] pcmData, Map<String, Object> properties) {
		// write the data to the visualizer
		visualizer.writeDSP(pcmData);

	}

	@Override
	public void statusUpdated(StreamPlayerEvent event) {
		System.out.println("Player Status is:" + streamPlayer.getStatus());

		// player is opened
		if (event.getPlayerStatus() == Status.OPENED && streamPlayer.getSourceDataLine() != null) {

			visualizer.setupDSP(streamPlayer.getSourceDataLine());
			visualizer.startDSP(streamPlayer.getSourceDataLine());

			Platform.runLater(() -> visualizer.startVisualizerPainter());
			
			// player is stopped
		} else if (event.getPlayerStatus() == Status.STOPPED) {

			visualizer.stopDSP();

			Platform.runLater(() -> visualizer.stopVisualizerPainter());

		}
	}

}
