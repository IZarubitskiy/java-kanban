import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> idSubtasklist;

    public Epic(String title, String description, TaskStatus statusTask, ArrayList<Integer> idSubtask ) {
        super(title, description, statusTask);
        this.idSubtasklist = idSubtask;
    }

    public ArrayList<Integer> getSubTasks() {
        return idSubtasklist;
    }

    public void setIdSubtasklist(ArrayList<Integer> idSubtasklist) {
        this.idSubtasklist = idSubtasklist;
    }
}
