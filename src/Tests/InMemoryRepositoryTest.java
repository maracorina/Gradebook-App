package Tests;

import Domain.Student;
import Domain.Validator.ValidationException;
import Domain.Validator.ValidatorStudent;
import Repository.InMemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import java.util.Optional;

public class InMemoryRepositoryTest {
    private InMemoryRepository<String, Student>  repo= new InMemoryRepository<>(new ValidatorStudent());

    @Before
    public void setUp() throws Exception {
        repo.save(new Student("1", "Mara", 4, "mara_corina_ioana"));
        repo.save(new Student("2", "Luca", 4, "luca_bogdan"));
        repo.save(new Student("3", "Linca", 4, "linca_razvan"));

        assertEquals(this.repo.size(), 3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findOne() {
        assertEquals(repo.findOne("1").get().getNume(), "Mara");
        assertEquals(repo.findOne("4"), Optional.empty());
        try{
            repo.findOne("");
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "ID-ul nu poate fi null!");
            assertTrue(true);
        }
    }

    @Test
    public void save() {
        try{
            repo.save(null);
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Entitatea nu poate fi nula!");
            assertTrue(true);
        }
            assertEquals(repo.save(new Student("1", "Mara", 1, "mara")).get().getNume(), "Mara");
            assertEquals(repo.save(new Student("4", "Ilie", 4, "ilie_bogdan")), Optional.empty());
            assertEquals(repo.size(), 4);
    }

    @Test
    public void delete() {
        assertTrue(repo.delete("3").isPresent());
        assertEquals(repo.size(), 2);
        try{
            repo.delete("");
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "ID-ul nu poate fi null!");
            assertTrue(true);
        }
        assertFalse(repo.delete("4").isPresent());
    }

    @Test
    public void update() {
        try{
            repo.update(null);
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Entitatea nu poate fi nula!");
            assertTrue(true);
        }
        assertEquals(repo.update(new Student("4", "Corina", 1, "mara")).get().getNume(), "Corina");
        assertEquals(repo.update(new Student("1", "Ilie", 4, "ilie_bogdan")), Optional.empty());
    }
}