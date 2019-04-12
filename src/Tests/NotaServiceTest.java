package Tests;

import Domain.Nota;
import Domain.Student;
import Domain.Validator.ValidatorCadruDidactic;
import Domain.Validator.ValidatorNota;
import Domain.Validator.ValidatorStudent;
import Domain.Validator.ValidatorTemaLaborator;
import Repository.*;
import Service.NotaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

public class NotaServiceTest {

    private StudentRepository rStud;
    private TemaLaboratorRepository rTeme;
    private CadruDidacticRepository rCadre;
    private InMemoryRepository rNote;
    NotaService srv;

    @Before
    public void setUp() throws Exception {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter("StudentsTest",false))) {
            bW.write("1,Student1,3,mara_corina_ioana@yahoo.com\n" +
                    "2,Student2,5,linca");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bW = new BufferedWriter(new FileWriter("CadreDidacticeTest",false))) {
            bW.write("1,Andor Camelia,andor.camelia@scs.ubbcluj.ro,MAP");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bW = new BufferedWriter(new FileWriter("TasksTest",false))) {
            bW.write("1,MAP,44,45\n" +
                    "2,ASC,45,46\n" +
                    "3,ASC,40,43");
        } catch (IOException e) {
            e.printStackTrace();
        }

        rStud = new StudentRepository("StudentsTest", new ValidatorStudent());
        rNote=new InMemoryRepository(new ValidatorNota());
        rCadre=new CadruDidacticRepository("CadreDidacticeTest", new ValidatorCadruDidactic());
        rTeme=new TemaLaboratorRepository("TasksTest", new ValidatorTemaLaborator());
        srv=new NotaService(rStud, rTeme, rCadre, rNote);

        srv.add(new Nota("1", "1", 7, "Respecta structura!" , 45 ,47, "1"));
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("StudentsTest");
        file.delete();
        file = new File("CadreDidacticeTest");
        file.delete();
        file = new File("TasksTest");
        file.delete();

        rStud.findAll().forEach(st->{File file1 = new File(st.getNume()+".txt");
            file1.delete();});
    }

    @Test
    public void add() {
        try {
            srv.add(new Nota("2", "3", 7, "Respecta structura!" , 45 ,47, "1"));
            assertEquals(srv.get("2", "3").getNota(), 1.0, 0.01);
            srv.add(new Nota("2", "2", 7, "Respecta structura!" , 45 ,47, "1"));
            assertEquals(srv.get("2", "2").getNota(), 1.0, 0.01);
            assertEquals(srv.getNumberOfGrades(), 3);
            srv.add(new Nota("1", "1", 7, "Respecta structura!" , 45 ,47, "1"));
            assertTrue(false);
        } catch (CloneNotSupportedException e) {
            assertEquals(e.getMessage(), "Nu se accepta duplicate! (Student Service)");
            assertTrue(true);
        }

    }

    @Test
    public void motivareAbsenta() {
        try {
            srv.add(new Nota("2", "1", 9, "Bine!" , 46 ,45, "1"));
            assertEquals(srv.get("2", "1").getNota(), 1, 0.01);
            srv.motivareAbsenta("2", "1", 45);
            assertEquals(srv.get("2", "1").getNota(), 3.5, 0.01);
            srv.motivareAbsenta("2", "1", 30);
            assertTrue(false);
        } catch (CloneNotSupportedException e) {
            assertTrue(false);
        } catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void printToStudentFile() {
        File file = new File(rStud.findOne("1").get().getNume()+".txt");
        file.delete();

        try {
            srv.add(new Nota("1", "2", 7, "Respecta structura!" , 45 ,47, "1"));
            try (BufferedReader buff = new BufferedReader(new FileReader(rStud.findOne("1").get().getNume()+".txt"))) {
                String linie, all="";
                while((linie=buff.readLine())!=null){
                    all=all+linie+"\n";
                }
                assertEquals(all.compareTo("Tema:2\n" +
                        "Nota:1.0\n" +
                        "Predata in saptamana:45\n" +
                        "Deadline:47\n" +
                        "Feedback:Respecta structura!\n" +
                        "\n"), 0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (CloneNotSupportedException e) {
            assertTrue(false);
        } catch(IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
        public void getStudentGrades(){
        List<Nota> st1=srv.getStudentGrades("1");
        assertEquals(st1.size(), 1);
        List<Nota> st0=srv.getStudentGrades("0");
        assertEquals(st0.size(), 0);
        try {
            List<Nota> st=srv.getStudentGrades("");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "ID-ul nu poate fi null!");
            assertTrue(true);
        }
    }

    @Test
    public void getTaskGrades(){
        List<Nota> t1=srv.getTaskGrades("1");
        assertEquals(t1.size(), 1);
        List<Nota> t0=srv.getTaskGrades("0");
        assertEquals(t0.size(), 0);
        try {
            List<Nota> st=srv.getTaskGrades("");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "ID-ul nu poate fi null!");
            assertTrue(true);
        }
    }

    @Test
    public void getGroupGrades(){
        List<Nota> g3=srv.getGroupGrades(3);
        assertEquals(g3.size(), 1);
        List<Nota> g0=srv.getGroupGrades(2);
        assertEquals(g0.size(), 0);
        try {
            List<Nota> g=srv.getGroupGrades(-1);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Grupa nu poate fi mai mica decat 1!");
            assertTrue(true);
        }
    }

    @Test
    public void getGroupTaskGrades(){
        List<Nota> gt=srv.getGroupTaskGrades(3, "1");
        assertEquals(gt.size(), 1);
        List<Nota> gt0=srv.getGroupTaskGrades(2, "1");
        assertEquals(gt0.size(), 0);
    }


}