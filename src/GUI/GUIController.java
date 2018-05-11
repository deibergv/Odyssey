/**
 * @author deiber
 */

package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * @author deiber Controlador de la ventana Principal
 */
public class GUIController implements Initializable {

	private double xOffset = 0;
	private double yOffset = 0;
	private static final int DEFAULT_STARTING_X_POSITION = 0;
	private static final int DEFAULT_ENDING_X_POSITION = -120;
	AnimationGenerator animationGenerator = null;

	@FXML
	private HBox parent;
	@FXML
	private Pane Sidebar;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDrageable();
		Sidebar.setVisible(false);
		animationGenerator = new AnimationGenerator();
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
}