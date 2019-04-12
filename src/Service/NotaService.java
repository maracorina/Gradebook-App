package Service;

import Domain.*;
import Domain.Validator.ValidationException;
import Repository.InMemoryRepository;
import Utils.*;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NotaService implements Observable<GradeChangeEvent> {
    private List<Observer<GradeChangeEvent>> observers=new ArrayList<>();

    private InMemoryRepository<String, Student> repoStudenti;
    private InMemoryRepository<String, TemaLaborator> repoTeme;
    private InMemoryRepository<String, CadruDidactic> repoCadre;
    private InMemoryRepository<Pair<String, String>, Nota> repoNote;

    public NotaService(InMemoryRepository rStud, InMemoryRepository rTeme, InMemoryRepository rCadre, InMemoryRepository rNote){
        repoStudenti=rStud;
        repoTeme=rTeme;
        repoCadre=rCadre;
        repoNote=rNote;
        loadNote();
    }

    /**
     * sets the student, the task and the teacher for every grade in file
     */
    private void loadNote(){
        Iterable<Nota> note=repoNote.findAll();
        note.forEach(nt->{
            repoStudenti.findOne(nt.getStudent().getID()).ifPresent(st->nt.setStudent(st));
            repoTeme.findOne(nt.getLaborator().getID()).ifPresent(t->nt.setLaborator(t));
            repoCadre.findOne(nt.getCadruDidactic().getID()).ifPresent(cd->nt.setCadruDidactic(cd));
        });
    }

    public Iterable<Nota> getAll() {
        return repoNote.findAll();
    }

    /**
     * adds new grade
     * @param nt
     * entity must be not null
     * entity must be not null
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws CloneNotSupportedException
     * if the given entity already exists or if the student/task/teacher given doesn't exist
     */
    public void add(Nota nt) throws CloneNotSupportedException{
        repoStudenti.findOne(nt.getStudent().getID()).orElseThrow(() -> new IllegalArgumentException("Nu exista studentul!"));
        repoTeme.findOne(nt.getLaborator().getID()).orElseThrow(() -> new IllegalArgumentException("Nu exista tema!"));
        repoCadre.findOne(nt.getCadruDidactic().getID()).orElseThrow(() -> new IllegalArgumentException("Nu exista cadrul didactic!"));
        repoStudenti.findOne(nt.getStudent().getID()).ifPresent(st->nt.setStudent(st));
        repoTeme.findOne(nt.getLaborator().getID()).ifPresent(t->nt.setLaborator(t));
        repoCadre.findOne(nt.getCadruDidactic().getID()).ifPresent(cd->nt.setCadruDidactic(cd));

        nt.setNota(punctaj(nt));

        if(repoNote.save(nt).isPresent())
            throw new CloneNotSupportedException("Nu se accepta duplicate! (Nota Service)");
        else
            notifyObservers(new GradeChangeEvent(ChangeEventType.ADD,nt)) ;

        printToStudentFile(nt);
    }

    public Nota get(String idS, String idL) {
        return repoNote.findOne(new Pair<>(idS, idL)).get();
    }

    /**
     * computes the grade's value
     * @param nt
     */
    public float punctaj(Nota nt){
        Predicate<Nota> check= n -> (n.getNota()-getDepunctare(n))<5;
        if(check.test(nt))
            return 1;
        else
            return nt.getNota() - getDepunctare(nt);
    }

    /**
     * updates the deadline and the value for a grade
     * @param idS, idL, saptamana
     * entity must be not null
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the grade doesn't exist.
     * if the given week doesn't match the task
     */
    public void motivareAbsenta(String idS, String idL, int saptamana){
        repoNote.findOne(new Pair<>(idS, idL)).orElseThrow(() -> new IllegalArgumentException("Nu exista nota!"));
        Nota nt=get(idS, idL);
        if(nt.getLaborator().getStart()>saptamana || nt.getDeadline()<saptamana)
            throw new IllegalArgumentException("Saptamana nu se potriveste cu perioada laboratorului!");
        int i=nt.getDeadline();
        if(nt.getSaptamanaPredare()>nt.getDeadline())
                if(saptamana<=nt.getDeadline()){
                    Nota old=nt;
                    nt.setDeadline(nt.getDeadline()+1);
                    nt.setNota((float)(nt.getNota()+2.5));
                    repoNote.update(nt);
                    notifyObservers(new GradeChangeEvent(ChangeEventType.UPDATE, nt, old)) ;
                    overwriteStudentFile(repoStudenti.findOne(idS).get());
                }
    }

    /**
     * computes the maximum value for the grade
     * @param nt
     */
    public float getNotaMaxima(Nota nt){
        Predicate<Nota> check= n -> (10-getDepunctare(n))<5;
        if(check.test(nt))
            return 1;
        else
            return 10-getDepunctare(nt);
    }


    /**
     *computes the points lost by the student
     * @param nt
     */
    public float getDepunctare(Nota nt){
        float p=(float)((Time.getCurrentWeek()-nt.getDeadline())*2.5);
        Predicate<Float> negative= i -> i<0;
        if(negative.test(p))
            return 0;
        else
            return p;
    }

    public void overwriteStudentFile(Student st){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(st.getNume()+".txt",false))) {
            bW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Nota> l=new ArrayList<>();
        repoNote.findAll().forEach(n->l.add(n));
        Predicate<Nota> byStudent=n->n.getStudent().getID()==st.getID();
        List<Nota> result = l.stream().filter(byStudent).collect(Collectors.<Nota> toList());
        result.forEach(nota -> printToStudentFile(nota));
    }

    public void printToStudentFile(Nota nt){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(nt.getStudent().getNume()+".txt",true))) {
            bW.write("Tema:" + nt.getLaborator().getID());
            bW.newLine();
            bW.write("Nota:" + nt.getNota());
            bW.newLine();
            bW.write("Predata in saptamana:" + nt.getSaptamanaPredare());
            bW.newLine();
            bW.write("Deadline:" + nt.getDeadline());
            bW.newLine();
            bW.write("Feedback:" + nt.getFeedback());
            bW.newLine();
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return number of entities
     */
    public int getNumberOfGrades(){
        return repoNote.size();
    }


    /**
     *
     * @param id - student id
     * id must not be null
     * @return List a list of the student's grades
     * @throws IllegalArgumentException
     * if id is null
     */
    public List<Nota> getStudentGrades(String id){
        if(id=="")
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        Iterable<Nota> grades=repoNote.findAll();
        List<Nota> l=new ArrayList<>();
        grades.forEach(n->l.add(n));
        Predicate<Nota> byStudent = n -> n.getStudent().getID().compareTo(id) == 0;

        List<Nota> result = l.stream().filter(byStudent).collect(Collectors.toList());
        return result;
    }

    /**
     *
     * @param id - task id
     * id must not be null
     * @return List a list of the student's grades
     * @throws IllegalArgumentException
     * if id is null
     */
    public List<Nota> getTaskGrades(String id){
        if(id=="")
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        Iterable<Nota> grades=repoNote.findAll();
        List<Nota> l=new ArrayList<>();
        grades.forEach(n->l.add(n));
        Predicate<Nota> byTask = n -> n.getLaborator().getID().compareTo(id) == 0;

        List<Nota> result = l.stream().filter(byTask).collect(Collectors.toList());
        return result;
    }

    /**
     *
     * @param group - the group number
     * group must not be null
     * @return List a list of the students in the given group
     * @throws IllegalArgumentException
     * if id is not in the interval [1; 7]
     */
    public List<Nota> getGroupGrades(int group){
        if(group <0)
            throw new IllegalArgumentException("Grupa nu poate fi mai mica decat 1!");
        if(group >7)
            throw new IllegalArgumentException("Grupa nu poate fi mai mare decat 7!");
        Iterable<Nota> grades=repoNote.findAll();
        List<Nota> l=new ArrayList<>();
        grades.forEach(n->l.add(n));

        Predicate<Nota> byGroup = n -> n.getStudent().getGrupa() == group;

        List<Nota> result = l.stream().filter(byGroup).collect(Collectors.toList());
        return result;
    }

    /**
     *
     * @param group - the group number
     * @param idTask - task id
     * group must not be null
     * idTask must not be null
     * @return List a list of the students in the given group
     * @throws IllegalArgumentException
     * if id is not in the interval [1; 7]
     * id idTask is null
     */
    public List<Nota> getGroupTaskGrades(int group, String idTask){
        if(group <0)
            throw new IllegalArgumentException("Grupa nu poate fi mai mica decat 1!");
        if(group >7)
            throw new IllegalArgumentException("Grupa nu poate fi mai mare decat 7!");
        if(idTask=="")
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        Iterable<Nota> grades=getGroupGrades(group);
        List<Nota> l=new ArrayList<>();
        grades.forEach(n->l.add(n));

        Predicate<Nota> byTask = n -> n.getLaborator().getID().compareTo(idTask) == 0;

        List<Nota> result = l.stream().filter(byTask).collect(Collectors.toList());
        return result;
    }

    /**
     *
     * @param  - the first date
     * @param  - the second date
     * @return List a list of the ades given between the two dates
     *
     *
     */
    public List<Float> getStudentFullGrades(String id){
        List<Optional<Nota>> l=new ArrayList<>();
        List<Float> l2=new ArrayList<>();
        l.add(repoNote.findOne(new Pair<>(id, "1")));
        l.add(repoNote.findOne(new Pair<>(id, "2")));
        l.add(repoNote.findOne(new Pair<>(id, "3")));
        l.add(repoNote.findOne(new Pair<>(id, "4")));
        l.add(repoNote.findOne(new Pair<>(id, "5")));
        l.add(repoNote.findOne(new Pair<>(id, "6")));
        l.add(repoNote.findOne(new Pair<>(id, "7")));
        l.forEach(n->{
            try{
                l2.add(n.get().getNota());
            }catch(Exception e){
                l2.add((float)1);
            }
        });
        return l2;
    }

    public List<NotaDTOTable> getStudentGroupGrades(int id){
        List<NotaDTOTable> l=new ArrayList<>();

        Iterable<Student> students=repoStudenti.findAll();
        List<Student> s=new ArrayList<>();
        students.forEach(n->s.add(n));

        Predicate<Student> byGroup = st -> st.getGrupa()== id;

        List<Student> stud = s.stream().filter(byGroup).collect(Collectors.toList());
        stud.forEach(student->{
            List<Float> note=getStudentFullGrades(student.getID());
            NotaDTOTable nota=new NotaDTOTable(student.getNume(), note.get(0), note.get(1), note.get(2), note.get(3), note.get(4), note.get(5), note.get(6));
            l.add(nota);
        });
        return l;
    }

    @Override
    public void addObserver(Observer<GradeChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<GradeChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(GradeChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

}
