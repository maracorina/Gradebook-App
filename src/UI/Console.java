package UI;

import Domain.Nota;
import Domain.Student;
import Domain.TemaLaborator;
import Domain.Validator.ValidationException;
import Service.NotaService;
import Service.StudentService;
import Service.TemaLaboratorService;
import Utils.Time;

import java.util.Iterator;
import java.util.Scanner;

public class Console {
    private StudentService studSrv;
    private TemaLaboratorService temaSrv;
    private NotaService noteSrv;


    public Console(StudentService stSrv, TemaLaboratorService tSrv, NotaService nSrv) {
        this.studSrv = stSrv;
        this.temaSrv = tSrv;
        this.noteSrv= nSrv;

        this.options();
    }

    private void showOptions() {
        System.out.println("1. Show all students");
        System.out.println("2. Show all tasks");
        System.out.println("3. Add a student");
        System.out.println("4. Delete a student");
        System.out.println("5. Update a student");
        System.out.println("6. Add a task");
        System.out.println("7. Update a deadline");
        System.out.println("8. Add a grade");
        System.out.println("9. Show all grades");
        System.out.println("10. Motivate an absence");
        System.out.println("0. Exit");

    }

    private int options() {
        int op=1;
        Scanner sc = new Scanner(System.in);

        while (op != 0) {
            System.out.println("Choose an option");
            this.showOptions();
            op = sc.nextInt();
            if (op == 1) showStudents();
            else if (op == 2) showTasks();
            else if (op == 3) addStudent();
            else if (op == 4) delStudent();
            else if (op == 5) updStudent();
            else if (op == 6) addTask();
            else if (op == 7) updDeadline();
            else if (op == 8) addGrade();
            else if (op == 9) showGrades();
            else if (op == 10) motivare();
        }
        return 0;
    }

    private void motivare() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Id student =");
        String idS = sc1.next();
        System.out.println("Id tema =");
        String idL = sc1.next();
        System.out.println("Saptamana =");
        int s = sc1.nextInt();
        try {
            noteSrv.motivareAbsenta(idS, idL, s);
            System.out.println(noteSrv.get(idS, idL));
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void showStudents() {
        Iterable<Student> students = this.studSrv.getAll();
        students.forEach(st -> System.out.println(st));
    }

    private void showGrades() {
        Iterable<Nota> grades = this.noteSrv.getAll();
        grades.forEach( nt -> System.out.println(nt));
    }

    private void showTasks() {
        Iterable<TemaLaborator> tasks = this.temaSrv.getAll();
        tasks.forEach(t -> System.out.println(t));
    }

    private void addStudent() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Id=");
        String id = sc1.next();
        System.out.println("Nume=");
        sc1.nextLine();
        String nume = sc1.nextLine();
        System.out.println("Grupa=");
        int gr = sc1.nextInt();
        System.out.println("Email=");
        String email = sc1.next();
        try {
            studSrv.add(new Student(id, nume, gr, email));
        }
        catch(CloneNotSupportedException x){
            System.out.println(x.getMessage());
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch(ValidationException v){
            System.out.println(v.getMessage());
        }
    }

    private void addGrade() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Id student =");
        String idS = sc1.next();
        System.out.println("Id tema =");
        String idL = sc1.next();
        System.out.println("Id cadru didactic =");
        String idCD = sc1.next();
        System.out.println("Nota =");
        int nt = sc1.nextInt();
        System.out.println("Feedback =");
        sc1.nextLine();
        String feedbck = sc1.nextLine();
        try {
            Nota n=new Nota(idS, idL, nt, feedbck, (int)Time.getCurrentWeek(), temaSrv.get(idL).getDeadline(), idCD);
            noteSrv.add(n);
            System.out.println("Nota maxima posibila: "+noteSrv.getNotaMaxima(n));
            System.out.println("Nota acordata: "+n.getNota());
            if(n.getNota()==1)
                System.out.println("Laboratorul nu mai poate fi predat!");
        }
        catch(CloneNotSupportedException x){
            System.out.println(x.getMessage());
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch(ValidationException v){
            System.out.println(v.getMessage());
        }
    }

    private void delStudent() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Id=");
        String id = sc1.next();
        try{
            studSrv.remove(id);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void updStudent() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Id=");
        String id = sc1.next();
        System.out.println("Nume=");
        String nume = sc1.next();
        System.out.println("Grupa=");
        int gr = sc1.nextInt();
        System.out.println("Email=");
        String email = sc1.next();
        try{
            studSrv.update(new Student(id, nume, gr, email));
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch(ValidationException v){
            System.out.println(v.getMessage());
        }
    }

    private void addTask() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Id=");
        String id = sc1.next();
        System.out.println("Descriere=");
        sc1.nextLine();
        String descriere = sc1.nextLine();
        System.out.println("Termen=");
        int termen = sc1.nextInt();
        System.out.println("Saptamana primire=");
        int primire = sc1.nextInt();
        try{
            temaSrv.add(new TemaLaborator(id, descriere, primire, termen));
        }
        catch(CloneNotSupportedException x){
            System.out.println(x.getMessage());
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch(ValidationException v){
            System.out.println(v.getMessage());
        }
    }

    private void updDeadline() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Id=");
        String id = sc1.next();
        temaSrv.prelungireDeadline(temaSrv.get(id));
    }


}