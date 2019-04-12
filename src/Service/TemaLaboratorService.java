package Service;

import Domain.TemaLaborator;
import Domain.Validator.ValidationException;
import Domain.Validator.ValidatorStudent;
import Domain.Validator.ValidatorTemaLaborator;
import Repository.InMemoryRepository;
import Repository.StudentRepository;
import Repository.TemaLaboratorRepository;
import Utils.*;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TemaLaboratorService implements Observable<TaskChangeEvent> {
    private List<Observer<TaskChangeEvent>> observers=new ArrayList<>();

    private InMemoryRepository<String, TemaLaborator> repo;

    public TemaLaboratorService(InMemoryRepository r) {
        repo=r;
    }

    /**
     *
     * @param t
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws CloneNotSupportedException
     * if the given entity already exists.
     */
    public void add(TemaLaborator t) throws CloneNotSupportedException{
        if(repo.save(t).isPresent())
            throw new CloneNotSupportedException("Nu se accepta duplicate!");
        else
            notifyObservers(new TaskChangeEvent(ChangeEventType.ADD,t)) ;
    }

    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     * @throws IllegalArgumentException
     * if the given id doesn't match an entity
     */
    public void remove(String id){
        Optional<TemaLaborator> old=repo.delete(id);
        if(old.isPresent())
            notifyObservers(new TaskChangeEvent(ChangeEventType.DELETE, old.get())) ;
        else
            throw new IllegalArgumentException("Tema nu exista!");
    }

    /**
     *
     * @param t
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws ValidationException
     * if the entity is not valid.
     * @throws IllegalArgumentException
     * if the given entity does not exist
     */
    public void update(TemaLaborator t){
        Optional<TemaLaborator> old=repo.update(t);
        if(old.isPresent())
            throw new IllegalArgumentException("Tema nu exista!");
        else
            notifyObservers(new TaskChangeEvent(ChangeEventType.UPDATE,t, old.get()));
    }

    /**
     *
     * @return all entities
     */
    public Iterable<TemaLaborator> getAll() {
        return repo.findAll();
    }

    /**
     *
     * @return all entities
     */
    public TemaLaborator get(String id) {
        return repo.findOne(id).get();
    }

    /**
     *
     * @return number of entities
     */
    public int getNumberOfTeme(){
        return repo.size();
    }

    /**
     *
     * @param t
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws ValidationException
     * if the entity is not valid.
     */
    public void prelungireDeadline(TemaLaborator t){
        TemaLaborator old=t;
        if (t==null)
            throw new IllegalArgumentException("Entitatea nu poate fi nula!");
        repo.findOne(t.getID()).orElseThrow(()->new IllegalArgumentException("Entitatea nu exista!"));
        if(Time.getCurrentWeek()>=t.getDeadline())
            t.setDeadline((int)Time.getCurrentWeek()+1);
        repo.update(t);
        notifyObservers(new TaskChangeEvent(ChangeEventType.UPDATE,t, old));
    }


    /**
     *
     * @return a list of the labs that need an updated deadline
     *
     *
     */
    public List<TemaLaborator> getNewDeadlineLabs(){
        Iterable<TemaLaborator> labs=repo.findAll();
        List<TemaLaborator> l=new ArrayList<>();
        labs.forEach(t->l.add(t));
        Predicate<TemaLaborator> byDeadline = t -> t.getDeadline() <=Time.getCurrentWeek();

        List<TemaLaborator> result = l.stream().filter(byDeadline).collect(Collectors.<TemaLaborator> toList());
        return result;
    }

    /**
     *
     * @return
     *
     *
     */
    public TemaLaborator getCurrentTask(){
        Iterable<TemaLaborator> labs=repo.findAll();
        List<TemaLaborator> l=new ArrayList<>();
        labs.forEach(t->l.add(t));

        Predicate<TemaLaborator> byDeadline = t -> t.getDeadline() >=Time.getCurrentWeek();

        TemaLaborator result = l.stream().filter(byDeadline).collect(Collectors.<TemaLaborator> toList()).get(0);
        return result;
    }

    @Override
    public void addObserver(Observer<TaskChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<TaskChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(TaskChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}


