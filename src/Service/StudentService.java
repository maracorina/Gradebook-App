package Service;

import Domain.Student;
import Domain.Validator.ValidationException;
import Repository.CrudRepository;
import Utils.ChangeEventType;
import Utils.Observer;
import Utils.StudentChangeEvent;

import Utils.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentService implements Observable<StudentChangeEvent>{
    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();

    private CrudRepository<String, Student> repo;

    public StudentService(CrudRepository r){
        repo=r;
    }

    /**
     *
     * @param st
     * entity must be not null
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws CloneNotSupportedException
     * if the given entity already exists.
     */
    public void add(Student st) throws CloneNotSupportedException{
        if(repo.save(st).isPresent())
            throw new CloneNotSupportedException("Nu se accepta duplicate!");
        else
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD,st)) ;
    }

    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @throws IllegalArgumentException
     * if the given id is null.
     * @throws IllegalArgumentException
     * if the given id doesn't match an entity
     */
    public void remove(String id){
        Optional<Student> old=repo.delete(id);
        if(old.isPresent())
            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE, old.get())) ;
        else
            throw new IllegalArgumentException("Studentul nu exista!");
    }

    /**
     *
     * @param st
     * entity must not be null
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws ValidationException
     * if the entity is not valid.
     * @throws IllegalArgumentException
     * if the given entity does not exist
     */
    public void update(Student st){
        Optional<Student> old=repo.update(st);
        if(old.isPresent())
            throw new IllegalArgumentException("Studentul nu exista!");
        else
            notifyObservers(new StudentChangeEvent(ChangeEventType.UPDATE,st, old.get())) ;
    }

    /**
     *
     * @return all entities
     */
    public Iterable<Student> getAll() {
        return repo.findAll();
    }

    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    public Student get(String id) {
        return repo.findOne(id).get();
    }

    /**
     *
     * @return number of entities
     */
    public int getNumberOfStudents(){
        return repo.size();
    }



    /**
     *
     * @param nume - students' name
     * nume must not be null
     * @return List a list of the students that have this name
     * @throws IllegalArgumentException
     * if nume is null
     */
    public List<Student> searchByName(String nume){
        Iterable<Student> student=repo.findAll();
        List<Student> l=new ArrayList<>();
        student.forEach(s->l.add(s));
        Predicate<Student> byStudent = s -> s.getNume().startsWith(nume);

        return l.stream().filter(byStudent).collect(Collectors.toList());
    }

    public List<Student> getGroup(int g){
        Iterable<Student> student=repo.findAll();
        List<Student> l=new ArrayList<>();
        student.forEach(s->l.add(s));
        Predicate<Student> byGroup = s -> s.getGrupa()==g;

        return l.stream().filter(byGroup).collect(Collectors.toList());
    }

    @Override
    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(StudentChangeEvent t) {
        observers.forEach(x->x.update(t));
    }


}
