package Tests;

import Domain.Student;
import Domain.Validator.ValidatorStudent;
import Repository.InMemoryRepository;
import Service.StudentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.Assert.*;

public class StudentServiceTest {
    private StudentService srv;

    @Before
    public void setUp() throws Exception {
        srv = new StudentService(new InMemoryRepository<String, Student>(new ValidatorStudent()));
        srv.add(new Student("1", "Mara", 4, "mara_corina_ioana"));
        srv.add(new Student("2", "Luca", 4, "luca_bogdan"));
        srv.add(new Student("3", "Linca", 5, "linca_razvan"));

        assertEquals(this.srv.getNumberOfStudents(), 3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() {
        try {
            srv.add(new Student("1", "Mara", 4, "mara_corina_ioana"));
            assertTrue(false);
        } catch (CloneNotSupportedException e) {
            assertEquals(e.getMessage(), "Nu se accepta duplicate!");
            assertTrue(true);
        }
    }

    @Test
    public void remove() {
        try {
            srv.remove("4");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Studentul nu exista!");
            assertTrue(true);
        }
        try {
            srv.remove("");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "ID-ul nu poate fi null!");
        }
    }

    @Test
    public void update() {
        try {
            srv.update(new Student("4", "Mara", 4, "mara_corina_ioana"));
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Studentul nu exista!");
            assertTrue(true);
        }
        try {
            srv.update(null);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Entitatea nu poate fi nula!");
        }
    }

    @Test
    public void searchByName(){
            List<Student> st1=srv.searchByName("Mara");
            assertEquals(st1.size(), 1);
            List<Student> st2=srv.searchByName("Pop");
            assertEquals(st2.size(), 0);
            List<Student> st3=srv.searchByName("");
            assertEquals(st3.size(), 3);

    }
}