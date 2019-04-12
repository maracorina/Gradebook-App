package Utils;

import Domain.Nota;
import Domain.NotaDTO;
import Domain.NotaDTOTableEditable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class TableViewSample{

    public static void table(List<NotaDTOTableEditable> newdata) {
        Stage stage=new Stage();
        TableView<NotaDTOTableEditable> table = new TableView<>();
        ObservableList<NotaDTOTableEditable> data =
                FXCollections.observableArrayList(
                        new NotaDTOTableEditable("Corina", "8", ""),
                        new NotaDTOTableEditable("Bogdan", "1", "") );
        HBox hb = new HBox();

        Scene scene = new Scene(new Group());
        stage.setTitle("Add table");
        stage.setWidth(650);
        stage.setHeight(550);

        final Label label = new Label("Group grades");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        Callback<TableColumn<NotaDTOTableEditable, String>, TableCell<NotaDTOTableEditable, String>> cellFactory
                = (TableColumn<NotaDTOTableEditable, String> p) -> new EditingCell();

        TableColumn<NotaDTOTableEditable, String> nameCol =
                new TableColumn<>("Name");
        TableColumn<NotaDTOTableEditable, String> gradeCol =
                new TableColumn<>("Nota");
        TableColumn<NotaDTOTableEditable, String> feedbackCol =
                new TableColumn<>("Feedback");

        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("studentName"));

        gradeCol.setMinWidth(100);
        gradeCol.setCellValueFactory(
                new PropertyValueFactory<>("nota"));

        feedbackCol.setMinWidth(200);
        feedbackCol.setCellValueFactory(
                new PropertyValueFactory<>("feedback"));

        gradeCol.setCellFactory(cellFactory);
        feedbackCol.setCellFactory(cellFactory);
        gradeCol.setOnEditCommit(
                (CellEditEvent<NotaDTOTableEditable, String> t) -> {
                    ((NotaDTOTableEditable) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setNota(t.getNewValue());;
                });

        feedbackCol.setOnEditCommit(
                (CellEditEvent<NotaDTOTableEditable, String> t) -> {
                    TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();

// Item here is the table view type:
                    NotaDTOTableEditable item = table.getItems().get(row);

                    TableColumn col = nameCol;
                    TableColumn colN = gradeCol;
// this gives the value in the selected cell:
                    String dataN = (String) col.getCellObservableValue(item).getValue();
                    String data2 = (String) colN.getCellObservableValue(item).getValue();
                    ((NotaDTOTableEditable) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setNota(t.getNewValue());
                    newdata.add(new NotaDTOTableEditable(dataN, data2, t.getNewValue()));
                });


        table.setItems(data);
        table.getColumns().addAll(nameCol, gradeCol, feedbackCol);

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("Nume");
        addFirstName.setMaxWidth(nameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(gradeCol.getPrefWidth());
        addLastName.setPromptText("Nota");


        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            data.add(new NotaDTOTableEditable(
                    addFirstName.getText(), addLastName.getText(), ""));
            newdata.add(new NotaDTOTableEditable(
                    addFirstName.getText(), addLastName.getText(), ""));
            addFirstName.clear();
            addLastName.clear();
        });

        hb.getChildren().addAll(addFirstName, addLastName);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
