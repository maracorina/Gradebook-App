package Repository;

import Domain.HasID;
import Domain.Validator.Validator;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class FileRepository<ID, E extends HasID<ID>> extends InMemoryRepository<ID,E> {
    private String fileName;

    public FileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadFromFile();
    }

    public void loadFromFile() {
        try (BufferedReader buff = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=buff.readLine())!=null){
                List<String> attr= Arrays.asList(linie.split(","));
                E e=extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<E> save(E entity){
        Optional<E> e=super.save(entity);
        if (!e.isPresent())
        {
            writeToFile(entity);
        }
        return e;
    }

    @Override
    public Optional<E> delete(ID id){
        Optional<E> e=super.delete(id);
        if (e.isPresent())
        {
            overwriteFile();
        }
        return e;
    }

    @Override
    public Optional<E> update(E entity){
        Optional<E> e=super.update(entity);
        if (!e.isPresent())
        {
            overwriteFile();
        }
        return e;
    }

    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write(entity.toString());
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void overwriteFile(){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,false))) {
            this.findAll().forEach( e -> {
                try {
                    bW.write(e.toString());
                    bW.newLine();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(List<String> attributes);
}
