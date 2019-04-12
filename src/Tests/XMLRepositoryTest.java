package Tests;

import Domain.Student;
import Domain.Validator.ValidatorStudent;
import Repository.StudentRepository;
import Repository.StudentXMLRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class XMLRepositoryTest {
    private StudentXMLRepository repo;

    @Before
    public void setUp() throws Exception {
        Files.createFile(Paths.get("repotest.xml"));
        try (BufferedWriter bW = new BufferedWriter(new FileWriter("repotest.xml",true))) {
            bW.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<root>\n" +
                    "</root>");
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        repo=new StudentXMLRepository("repotest.xml", new ValidatorStudent());
        repo.save(new Student("1", "Mara", 4, "mara_corina_ioana"));
        repo.save(new Student("2", "Luca", 4, "luca_bogdan"));
        repo.save(new Student("3", "Linca", 4, "linca_razvan"));
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("repotest.xml");
        file.delete();
    }

    @Test
    public void loadFile() {
        StudentXMLRepository repoaux=new StudentXMLRepository("repotest.xml", new ValidatorStudent());
        assertEquals(repoaux.size(), 3);
        assertEquals(repoaux.findOne("1").get().getNume(), "Mara");
    }

    @Test
    public void writeToFile() {
        repo.update(new Student("1", "Corina", 4, "mara_corina_ioana"));
        StudentXMLRepository repoaux=new StudentXMLRepository("repotest.xml", new ValidatorStudent());
        assertEquals(repoaux.findOne("1").get().getNume(), "Corina");
    }
}