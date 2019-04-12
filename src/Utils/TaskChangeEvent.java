package Utils;

import Domain.Student;
import Domain.TemaLaborator;

public class TaskChangeEvent implements Event{
    private ChangeEventType type;
    private TemaLaborator data, oldData;

    public TaskChangeEvent() {}

    public TaskChangeEvent(ChangeEventType type, TemaLaborator data) {
        this.type = type;
        this.data = data;
    }
    public TaskChangeEvent(ChangeEventType type, TemaLaborator data, TemaLaborator oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public TemaLaborator getData() {
        return data;
    }

    public TemaLaborator getOldData() {
        return oldData;
    }
}
