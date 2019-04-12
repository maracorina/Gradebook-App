package Controller;

import Domain.TemaLaborator;
import Domain.Validator.ValidationException;
import Service.TemaLaboratorService;
import Utils.Observer;
import Utils.TaskChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import views.MessageView;
import views.TaskView;

public class TaskController implements Observer<TaskChangeEvent>{
    private TemaLaboratorService service;

    private ObservableList<TemaLaborator> model;

    private TaskView view;

    public TaskController(TemaLaboratorService service) {
        this.service = service;
        List<TemaLaborator> list= StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList());
        model= FXCollections.observableArrayList(list);
        service.addObserver(this);
    }

    public ObservableList<TemaLaborator> getModel() {
        return model;
    }

    @Override
    public void update(TaskChangeEvent event) {
        model.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                .collect(Collectors.toList()));
    }

    public TaskView getView() {
        return view;
    }

    public void setView(TaskView view) {
        this.view = view;
    }

    public void handleAddTask(ActionEvent actionEvent) {
        TemaLaborator t= extractTask();
        if(t!=null) {
            try {
                service.add(t);
                MessageView.showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Tema a fost adaugata!");

            } catch (ValidationException e) {
                MessageView.showErrorMessage(e.getMessage());
            } catch (CloneNotSupportedException e2) {
                MessageView.showErrorMessage(e2.getMessage());
            } catch (Exception e3) {
                MessageView.showErrorMessage(e3.getMessage());
            }
        }
    }

    public void handleUpdateDeadlineTask(ActionEvent actionEvent) {
        view.showUpdateWindow();
    }

    public void handleUpdateTask(TemaLaborator t) {
        this.service.prelungireDeadline(t);
    }

    public TemaLaborator extractTask(){
        try {
            String id = view.textFieldId.getText();
            String descriere = view.textFieldDescriere.getText();
            int start = Integer.parseInt(view.textFieldStart.getText());
            int deadline = Integer.parseInt(view.textFieldDeadline.getText());
            TemaLaborator t=new TemaLaborator(id, descriere, start, deadline);
            return t;
        }catch (Exception e){
            MessageView.showErrorMessage(e.getMessage());
            return null;
        }
    }
}
