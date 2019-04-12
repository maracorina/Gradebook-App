package views;

import Controller.MainController;
import Controller.StudentController;
import Domain.Student;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StudentView {
    private StudentController ctrl;
    private Scene scene;
    private BorderPane root;
    private TableView<Student> tableView;

    public TextField textFieldId;
    public TextField textFieldName;
    public TextField textFieldGroup;
    public TextField textFieldEmail;

    public TextField textFieldSearch;

    public StudentView(StudentController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    private void initView() {
        root = new BorderPane();

        scene = new Scene(root, 1024, 512);

        textFieldId=new TextField();
        textFieldName=new TextField();
        textFieldGroup=new TextField();
        textFieldEmail=new TextField();

        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
        scene.getStylesheets().add("/themes/Theme.css");

        root.setLeft(initLeft());
        root.setCenter(initCenter());
    }


    public Scene getView(){ return scene;}

    public AnchorPane initLeft(){
        AnchorPane leftAnchorPane=new AnchorPane();
        tableView=createStudentTable();

        HBox search=searchBox();
        leftAnchorPane.getChildren().add(search);
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(search,45d);
        AnchorPane.setBottomAnchor(search,433d);
        AnchorPane.setLeftAnchor(tableView,60d);
        AnchorPane.setBottomAnchor(tableView,147d);

        HBox buttonsGroups=createBottomButtons();
        leftAnchorPane.getChildren().add(buttonsGroups);
        AnchorPane.setBottomAnchor(buttonsGroups,20d);
        AnchorPane.setLeftAnchor(buttonsGroups,330d);

        return  leftAnchorPane;
    }

    public HBox searchBox(){
        Button buttonSearch=createButton("Search");
        //buttonAdd.setPrefSize(60, 50);

        textFieldSearch=new TextField();
        textFieldSearch.setPrefSize(215, 10);

        HBox hb=new HBox(13, buttonSearch, textFieldSearch);
        buttonSearch.setOnAction(ctrl::handleSearchStudent);
        return hb;

    }

    private TableView<Student> createStudentTable() {
        tableView=new TableView<>();

        TableColumn<Student,String> nameColumn=new TableColumn<>("Nume");
        TableColumn<Student,String> groupColumn=new TableColumn<>("Grupa");

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(groupColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("Nume"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("Grupa"));


        tableView.setItems(ctrl.getModel());
        tableView.setMaxHeight(270);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {ctrl.showMessageTaskDetails(newValue);});

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getStylesheets().add("/themes/Theme1.css");

        return tableView;
    }



    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridPane=createGridPane();

        centerAnchorPane.getChildren().add(gridPane);
        AnchorPane.setLeftAnchor(gridPane,5d);
        AnchorPane.setBottomAnchor(gridPane, 200d);

        HBox buttonsGroups=createButtons();
        centerAnchorPane.getChildren().add(buttonsGroups);
        AnchorPane.setBottomAnchor(buttonsGroups,145d);
        AnchorPane.setLeftAnchor(buttonsGroups,25d);


        return centerAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneMessageDetails=new GridPane();
        gridPaneMessageDetails.setPadding(new Insets(20, 20, 20, 20));

        gridPaneMessageDetails.setHgap(20);
        gridPaneMessageDetails.setVgap(20);

        Label labelId= createLabel("ID: ");
        Label labelNume= createLabel("Nume: ");
        Label labelGrupa=createLabel("Grupa: ");
        Label labelEmail=createLabel("Email: ");

        gridPaneMessageDetails.add(labelId,0,0);
        gridPaneMessageDetails.add(labelNume,0,1);
        gridPaneMessageDetails.add(labelGrupa,0,2);
        gridPaneMessageDetails.add(labelEmail,0,3);

        gridPaneMessageDetails.add(textFieldId,1,0);
        gridPaneMessageDetails.add(textFieldName,1,1);
        gridPaneMessageDetails.add(textFieldGroup,1,2);
        gridPaneMessageDetails.add(textFieldEmail,1,3);

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
        Button buttonUpdate=createButton("Update");
        buttonUpdate.setPrefSize(60, 50);
        Button buttonDelete=createButton("Delete");
        buttonDelete.setPrefSize(60, 50);

        HBox hb=new HBox(50, buttonAdd,buttonUpdate, buttonDelete);
        buttonAdd.setOnAction(ctrl::handleAddStudent);
        buttonUpdate.setOnAction(ctrl::handleUpdateStudent);
        buttonDelete.setOnAction(ctrl::handleDeleteStudent);
        return hb;

    }

}
