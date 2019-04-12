package views;

import Controller.MainController;
import Controller.TaskController;
import Domain.TemaLaborator;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TaskView {
    private TaskController ctrl;
    private Scene scene;
    private BorderPane root;

    public TextField textFieldId;
    public TextField textFieldDescriere;
    public TextField textFieldStart;
    public TextField textFieldDeadline;


    public TaskView(TaskController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    private void initView() {
        root = new BorderPane();

        scene = new Scene(root, 1024, 512);

        textFieldId=new TextField();
        textFieldDescriere=new TextField();
        textFieldStart=new TextField();
        textFieldDeadline=new TextField();

        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
        scene.getStylesheets().add("/themes/Theme.css");

        root.setCenter(initCenter());
    }


    public Scene getView(){ return scene;}


    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridPane=createGridPane();

        HBox buttonsGroups=createBottomButtons();
        centerAnchorPane.getChildren().add(buttonsGroups);
        AnchorPane.setBottomAnchor(buttonsGroups,20d);
        AnchorPane.setLeftAnchor(buttonsGroups,330d);

        centerAnchorPane.getChildren().add(gridPane);
        AnchorPane.setLeftAnchor(gridPane,330d);
        AnchorPane.setBottomAnchor(gridPane, 200d);

        HBox buttonsGroups2=createButtons();
        centerAnchorPane.getChildren().add(buttonsGroups2);
        AnchorPane.setBottomAnchor(buttonsGroups2,145d);
        AnchorPane.setLeftAnchor(buttonsGroups2,400d);

        return centerAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneMessageDetails=new GridPane();
        gridPaneMessageDetails.setPadding(new Insets(20, 20, 20, 20));

        gridPaneMessageDetails.setHgap(20);
        gridPaneMessageDetails.setVgap(20);

        Label labelId= createLabel("ID: ");
        Label labelDescriere= createLabel("Descriere: ");
        Label labelStart=createLabel("Start: ");
        Label labelDeadline=createLabel("Deadline: ");

        gridPaneMessageDetails.add(labelId,0,0);
        gridPaneMessageDetails.add(labelDescriere,0,1);
        gridPaneMessageDetails.add(labelStart,0,2);
        gridPaneMessageDetails.add(labelDeadline,0,3);

        gridPaneMessageDetails.add(textFieldId,1,0);
        gridPaneMessageDetails.add(textFieldDescriere,1,1);
        gridPaneMessageDetails.add(textFieldStart,1,2);
        gridPaneMessageDetails.add(textFieldDeadline,1,3);

        ColumnConstraints c1=new ColumnConstraints();
        ColumnConstraints c2=new ColumnConstraints();
        c2.setPrefWidth(200d);
        gridPaneMessageDetails.getColumnConstraints().addAll(c1,c2);

        return gridPaneMessageDetails;
    }

    private Label createLabel(String s){
        Label l=new Label(s);
        l.setFont(new Font("Futena", 25));
        l.setTextFill(Color.WHITE);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }

    private Button createButton(String s){
        Button b=new Button(s);
        b.getStylesheets().add("/themes/ThemeButton.css");
        return b;
    }

    public HBox createBottomButtons(){
        Image image = new Image(getClass().getResourceAsStream("/images/student.png"));
        ImageView imageView = new ImageView(image);

        Button button = new Button("", imageView);
        button.getStylesheets().add("/themes/Teme2Button.css");
        AnchorPane.setLeftAnchor(button,300d);
        AnchorPane.setBottomAnchor(button,15d);

        Image image2 = new Image(getClass().getResourceAsStream("/images/grade.png"));
        ImageView imageView2 = new ImageView(image2);

        Button button2 = new Button("", imageView2);
        button2.getStylesheets().add("/themes/Teme2Button.css");
        AnchorPane.setLeftAnchor(button2,440d);
        AnchorPane.setBottomAnchor(button2,15d);

        Image image3 = new Image(getClass().getResourceAsStream("/images/task.png"));
        ImageView imageView3 = new ImageView(image3);

        Button button3 = new Button("", imageView3);
        button3.getStylesheets().add("/themes/Teme2Button.css");
        AnchorPane.setLeftAnchor(button3,580d);
        AnchorPane.setBottomAnchor(button3,15d);

        HBox hb=new HBox(50, button,button2, button3);
        button.setOnAction(MainController::handleSetStudentView);
        button2.setOnAction(MainController::handleSetGradeView);
        button3.setOnAction(MainController::handleSetTaskView);
        return hb;

    }

    public HBox createButtons(){
        Button buttonAdd=createButton("Add");
        buttonAdd.setPrefSize(60, 50);
        Button buttonUpdate=createButton("Update deadline");
        buttonUpdate.setPrefSize(110, 50);


        HBox hb=new HBox(50, buttonAdd,buttonUpdate);
        buttonAdd.setOnAction(ctrl::handleAddTask);
        buttonUpdate.setOnAction(ctrl::handleUpdateDeadlineTask);
        return hb;

    }

    public void showUpdateWindow(){
        Stage stage = new Stage();

        AnchorPane root=new AnchorPane();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);

        stage.setHeight(scene.getHeight());
        stage.setWidth(scene.getWidth());

        TableView table=createTaskTable();
        root.getChildren().add(table);

        scene.getStylesheets().add("/themes/UpdateTask.css");
        stage.setTitle("Update deadlines");
        AnchorPane.setBottomAnchor(table,100d);
        AnchorPane.setLeftAnchor(table,120d);
        stage.show();
    }

    private TableView<TemaLaborator> createTaskTable() {
        TableView tableView=new TableView<>();

        TableColumn<TemaLaborator,String> descriereColumn=new TableColumn<>("Descriere");
        TableColumn<TemaLaborator,String> startColumn=new TableColumn<>("Start");
        TableColumn<TemaLaborator,String> deadlineColumn=new TableColumn<>("Deadline");

        tableView.getColumns().add(descriereColumn);
        tableView.getColumns().add(startColumn);
        tableView.getColumns().add(deadlineColumn);

        descriereColumn.setCellValueFactory(new PropertyValueFactory<TemaLaborator, String>("Descriere"));
        startColumn.setCellValueFactory(new PropertyValueFactory<TemaLaborator, String>("Start"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<TemaLaborator, String>("Deadline"));

        tableView.setMaxHeight(270);
        tableView.setItems(ctrl.getModel());
        tableView.setMaxHeight(270);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {ctrl.handleUpdateTask((TemaLaborator)newValue);});

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getStylesheets().add("/themes/Theme1.css");

        return tableView;
    }

}
