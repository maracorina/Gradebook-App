package Domain;

public class NotaDTOTableEditable {
    private String studentName;
    private String  nota;
    private String  feedback;

    public NotaDTOTableEditable(String studentName, String nota, String fb) {
        this.studentName = studentName;
        this.nota = nota;
        feedback=fb;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
