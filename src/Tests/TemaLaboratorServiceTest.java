package Tests;

import Domain.TemaLaborator;
import Domain.Validator.ValidatorTemaLaborator;
import Repository.InMemoryRepository;
import Service.StudentService;
import Service.TemaLaboratorService;
import Utils.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.List;

public class TemaLaboratorServiceTest {
    private TemaLaboratorService srv;

    @Before
    public void setUp() throws Exception {
        srv=new TemaLaboratorService(new InMemoryRepository<String, TemaLaborator>(new ValidatorTemaLaborator()));
        srv.add(new TemaLaborator("1", "MAP",  46, 50));
        srv.add(new TemaLaborator("2", "Baze",  46, 50));
        srv.add(new TemaLaborator("3", "Retele",  42, 47));

        assertEquals(this.srv.getNumberOfTeme(), 3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() {
        try{
            srv.add(new TemaLaborator("3", "Retele", 45, 47));
            assertTrue(false);
        }catch(CloneNotSupportedException e){
            assertEquals(e.getMessage(), "Nu se accepta duplicate! (Tema Laborator Service)");
            assertTrue(true);
        }
    }

    @Test
    public void remove() {
        try{
            srv.remove("4");
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Tema Laborator nu exista!");
            assertTrue(true);
        }
        try{
            srv.remove("");
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "ID-ul nu poate fi null!");
            assertTrue(true);
        }
    }

    @Test
    public void update() {
        try{
            srv.update(new TemaLaborator("4", "MAP", 40, 40));
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Tema Laborator nu exista!");
            assertTrue(true);
        }
        try{
            srv.update(null);
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Entitatea nu poate fi nula!");
            assertTrue(true);
        }
    }

    @Test
    public void prelungireDeadline(){
        try{
            TemaLaborator t=new TemaLaborator("4", "MAP", 40, 44);
            srv.add(t);
            srv.prelungireDeadline(t);
            assertEquals(srv.get("4").getDeadline(), Time.getCurrentWeek()+1);
        }
        catch(CloneNotSupportedException e){
            assertTrue(false);
        }
        try{
            srv.prelungireDeadline(null);
            assertTrue(false);
        }
        catch(IllegalArgumentException x){
            assertEquals(x.getMessage(), "Entitatea nu poate fi nula!");
        }
        try{
            srv.prelungireDeadline(new TemaLaborator("8", "MAP", 40, 40));
            assertTrue(false);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Entitatea nu exista!");
            assertTrue(true);
        }

    }

    @Test
    public void getNewDeadlineLabs(){
        try{
            srv.add(new TemaLaborator("5", "Retele", 40, 43));
            srv.add(new TemaLaborator("6", "Retele", 40, 44));
            List<TemaLaborator> l =srv.getNewDeadlineLabs();
            assertEquals(l.size(), 3);
        }
        catch(CloneNotSupportedException e){
            assertTrue(false);
        }
    }
}