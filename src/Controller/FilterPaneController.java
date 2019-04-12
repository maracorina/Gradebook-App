package Controller;

import Domain.NotaDTO;
import Domain.NotaDTOTable;
import Service.NotaService;
import Utils.GradeChangeEvent;
import Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import views.FilterPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FilterPaneController implements Observer<GradeChangeEvent>{
    private NotaService service;
    private ObservableList<NotaDTO> modelTaskGrades;
    private ObservableList<NotaDTO> modelStudentGrades;
    private ObservableList<NotaDTOTable> modelGroupGrades;
    private ObservableList<NotaDTO> modelTaskGroupGrades;
    FilterPane view;

    private String taskID="Lab1";
    private String studentID="1";
    private int groupID=1;
    private String taskID2="Lab1";
    private int groupID2=1;

    public void setView(FilterPane view) {
        this.view = view;
    }

    public FilterPaneController(NotaService srv){
        service=srv;
        modelTaskGrades= FXCollections.observableArrayList();
        modelStudentGrades= FXCollections.observableArrayList();
        modelGroupGrades= FXCollections.observableArrayList();
        modelTaskGroupGrades= FXCollections.observableArrayList();
        service.addObserver(this);
    }

    public ObservableList<NotaDTO> getModelTask() {
        return modelTaskGrades;
    }

    public ObservableList<NotaDTO> getModelStudent() {
        return modelStudentGrades;
    }

    public ObservableList<NotaDTO> getModelTaskGroup() {
        return modelTaskGroupGrades;
    }

    public ObservableList<NotaDTOTable> getModelGroup() {
        return modelGroupGrades;
    }

    @Override
    public void update(GradeChangeEvent event) {
        List<NotaDTO> note1 = new ArrayList<>();
        service.getTaskGrades(taskID).forEach(n->note1.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelTaskGrades.setAll(StreamSupport.stream(note1.spliterator(),false)
                .collect(Collectors.toList()));

        List<NotaDTO> note = new ArrayList<>();
        service.getStudentGrades(studentID).forEach(n->note.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelStudentGrades.setAll(StreamSupport.stream(note.spliterator(),false)
                .collect(Collectors.toList()));

        List<NotaDTO> note2 = new ArrayList<>();
        service.getGroupTaskGrades(groupID, taskID2).forEach(n->note2.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelTaskGroupGrades.setAll(StreamSupport.stream(note2.spliterator(),false)
                .collect(Collectors.toList()));

        List<NotaDTOTable> note4 = new ArrayList<>();
        service.getStudentGroupGrades(groupID2).forEach(n->note4.add(n));
        modelGroupGrades.setAll(StreamSupport.stream(note4.spliterator(),false)
                .collect(Collectors.toList()));
    }

    public void updateTaskGrades(String id) {
        taskID=id;
        List<NotaDTO> note1 = new ArrayList<>();
        service.getTaskGrades(taskID).forEach(n->note1.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelTaskGrades.setAll(StreamSupport.stream(note1.spliterator(),false)
                .collect(Collectors.toList()));
    }

    public void updateStudentGrades(String id) {
        studentID=id;
        List<NotaDTO> note = new ArrayList<>();
        service.getStudentGrades(studentID).forEach(n->note.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelStudentGrades.setAll(StreamSupport.stream(note.spliterator(),false)
                .collect(Collectors.toList()));
    }

    public void updateTaskGroupGrades(int id, String idT) {
        groupID=id;
        taskID2=idT;
        List<NotaDTO> note2 = new ArrayList<>();
        service.getGroupTaskGrades(groupID, taskID2).forEach(n->note2.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelTaskGroupGrades.setAll(StreamSupport.stream(note2.spliterator(),false)
                .collect(Collectors.toList()));
    }

    public void updateGroupGrades(int id) {
        groupID2=id;

        List<NotaDTOTable> note4 = new ArrayList<>();
        service.getStudentGroupGrades(groupID2).forEach(n->note4.add(n));
        modelGroupGrades.setAll(StreamSupport.stream(note4.spliterator(),false)
                .collect(Collectors.toList()));
    }

    public void handleTaskGrades(ActionEvent actionEvent, String id) {
        taskID=id;
        List<NotaDTO> note = new ArrayList<>();
        service.getTaskGrades(id).forEach(n->note.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelTaskGrades.setAll(StreamSupport.stream(note.spliterator(),false)
                .collect(Collectors.toList()));
        if(!view.getFilterTaskGradesStage().isShowing())
            view.getFilterTaskGradesStage().showAndWait();
        else
            view.getFilterTaskGradesStage().toFront();
    }

    public void handleStudentGrades(ActionEvent actionEvent, String id) {
        studentID=id;
        List<NotaDTO> note = new ArrayList<>();
        service.getStudentGrades(id).forEach(n->note.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelStudentGrades.setAll(StreamSupport.stream(note.spliterator(),false)
                .collect(Collectors.toList()));
        if(!view.getFilterStudentGradesStage().isShowing())
            view.getFilterStudentGradesStage().showAndWait();
        else
            view.getFilterStudentGradesStage().toFront();
    }

    public void handleTaskGroupGrades(ActionEvent actionEvent, int id, String idT) {
        groupID=id;
        taskID2=idT;
        List<NotaDTO> note = new ArrayList<>();
        service.getGroupTaskGrades(id, idT).forEach(n->note.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));
        modelTaskGroupGrades.setAll(StreamSupport.stream(note.spliterator(),false)
                .collect(Collectors.toList()));
        if(!view.getFilterTaskGroupGradesStage().isShowing())
            view.getFilterTaskGroupGradesStage().showAndWait();
        else
            view.getFilterTaskGroupGradesStage().toFront();
    }

    public void handleGroupGrades(ActionEvent actionEvent, int id) {
        groupID2=id;
        List<NotaDTOTable> note4 = new ArrayList<>();
        service.getStudentGroupGrades(groupID2).forEach(n->note4.add(n));
        modelGroupGrades.setAll(StreamSupport.stream(note4.spliterator(),false)
                .collect(Collectors.toList()));
        if(!view.getFilterGroupGradesStage().isShowing())
            view.getFilterGroupGradesStage().showAndWait();
        else
            view.getFilterGroupGradesStage().toFront();
    }

}
