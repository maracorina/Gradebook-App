package Controller;

import Domain.Student;
import Domain.Validator.ValidationException;
import Service.StudentService;
import Utils.Observer;
import Utils.StudentChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javafx.event.ActionEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import views.MessageView;
import views.StudentView;

public class StudentController implements Observer<StudentChangeEvent> {
    private StudentService service;
    private ObservableList<Student> model;

    private StudentView view;

    public ObservableList<Student> getModel() {
        return model;
    }

    public StudentController(StudentService service) {
        this.service = service;
        List<Student> list= StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList());
        model= FXCollections.observableArrayList(list);
        service.addObserver(this);
    }


    @Override
    public void update(StudentChangeEvent event) {
        model.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                .collect(Collectors.toList()));
    }

    public StudentView getView() {
        return view;
    }

    public void setView(StudentView view) {
        this.view = view;
    }

    public void showMessageTaskDetails(Student s) {
        if (s==null)
        {
            view.textFieldId.setText("");
            view.textFieldName.setText("");
            view.textFieldGroup.setText("");
            view.textFieldEmail.setText("");
        }
        else
        {
            view.textFieldId.setText(s.getID());
            view.textFieldName.setText(s.getNume());
            view.textFieldGroup.setText(String.valueOf(s.getGrupa()));
            view.textFieldEmail.setText(s.getEmail());
        }
    }


    public void handleSearchStudent(ActionEvent event) {
        model.setAll(StreamSupport.stream(service.searchByName(view.textFieldSearch.getText()).spliterator(),false)
                .collect(Collectors.toList()));
    }


    public void handleAddStudent(ActionEvent actionEvent) {
        Student s= extractStudent();
        if(s!=null) {
            try {
                service.add(s);
                MessageView.showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Studentul a fost adaugat!");

            } catch (ValidationException e) {
                MessageView.showErrorMessage(e.getMessage());
            } catch (CloneNotSupportedException e2) {
                MessageView.showErrorMessage(e2.getMessage());
            } catch (Exception e3) {
                MessageView.showErrorMessage(e3.getMessage());
            }
        }
    }

    public void handleUpdateStudent(ActionEvent actionEvent) {
        Student s= extractStudent();
        if(s!=null) {
            try {
                service.update(s);
                MessageView.showMessage(Alert.AlertType.INFORMATION, "Update cu succes", "Studentul a fost modificat!");

            } catch (ValidationException e) {
                MessageView.showErrorMessage(e.getMessage());
            } catch (IllegalArgumentException e2) {
                MessageView.showErrorMessage(e2.getMessage());
            }
        }
    }

    public void handleDeleteStudent(ActionEvent actionEvent) {
        try {
            service.remove(view.textFieldId.getText());
            MessageView.showMessage(Alert.AlertType.INFORMATION, "Stergere cu succes", "Studentul a fost sters!");

        } catch (ValidationException e) {
            MessageView.showErrorMessage(e.getMessage());
        } catch(IllegalArgumentException e2) {
            MessageView.showErrorMessage(e2.getMessage());
        }catch (Exception e3){
            MessageView.showErrorMessage(e3.getMessage());
        }
    }


    public Student extractStudent(){
        try {
            String id = view.textFieldId.getText();
            String nume = view.textFieldName.getText();
            int grupa = Integer.parseInt(view.textFieldGroup.getText());
            String email = view.textFieldEmail.getText();
            Student s = new Student(id, nume, grupa, email);
            return s;
        }catch (Exception e){
            MessageView.showErrorMessage(e.getMessage());
            return null;
        }
    }
}
