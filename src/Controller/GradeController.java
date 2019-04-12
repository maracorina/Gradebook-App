package Controller;

import Domain.*;
import Domain.Validator.ValidationException;
import Repository.InMemoryRepository;
import Service.NotaService;
import Service.StudentService;
import Service.TemaLaboratorService;
import Utils.GradeChangeEvent;
import Utils.Observer;
import Utils.TableViewSample;
import Utils.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import views.FilterPane;
import views.MessageView;

import javax.swing.text.DefaultEditorKit;


public class GradeController implements Observer<GradeChangeEvent> {
    private NotaService service;
    private StudentService studentService;
    private TemaLaboratorService temaService;
    private InMemoryRepository<String, CadruDidactic> repoCadre;

    ObservableList<NotaDTO> model=FXCollections.observableArrayList();


    @FXML
    TableColumn<NotaDTO,String> tableColumnName;
    @FXML
    TableColumn<NotaDTO,String> tableColumnTema;
    @FXML
    TableColumn<NotaDTO,Float> tableColumnNota;
    @FXML
    TableView<NotaDTO> tableViewNote;

    @FXML
    private ComboBox<String> inputCadruBox;

    @FXML
    private TextField inputNota;

    @FXML
    private TextArea inputFeedback;

    @FXML
    private ComboBox<String> inputStudentBox;

    @FXML
    private ComboBox<String> inputTaskBox;

    @FXML
    private ComboBox<String> inputGroupBox;

    @FXML
    private CheckBox checkStudent;

    @FXML
    private CheckBox checkGroup;

    FilterPaneController ctrlFilter;
    FilterPane viewFilter;


    public void initialize(NotaService notaService, StudentService studentService, TemaLaboratorService temaService, InMemoryRepository<String, CadruDidactic> repo) {
        this.service = notaService;
        this.studentService = studentService;
        this.temaService = temaService;
        repoCadre=repo;
        this.service.addObserver(this);

        populateTable();

        List<NotaDTO> note = new ArrayList<>();
        service.getAll().forEach(n->note.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));

        model.setAll(StreamSupport.stream(note.spliterator(), false)
                .collect(Collectors.toList()));

        checkStudent.setSelected(true);

        initComboBoxStudents();
        initComboBoxTasks();
        initComboBoxGroups();
        initCadruBox();


        ctrlFilter=new FilterPaneController(service);
        viewFilter=new FilterPane(ctrlFilter);
        ctrlFilter.setView(viewFilter);
    }

    public void initCadruBox(){
        List<String> l=new ArrayList<>();
        repoCadre.findAll().forEach(s->l.add(s.getID()));
        inputCadruBox.getItems().addAll(l);
        new samples.AutoCompleteComboBoxListener<String>(inputCadruBox);
        inputCadruBox.getEditor().setText("1");
    }

    public void initComboBoxGroups(){
        inputGroupBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7");
        new samples.AutoCompleteComboBoxListener<String>(inputGroupBox);
    }

    public void initComboBoxStudents(){
        List<String> l=new ArrayList<>();
        studentService.getAll().forEach(s->l.add(s.getNume()));
        inputStudentBox.getItems().addAll(l);
        new samples.AutoCompleteComboBoxListener<String>(inputStudentBox);
    }

    public void initComboBoxTasks(){
        List<String> l=new ArrayList<>();
        temaService.getAll().forEach(t->l.add(t.getID()));
        inputTaskBox.getItems().addAll(l);
        inputTaskBox.getEditor().setText(temaService.getCurrentTask().getID());
        new samples.AutoCompleteComboBoxListener<String>(inputTaskBox);
    }

    public void showMessageTaskDetails(Nota n){
        inputStudentBox.getEditor().setText(n.getStudent().getNume());
        inputTaskBox.getEditor().setText(n.getLaborator().getID());
        inputNota.setText(""+n.getNota());
        inputGroupBox.getEditor().setText(""+n.getStudent().getGrupa());
        inputCadruBox.getEditor().setText(n.getCadruDidactic().getID());
        inputFeedback.setText(n.getFeedback());
    }

    public void handleListSelection(MouseEvent actionEvent) {
        String student=tableViewNote.getSelectionModel().getSelectedItem().getStudentName();
        String tema=tableViewNote.getSelectionModel().getSelectedItem().getTemaId();

        showMessageTaskDetails(service.get(studentService.searchByName(student).get(0).getID(), tema));
    }

    private void populateTable() {

        tableColumnName.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("studentName"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("temaId"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<NotaDTO, Float>("nota"));

        tableViewNote.setItems(model);
    }

    @Override
    public void update(GradeChangeEvent notaEvent) {
        List<NotaDTO> note = new ArrayList<>();
        service.getAll().forEach(n->note.add(new NotaDTO(n.getStudent().getNume(), n.getLaborator().getID(), n.getNota())));

        model.setAll(StreamSupport.stream(note.spliterator(), false)
                .collect(Collectors.toList()));
    }

    public Nota extractGrade(){
        try {
            String student = inputStudentBox.getEditor().getText();
            String tema = inputTaskBox.getEditor().getText();
            float nota = Float.parseFloat(inputNota.getText());
            String cadru = inputCadruBox.getEditor().getText();
            String feedback = inputFeedback.getText();
            Nota nt=new Nota(studentService.searchByName(student).get(0), temaService.get(tema), nota, feedback, repoCadre.findOne(cadru).get(), (int) Time.getCurrentWeek(), temaService.get(tema).getDeadline());
            return nt;
        }catch (Exception e){
            MessageView.showErrorMessage(e.getMessage());
            return null;
        }
    }

    public void handleCheckGroup(ActionEvent actionEvent) {
        checkStudent.setSelected(false);
    }

    public void handleCheckStudent(ActionEvent actionEvent) {
        checkGroup.setSelected(false);
    }

    public void handleAdd(ActionEvent actionEvent) {
        if(checkStudent.isSelected())
            handleAddGrade(actionEvent);
        else if(checkGroup.isSelected())
            handleAddGroupGrade(actionEvent);
    }

    public void adaugareNota(Nota n){
        if(confirmAddMessage(n))
            try {
                service.add(n);
                MessageView.showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Nota a fost adaugata!");
            } catch (ValidationException e) {
                MessageView.showErrorMessage(e.getMessage());
            } catch(CloneNotSupportedException e2){
                MessageView.showErrorMessage(e2.getMessage());
            }
    }

    public void handleAddGroupGrade(ActionEvent actionEvent) {
        Nota n=extractGrade();
        studentService.getGroup(Integer.parseInt(inputGroupBox.getEditor().getText())).forEach(s->{
            Nota nt=n;
            nt.setStudent(s);
            nt.setID(new Pair<>(s.getID(), nt.getLaborator().getID()));
            adaugareNota(nt);
        });
    }

        public void handleAddGrade(ActionEvent actionEvent) {
        Nota n=extractGrade();
        adaugareNota(n);
        }

    public void handleTaskBox(ActionEvent actionEvent) {
        handleDefaultFeedback(actionEvent);
        updateTaskGrades(actionEvent);
        updateTaskGroupGrades(actionEvent);
    }

    public void handleGroupBox(ActionEvent actionEvent) {
        updateGroupGrades(actionEvent);
        updateTaskGroupGrades(actionEvent);
    }

    public void handleDefaultFeedback(ActionEvent actionEvent) {
        TemaLaborator t=temaService.get(inputTaskBox.getEditor().getText());
        TemaLaborator currentT=temaService.getCurrentTask();
        if(t.getDeadline()<currentT.getDeadline()) {
            double depunctare = (Time.getCurrentWeek() - t.getDeadline()) * 2.5;
            inputFeedback.setText("NOTA A FOST DIMINUATĂ CU " + depunctare + " PUNCTE DATORITĂ ÎNTÂRZIERILOR");
        }
        else{
            inputFeedback.setText("Laborator predat anterior termenului limita!");
        }
    }

    public void handleMotivateAbsence(ActionEvent actionEvent) {
        Nota n=extractGrade();
        try {
            service.motivareAbsenta(n.getStudent().getID(), n.getLaborator().getID(), n.getDeadline());
        }catch(IllegalArgumentException e){
            MessageView.showErrorMessage(e.getMessage());
        }
    }

    boolean confirmAddMessage(Nota nt) {
        if (nt.getNota() == 1)
            MessageView.showMessage(Alert.AlertType.INFORMATION, "Atentie!", "Laboratorul nu mai poate fi predat!");
        else {
            MessageView.showMessage(Alert.AlertType.INFORMATION, "Atentie!", "Nota maxima posibila: " + service.getNotaMaxima(nt));
            return (MessageView.showMessage(Alert.AlertType.CONFIRMATION, "Confirmati salvarea", "Student: " + nt.getStudent().getNume() + "\n" +
                    "Grupa student: " + nt.getStudent().getGrupa() + "\n" +
                    "Tema: " + nt.getLaborator().getID() + "\n" +
                    "Cadru didactic: " + nt.getCadruDidactic().getNume() + "\n" +
                    "Nota: " + service.punctaj(nt) + "\n" +
                    "Depunctare: " + (10 - service.getNotaMaxima(nt)) + "\n" +
                    "Feedback: " + nt.getFeedback()).get() == ButtonType.OK);
        }
        return false;
    }


    public void handleTaskGrades(ActionEvent actionEvent) {
        ctrlFilter.handleTaskGrades(actionEvent, inputTaskBox.getEditor().getText());
    }

    public void handleStudentGrades(ActionEvent actionEvent) {
        ctrlFilter.handleStudentGrades(actionEvent, studentService.searchByName(inputStudentBox.getEditor().getText()).get(0).getID());
    }

    public void handleTaskGroupGrades(ActionEvent actionEvent) {
        if(inputGroupBox.getEditor().getText().compareTo("")!=0)
            ctrlFilter.handleTaskGroupGrades(actionEvent, Integer.parseInt(inputGroupBox.getEditor().getText()), inputTaskBox.getEditor().getText());
        else
            ctrlFilter.handleTaskGroupGrades(actionEvent, 0, inputTaskBox.getEditor().getText());
    }

    public void handleGroupGrades(ActionEvent actionEvent) {
        if(inputGroupBox.getEditor().getText().compareTo("")!=0)
            ctrlFilter.handleGroupGrades(actionEvent, Integer.parseInt(inputGroupBox.getEditor().getText()));
        else
            ctrlFilter.handleGroupGrades(actionEvent, 0);
    }

    public void updateTaskGrades(ActionEvent actionEvent) {
        ctrlFilter.updateTaskGrades(inputTaskBox.getEditor().getText());
    }

    public void updateStudentGrades(ActionEvent actionEvent) {
        ctrlFilter.updateStudentGrades(studentService.searchByName(inputStudentBox.getEditor().getText()).get(0).getID());
    }

    public void updateTaskGroupGrades(ActionEvent actionEvent) {
        if(inputGroupBox.getEditor().getText().compareTo("")!=0)
            ctrlFilter.updateTaskGroupGrades(Integer.parseInt(inputGroupBox.getEditor().getText()), inputTaskBox.getEditor().getText());
        else
            ctrlFilter.updateTaskGroupGrades(0, inputTaskBox.getEditor().getText());
    }

    public void updateGroupGrades(ActionEvent actionEvent) {
        if(inputGroupBox.getEditor().getText().compareTo("")!=0)
            ctrlFilter.updateGroupGrades(Integer.parseInt(inputGroupBox.getEditor().getText()));
        else
            ctrlFilter.updateGroupGrades(0);
    }


    public ObservableList<NotaDTO> getModel() {
        return model;
    }


    public void handleSetStudentView(ActionEvent actionEvent) {
        MainController.handleSetStudentView(actionEvent);
    }

    public void handleSetGradeView(ActionEvent actionEvent) {
        MainController.handleSetGradeView(actionEvent);
    }

    public void handleSetTaskView(ActionEvent actionEvent) {
        MainController.handleSetTaskView(actionEvent);
    }
}
