package Repository;

import Domain.HasID;
import Domain.Validator.ValidationException;
import Domain.Validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends HasID<ID>> implements CrudRepository<ID,E> {

    private Validator<E> validator;
    private Map<ID,E> entities;

    /**
     *
     * @param validator
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return E the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    public Optional<E> findOne(ID id) {
        if (id=="")
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    /**
     *
     * @return Iterable all entities
     */
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    /**
     *
     * @param entity
     * entity must be not null
     * @return E
     * null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
    public Optional<E> save(E entity) throws ValidationException {
        if(entity==null)
            throw new IllegalArgumentException("Entitatea nu poate fi nula!");
        validator.validate(entity);
        if(findOne(entity.getID()).isPresent()) {
            return Optional.ofNullable(entity);
        }
        entities.put(entity.getID(),entity);
        return Optional.empty();
    }

    @Override
    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    public Optional<E> delete(ID id) {
        if (id=="")
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        return Optional.ofNullable(entities.remove(id));

    }

    @Override
    /**
     *
     * @param entity
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws ValidationException
     * if the entity is not valid.
     */
    public Optional<E> update(E entity) {
        if(entity==null)
            throw new IllegalArgumentException("Entitatea nu poate fi nula!");
        validator.validate(entity);
        if(findOne(entity.getID()).isPresent()) {
            entities.remove(entity.getID());
            entities.put(entity.getID(),entity);
            return Optional.empty();
        }
        return Optional.ofNullable(entity);
    }

    public int size(){
        return entities.size();
    }
}
