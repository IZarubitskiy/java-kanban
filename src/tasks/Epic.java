package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskListId;
    private LocalDateTime endTime;

    public Epic(String title, String description, Integer id, TaskStatus statusTask, LocalDateTime startTime, Duration duration, ArrayList<Integer> idSubtasklist, LocalDateTime endTime) {
        super(title, description, id, statusTask, startTime, duration);
        this.subTaskListId = idSubtasklist;
        this.endTime = endTime;
    }

    public Epic(String title, String description, TaskStatus statusTask, LocalDateTime startTime, Duration duration, ArrayList<Integer> idSubtasklist, LocalDateTime endTime) {
        super(title, description, statusTask, startTime, duration);
        this.subTaskListId = idSubtasklist;
        this.endTime = endTime;
    }

    public ArrayList<Integer> getSubTasks() {
        return subTaskListId;
    }

    public void setSubTaskListId(ArrayList<Integer> newSubTaskListId) {
        this.subTaskListId = newSubTaskListId;
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
        return "tasks.Epic" +
                super.toString() +
                ", idSubtasklist=" + subTaskListId +
                '}';
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


}
