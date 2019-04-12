package views;

import Controller.MainController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainView {
    private MainController ctrl;
    private Scene scene;
    private BorderPane root;


    public MainView() {
        initView();
    }

    public void setController(MainController C){
        this.ctrl=C;
    }

    private void initView() {
        root = new BorderPane();

        scene = new Scene(root, 1024, 512);

        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());
        scene.getStylesheets().add("/themes/Theme.css");

        root.setCenter(initCenter());
    }


    public Scene getView(){ return scene;}


    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();

        HBox buttonsGroups=createBottomButtons();
        centerAnchorPane.getChildren().add(buttonsGroups);
        AnchorPane.setBottomAnchor(buttonsGroups,200d);
        AnchorPane.setLeftAnchor(buttonsGroups,240d);

        return centerAnchorPane;
    }


    public HBox createBottomButtons(){
        Image image = new Image(getClass().getResourceAsStream("/images/studentBig.png"));
        ImageView imageView = new ImageView(image);

        Button button = new Button("", imageView);
        button.getStylesheets().add("/themes/Teme2Button.css");
        AnchorPane.setLeftAnchor(button,300d);
        AnchorPane.setBottomAnchor(button,15d);

        Image image2 = new Image(getClass().getResourceAsStream("/images/gradeBig.png"));
        ImageView imageView2 = new ImageView(image2);

        Button button2 = new Button("", imageView2);
        button2.getStylesheets().add("/themes/Teme2Button.css");
        AnchorPane.setLeftAnchor(button2,440d);
        AnchorPane.setBottomAnchor(button2,15d);

        Image image3 = new Image(getClass().getResourceAsStream("/images/taskBig.png"));
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

}
