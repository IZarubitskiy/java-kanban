import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> idSubtasklist;

    public Epic(String title, String description, Integer id, TaskStatus statusTask, ArrayList<Integer> idSubtask) {
        super(title, description, id, statusTask);
        this.idSubtasklist = idSubtask;
    }

    public ArrayList<Integer> getSubTasks() {
        return idSubtasklist;
    }

    public void setIdSubtasklist(ArrayList<Integer> idSubtasklist) {
        this.idSubtasklist = idSubtasklist;
    }

    @Override
    public String toString() {
        return "Epic" +
                super.toString() +
                ", idSubtasklist=" + idSubtasklist +
                '}';
    }
}
