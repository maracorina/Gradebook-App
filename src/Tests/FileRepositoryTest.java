package Tests;

import Domain.Student;
import Domain.Validator.ValidatorStudent;
import Repository.StudentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.*;

public class FileRepositoryTest {

    private StudentRepository repo;

    @Before
    public void setUp() throws Exception {
        Files.createFile(Paths.get("repotest"));
        repo=new StudentRepository("repotest", new ValidatorStudent());
        repo.save(new Student("1", "Mara", 4, "mara_corina_ioana"));
        repo.save(new Student("2", "Luca", 4, "luca_bogdan"));
        repo.save(new Student("3", "Linca", 4, "linca_razvan"));
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("repotest");
        file.delete();
    }

    @Test
    public void loadFromFile() {
        StudentRepository repoaux=new StudentRepository("repotest", new ValidatorStudent());
        assertEquals(repoaux.size(), 3);
        assertEquals(repoaux.findOne("1").get().getNume(), "Mara");
    }

    @Test
    public void overwriteFile() {
        repo.update(new Student("1", "Corina", 4, "mara_corina_ioana"));
        StudentRepository repoaux=new StudentRepository("repotest", new ValidatorStudent());
        assertEquals(repoaux.findOne("1").get().getNume(), "Corina");
    }

}