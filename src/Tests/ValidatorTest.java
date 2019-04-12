package Tests;

import Domain.Student;
import Domain.TemaLaborator;
import Domain.Validator.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {
    private ValidatorStudent ValSt=new ValidatorStudent();
    private ValidatorTemaLaborator ValTL=new ValidatorTemaLaborator();
    private ValidatorNota ValNt=new ValidatorNota();
    private ValidatorCadruDidactic ValCD=new ValidatorCadruDidactic();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void validateStudent() {
        try{
            ValSt.validate(new Student("", "Mara", 4, "mara@yahoo.com"));
            assertTrue(false);
        }
        catch(ValidationException e){
            assertEquals(e.getMessage(),"ID-ul studentului nu poate fi null!\n");
            assertTrue(true);
        }
        try{
            ValSt.validate(new Student("3", "", 4, "mara@yahoo.com"));
            assertTrue(false);
        }
        catch(ValidationException e){
            assertEquals(e.getMessage(),"Numele studentului nu poate fi null!\n");
            assertTrue(true);
        }
        try{
            ValSt.validate(new Student("3", "Mara", 0, "mara@yahoo.com"));
            assertTrue(false);
        }
        catch(ValidationException e){
            assertEquals(e.getMessage(),"Grupa studentului nu poate fi mai mica decat 1!\n");
            assertTrue(true);
        }
        try{
            ValSt.validate(new Student("3", "Mara", 8, "mara@yahoo.com"));
            assertTrue(false);
        }
        catch(ValidationException e){
            assertEquals(e.getMessage(),"Grupa studentului nu poate fi mai mare decat 7!\n");
            assertTrue(true);
        }
    }

    @Test
    public void validateTemaLaborator(){
        try{
            ValTL.validate(new TemaLaborator("", "MAP", 40, 45));
            assertTrue(false);
        }
        catch(ValidationException e){
            assertEquals(e.getMessage(),"ID-ul laboratorului nu poate fi null!\n");
            assertTrue(true);
        }
        try{
            ValTL.validate(new TemaLaborator("1", "MAP", 46, 45));
            assertTrue(false);
        }
        catch(ValidationException e){
            assertEquals(e.getMessage(),"Saptamana de start nu poate fi mai mare decat deadline-ul laboratorului!\n");
            assertTrue(true);
        }
        try{
            ValTL.validate(new TemaLaborator("1", "MAP", 30, 45));
            assertTrue(false);
        }
        catch(ValidationException e){
            assertEquals(e.getMessage(),"Saptamana de start nu poate fi inaintea primei saptamani din semestru!\nSemestrul are doar 14 saptamani!\n");
            assertTrue(true);
        }
    }
}