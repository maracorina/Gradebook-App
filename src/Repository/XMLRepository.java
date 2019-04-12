package Repository;

import Domain.HasID;
import Domain.Validator.ValidationException;
import Domain.Validator.Validator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Optional;

public abstract class XMLRepository<ID, E extends HasID<ID>> extends InMemoryRepository<ID,E>  {
    private String fileName;

    public XMLRepository(Validator<E> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }


    /**
     *
     * loads data from the file
     */
    private void loadData() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i=0; i < children.getLength(); i++) {
                Node nod = children.item(i);
                if(nod instanceof Element) {
                    E entity = createEntityFromElement((Element)nod);
                    super.save(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * writes data to file
     */
    public void writeToFile(){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root  = document.createElement("root");
            document.appendChild(root);
            super.findAll().forEach(el->{
                Element e = createElementfromEntity(document,el);
                root.appendChild(e);
            });

            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(fileName));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *@param document
     * @param m
     * creates a new element to be added to the file
     */
    public abstract Element createElementfromEntity(Document document, E m);


    /**
     *@param e
     * extracts an entity from an element
     */
    public abstract E createEntityFromElement(Element e);


    /**
     *
     * @param entity
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
    @Override
    public Optional<E> save(E entity){
        Optional<E> e=super.save(entity);
        if (!e.isPresent())
        {
            writeToFile();
        }
        return e;
    }

    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    @Override
    public Optional<E> delete(ID id){
        Optional<E> e=super.delete(id);
        if (e.isPresent())
        {
            writeToFile();
        }
        return e;
    }


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
    @Override
    public Optional<E> update(E entity){
        Optional<E> e=super.update(entity);
        if (!e.isPresent())
        {
            writeToFile();
        }
        return e;
    }
}
