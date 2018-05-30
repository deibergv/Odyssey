package GUI;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldListCell;

/**
 * @author deiber
 *
 */
public class ListCellWithContextMenu extends TextFieldListCell<String> {

    private final ContextMenu contextMenu ;

    public ListCellWithContextMenu(GUIController controller) {
        contextMenu = new ContextMenu();
        MenuItem play = new MenuItem("Play song");
        MenuItem edit = new MenuItem("Edit song data");
        MenuItem order = new MenuItem("Order list");
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(play, edit, order, delete);

        play.setOnAction(e -> controller.PlayListIndex(getIndex()));
        edit.setOnAction(e -> controller.ListEdit(getIndex()));
        order.setOnAction(e -> controller.OrderList(getListView()));
        delete.setOnAction(e -> controller.ListDelete(getIndex()));

        setConverter(TextFormatter.IDENTITY_STRING_CONVERTER);

    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setContextMenu(null);
        } else {
            setContextMenu(contextMenu);
        }
    }
}
