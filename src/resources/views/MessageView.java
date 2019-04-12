package views;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class MessageView {
    public static Optional<ButtonType> showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        DialogPane dialogPane = message.getDialogPane();
        dialogPane.getStylesheets().add("/themes/ThemeDialogPane.css");
        dialogPane.getStyleClass().remove("alert");

        GridPane grid = (GridPane)dialogPane.lookup(".header-panel");
        grid.setStyle("-fx-background-color: #1A314E;");
        message.setHeaderText(header);
        message.setContentText(text);
        return message.showAndWait();

    }


    public static Optional<ButtonType> showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = message.getDialogPane();
        dialogPane.getStylesheets().add("/themes/ThemeErrorPane.css");
        dialogPane.getStyleClass().remove("alert");

        GridPane grid = (GridPane)dialogPane.lookup(".header-panel");
        grid.setStyle("-fx-background-color: #1A314E;");
        message.setHeaderText("Mesaj eroare");
        message.setContentText(text);
        return message.showAndWait();
    }
}
