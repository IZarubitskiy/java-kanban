import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> idSubtasklist;
    LocalDateTime endTime;

    public Epic(String title, String description, Integer id, TaskStatus statusTask, LocalDateTime startTime, Duration duration, ArrayList<Integer> idSubtasklist, LocalDateTime endTime) {
        super(title, description, id, statusTask, startTime, duration);
        this.idSubtasklist = idSubtasklist;
        this.endTime = endTime;
    }


    public ArrayList<Integer> getSubTasks() {
        return idSubtasklist;
    }

    public void setIdSubtasklist(ArrayList<Integer> idSubtasklist) {
        this.idSubtasklist = idSubtasklist;
    }



    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public String getType() {
        return TaskTypes.EPIC.name();
    }

    @Override
    public String toString() {
        return "Epic" +
                super.toString() +
                ", idSubtasklist=" + idSubtasklist +
                '}';
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


}
