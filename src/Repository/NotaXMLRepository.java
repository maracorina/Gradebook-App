package Repository;

import Domain.Nota;
import Domain.Student;
import Domain.TemaLaborator;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorNota;
import Domain.Validator.ValidatorStudent;
import Domain.Validator.ValidatorTemaLaborator;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public class NotaXMLRepository extends XMLRepository<Pair<String, String>, Nota> {


    public NotaXMLRepository(String file, ValidatorNota validator) {
        super(validator, file);
    }



    @Override
    public Element createElementfromEntity(Document document, Nota n) {
        Element e = document.createElement("Nota");
        e.setAttribute("idStudent", n.getStudent().getID());
        e.setAttribute("idTask", n.getLaborator().getID());

        Element nota = document.createElement("nota");
        nota.setTextContent(String.valueOf(n.getNota()));
        e.appendChild(nota);

        Element feedback = document.createElement("feedback");
        feedback.setTextContent(n.getFeedback());
        e.appendChild(feedback);

        Element saptamanaPredare = document.createElement("saptamanaPredare");
        saptamanaPredare.setTextContent(String.valueOf(n.getSaptamanaPredare()));
        e.appendChild(saptamanaPredare);

        Element deadline = document.createElement("deadline");
        deadline.setTextContent(String.valueOf(n.getDeadline()));
        e.appendChild(deadline);

        Element idCadruDidactic = document.createElement("idCadruDidactic");
        idCadruDidactic.setTextContent(n.getCadruDidactic().getID());
        e.appendChild(idCadruDidactic);

        return e;
    }

    @Override
    public Nota createEntityFromElement(Element e) {
        String idS = e.getAttribute("idStudent");
        String idT = e.getAttribute("idTask");

        float nota =Float.parseFloat(e.getElementsByTagName("nota")
                .item(0)
                .getTextContent());

        String feedback =e.getElementsByTagName("feedback")
                .item(0)
                .getTextContent();

        int saptamanaPredare =Integer.parseInt(e.getElementsByTagName("saptamanaPredare")
                .item(0)
                .getTextContent());

        int deadline =Integer.parseInt(e.getElementsByTagName("deadline")
                .item(0)
                .getTextContent());

        String idCadruDidactic =e.getElementsByTagName("idCadruDidactic")
                .item(0)
                .getTextContent();

        return new Nota(idS, idT, nota, feedback, saptamanaPredare, deadline, idCadruDidactic);
    }
}
