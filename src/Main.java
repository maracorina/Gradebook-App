import Controller.GradeController;
import Controller.MainController;
import Controller.StudentController;
import Controller.TaskController;
import Domain.Validator.ValidatorCadruDidactic;
import Domain.Validator.ValidatorNota;
import Domain.Validator.ValidatorStudent;
import Domain.Validator.ValidatorTemaLaborator;
import Repository.*;
import Service.NotaService;
import Service.StudentService;
import Service.TemaLaboratorService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import views.MainView;
import views.StudentView;
import views.TaskView;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Integer intObject1 = new Integer(34);
        Integer intObject2 = new Integer("35");
        Boolean boolValue1 = new Boolean("true");
        Integer intObject3 = Integer.valueOf("36");
        Integer intObject4 = Integer.valueOf("1001", 2); // 9 in baza


        StudentRepository repo = new StudentRepository("src/Students", new ValidatorStudent());
        StudentService service=new StudentService(repo);
        StudentController ctrl=new StudentController(service);
        StudentView view=new StudentView(ctrl);
        ctrl.setView(view);

        TemaLaboratorRepository repoTask = new TemaLaboratorRepository("src/Tasks", new ValidatorTemaLaborator());
        TemaLaboratorService serviceTask=new TemaLaboratorService(repoTask);
        TaskController ctrlTask=new TaskController(serviceTask);
        TaskView viewTask=new TaskView(ctrlTask);
        ctrlTask.setView(viewTask);

        CadruDidacticRepository cadre=new CadruDidacticRepository("src/CadreDidactice", new ValidatorCadruDidactic());

        NotaRepository repoGrade = new NotaRepository("src/Grades", new ValidatorNota());
        NotaService serviceGrade=new NotaService(repo, repoTask, cadre, repoGrade);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("views/GradeView.fxml"));
        BorderPane root=null;
        try{
            root=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        GradeController ctrlGrade=loader.getController();
        ctrlGrade.initialize(serviceGrade, service, serviceTask, cadre);

        MainView view3=new MainView();

        MainController mC= new MainController(view, viewTask, view3, new Scene(root, 1024, 512), primaryStage);

        view3.setController(mC);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


//import Domain.TemaLaborator;
//import Domain.Validator.ValidatorCadruDidactic;
//import Domain.Validator.ValidatorNota;
//import Domain.Validator.ValidatorStudent;
//import Domain.Validator.ValidatorTemaLaborator;
//import Repository.*;
//import Service.NotaService;
//import Service.StudentService;
//import Service.TemaLaboratorService;
//import UI.Console;
//
//import java.io.File;
//
//public class Main {
//    public static void main(String[] args) {
//        StudentXMLRepository stud=new StudentXMLRepository("src/Students.xml", new ValidatorStudent());
//        StudentService students= new StudentService(stud);
//        TemaLaboratorXMLRepository labs=new TemaLaboratorXMLRepository("src/Tasks.xml", new ValidatorTemaLaborator());
//        TemaLaboratorService tasks=new TemaLaboratorService(labs);
//        CadruDidacticXMLRepository cadre=new CadruDidacticXMLRepository("src/CadreDidactice.xml", new ValidatorCadruDidactic());
//        NotaService note=new NotaService(stud, labs, cadre, new NotaXMLRepository("src/Grades.xml", new ValidatorNota()));
//        Console console= new Console(students, tasks, note);
//    }
//}
