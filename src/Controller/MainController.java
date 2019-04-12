package Controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainView;
import views.StudentView;
import views.TaskView;

public class MainController{
    static private StudentView viewS;
    static private TaskView viewT;
    static private Scene viewG;
    private MainView viewM;

    static private Stage root;

    public MainController(StudentView s, TaskView t, MainView m, Scene gradeView, Stage p) {
        viewS=s;
        viewT=t;
        viewM = m;
        viewG=gradeView;
        root=p;
        root.setScene(viewM.getView());
        root.setTitle("Meniu");
    }

    static public void handleSetStudentView(ActionEvent actionEvent) {
        root.setScene(viewS.getView());
        root.setTitle("Studenti");
    }

    static public void handleSetTaskView(ActionEvent actionEvent) {
        root.setScene(viewT.getView());
        root.setTitle("Laboratoare");
    }

    static public void handleSetGradeView(ActionEvent actionEvent) {
        root.setScene(viewG);
        root.setTitle("Note");
    }

    }
