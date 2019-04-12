package views;

import Controller.FilterPaneController;
import Domain.NotaDTO;
import Domain.NotaDTOTable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class FilterPane{
    public FilterPaneController ctrl;

    public Stage stageTaskGrades;
    public Stage stageStudentGrades;
    public Stage stageGroupGrades;
    public Stage stageTaskGroupGrades;

    public FilterPane(FilterPaneController c){
        ctrl=c;
        stageTaskGrades=new Stage();
        stageStudentGrades=new Stage();
        stageGroupGrades=new Stage();
        stageTaskGroupGrades=new Stage();

        stageTaskGrades.setScene(filterTaskGradesScene());
        stageTaskGrades.setTitle("Task Grades");
        stageStudentGrades.setScene(filterStudentGradesScene());
        stageStudentGrades.setTitle("Student Grades");
        stageTaskGroupGrades.setScene(filterTaskGroupGradesScene());
        stageTaskGroupGrades.setTitle("Task Group Grades");
        stageGroupGrades.setScene(filterGroupGradesScene());
    }

    public Stage getFilterTaskGradesStage(){ return stageTaskGrades;}

    public Stage getFilterStudentGradesStage(){ return stageStudentGrades;}

    public Stage getFilterGroupGradesStage(){ return stageGroupGrades;}

    public Stage getFilterTaskGroupGradesStage(){ return stageTaskGroupGrades;}


    public Scene filterTaskGradesScene(){
        AnchorPane root=new AnchorPane();

        TableView tableView=createTaskGradesTable();
        root.getChildren().add(tableView);
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add("/themes/UpdateTask.css");

        AnchorPane.setLeftAnchor(tableView,77d);
        AnchorPane.setBottomAnchor(tableView,50d);
        return scene;
    }

    private TableView<NotaDTO> createTaskGradesTable() {
        TableView tableView=new TableView<>();

        TableColumn<NotaDTO,String> nameColumn=new TableColumn<>("Nume student");
        TableColumn<NotaDTO,String> notaColumn=new TableColumn<>("Nota");

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(notaColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("studentName"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("nota"));


        tableView.setItems(ctrl.getModelTask());
        tableView.setMaxHeight(300);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getStylesheets().add("/themes/Theme1.css");

        return tableView;
    }

    public Scene filterStudentGradesScene(){
        AnchorPane root=new AnchorPane();

        TableView tableView=createStudentGradesTable();
        root.getChildren().add(tableView);
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add("/themes/UpdateTask.css");

        AnchorPane.setLeftAnchor(tableView,77d);
        AnchorPane.setBottomAnchor(tableView,50d);
        return scene;
    }

    private TableView<NotaDTO> createStudentGradesTable() {
        TableView tableView=new TableView<>();

        TableColumn<NotaDTO,String> nameColumn=new TableColumn<>("Tema");
        TableColumn<NotaDTO,String> notaColumn=new TableColumn<>("Nota");

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(notaColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("temaId"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("nota"));


        tableView.setItems(ctrl.getModelStudent());
        tableView.setMaxHeight(300);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getStylesheets().add("/themes/Theme1.css");

        return tableView;
    }

    public Scene filterTaskGroupGradesScene(){
        AnchorPane root=new AnchorPane();

        TableView tableView=createGroupTaskGradesTable();
        root.getChildren().add(tableView);
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add("/themes/UpdateTask.css");

        AnchorPane.setLeftAnchor(tableView,77d);
        AnchorPane.setBottomAnchor(tableView,50d);
        return scene;
    }

    private TableView<NotaDTO> createGroupTaskGradesTable() {
        TableView tableView=new TableView<>();

        TableColumn<NotaDTO,String> nameColumn=new TableColumn<>("Student");
        TableColumn<NotaDTO,String> notaColumn=new TableColumn<>("Nota");

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(notaColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("studentName"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("nota"));

        tableView.setItems(ctrl.getModelTaskGroup());
        tableView.setMaxHeight(300);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getStylesheets().add("/themes/Theme1.css");

        return tableView;
    }

    public Scene filterGroupGradesScene(){
        AnchorPane root=new AnchorPane();

        TableView tableView=createGroupGradesTable();

        root.getChildren().add(tableView);
        Scene scene = new Scene(root, 797, 400);
        scene.getStylesheets().add("/themes/UpdateTask.css");

        AnchorPane.setLeftAnchor(tableView,77d);
        AnchorPane.setBottomAnchor(tableView,50d);
        return scene;
    }

    private TableView<NotaDTOTable> createGroupGradesTable() {
        TableView tableView=new TableView<>();

        TableColumn<NotaDTOTable,String> nameColumn=new TableColumn<>("Student");
        TableColumn<NotaDTOTable,String> task1Column=new TableColumn<>("Tema1");
        TableColumn<NotaDTOTable,String> task2Column=new TableColumn<>("Tema2");
        TableColumn<NotaDTOTable,String> task3Column=new TableColumn<>("Tema3");
        TableColumn<NotaDTOTable,String> task4Column=new TableColumn<>("Tema4");
        TableColumn<NotaDTOTable,String> task5Column=new TableColumn<>("Tema5");
        TableColumn<NotaDTOTable,String> task6Column=new TableColumn<>("Tema6");
        TableColumn<NotaDTOTable,String> task7Column=new TableColumn<>("Tema7");

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(task1Column);
        tableView.getColumns().add(task2Column);
        tableView.getColumns().add(task3Column);
        tableView.getColumns().add(task4Column);
        tableView.getColumns().add(task5Column);
        tableView.getColumns().add(task6Column);
        tableView.getColumns().add(task7Column);

        nameColumn.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("studentName"));
        task1Column.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("nota1"));
        task2Column.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("nota2"));
        task3Column.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("nota3"));
        task4Column.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("nota4"));
        task5Column.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("nota5"));
        task6Column.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("nota6"));
        task7Column.setCellValueFactory(new PropertyValueFactory<NotaDTOTable, String>("nota7"));

        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<NotaDTOTable, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setStudentName(t.getNewValue())
        );

        tableView.setItems(ctrl.getModelGroup());
        tableView.setMaxHeight(300);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getStylesheets().add("/themes/Theme1.css");

        return tableView;
    }
}
