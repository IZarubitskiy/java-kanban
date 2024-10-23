package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    int epicId;

    public SubTask(String title, String description, Integer id, TaskStatus statusTask, LocalDateTime startTime, Duration duration, int epicId) {
        super(title, description, id, statusTask, startTime, duration);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, TaskStatus statusTask, LocalDateTime startTime, Duration duration, int epicId) {
        super(title, description, statusTask, startTime, duration);
        this.epicId = epicId;
    }

    @Override
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String getType() {
        return TaskTypes.SUBTASK.name();
    }

    @Override
    public String toString() {
        return "tasks.SubTask" +
                super.toString() +
                ", epicId=" + epicId +
                '}';
    }
}
